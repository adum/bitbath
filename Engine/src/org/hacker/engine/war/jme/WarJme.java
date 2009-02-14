package org.hacker.engine.war.jme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import jmetest.renderer.TestBoxColor;
import jmetest.terrain.TestTerrainTrees;

import org.hacker.engine.war.Faction;
import org.hacker.engine.war.Terrain;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.WarModel;
import org.hacker.engine.war.jme.ModelCache.ModelType;
import org.hacker.engine.war.unit.Ambulatory;
import org.hacker.engine.war.unit.City;
import org.hacker.engine.war.unit.Unit;
import org.hacker.engine.war.unit.UnitTypes;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.light.DirectionalLight;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.Skybox;
import com.jme.scene.Spatial;
import com.jme.scene.Text;
import com.jme.scene.Spatial.CullHint;
import com.jme.scene.Spatial.LightCombineMode;
import com.jme.scene.Spatial.TextureCombineMode;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Pyramid;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.MaterialState.ColorMaterial;
import com.jme.util.TextureManager;
import com.jme.util.Timer;
import com.jmex.effects.TrailMesh;
import com.jmex.effects.particles.ParticleLines;
import com.jmex.effects.particles.ParticleMesh;
import com.jmex.terrain.TerrainBlock;

public class WarJme extends SimpleGame implements VisualsListener {
    private WarModel model;
    
    // Side walls
    private Node sideWalls;

    private TerrainBlock tb;
//    private Node scene;
    private Node lasersNode, unitsNode;
    
    private boolean gameOver = false;
    
    private HashMap<Unit, UnitRecord> unit_map = new HashMap<Unit, UnitRecord>();
    private List<LaserFire> laserFire = new ArrayList<LaserFire>();
    private Vector3f wheelAxis = new Vector3f(0, 1, 0);
    private List<ParticleLines> lasers = new ArrayList<ParticleLines>();
    private Text gameTimeText;

    // loads models by type
    private ModelCache modelCache = new ModelCache();

    private TextureState[] factionTextures;
        private TextureState laserTrailTextureState;

    private Skybox skybox;
    private Random random = new Random(); // some effects randomized

        private TextureState damageTexture;

    @Override
        protected void simpleUpdate() {
        // progress game model
        if (!gameOver) {
                model.step();
                Object winner = model.findWinner();
                if (winner != null) {
                        gameOver = true;
                }
        }
        
        // show game time
        gameTimeText.getText().replace(0, gameTimeText.getText().length(), "" + (int)model.getTime());

        //we want to keep the skybox around our eyes, so move it with the camera -- doesn't work?
        if (skybox != null) {
            skybox.setLocalTranslation(cam.getLocation());
            skybox.updateGeometricState(0, true);
        }
        
        for (ParticleLines plines: lasers) {
            if (plines.isActive() == false) {
                lasers.remove(plines);
                break;
            }
        }

        // idle lasers
        for (LaserFire lf: laserFire) {
                float width = 0.05f;
                Vector3f v = new Vector3f();
                float frac = (timer.getTime() - lf.birth) / ((float)timer.getResolution() * 0.2f);
                v.interpolate(lf.p0, lf.p1, frac);
            lf.trailMesh.setTrailFront(v, width, Timer.getTimer().getTimePerFrame());
            lf.trailMesh.update(cam.getLocation());
        }
        for (LaserFire lf: laserFire) {
            if (timer.getTime() - lf.birth > timer.getResolution() / 5) {
//            if (timer.getTime() - lf.birth > timer.getResolution() / 2) {
                laserFire.remove(lf);
                if (lf.line != null) lasersNode.detachChild(lf.line);
                lasersNode.detachChild(lf.trailMesh);
                break;
            }
        }

        // update units
        for (Unit unit : model.terra.units) {
                        UnitRecord rec = unit_map.get(unit);
                        if (rec == null) {
                                try {
                            rec = createUnitNode(unit);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                        
                        if (rec != null) {
                                updateUnitNode(unit, rec);
                        }
                }
        }

        private void updateUnitNode(Unit unit, UnitRecord rec) {
                BoundingBox bbox =((BoundingBox)rec.node.getWorldBound());

                if (unit instanceof Ambulatory) {
                        // set position
                        float x = (float)unit.x;
                        float z = (float)unit.y;
                        float y = 0;
                        float characterMinHeight = tb.getHeight(x, z);
                        if (!Float.isInfinite(characterMinHeight) && !Float.isNaN(characterMinHeight)) {
                            y = characterMinHeight;
                        }
                        if (bbox != null) y += bbox.yExtent;
                        rec.groupNode.setLocalTranslation(x, y, z);
                
                        // set location
                    double ang = ((Ambulatory) unit).getMoveAngle();
                    ang = -ang;
                    ang += FastMath.PI * 0.5f;
                    rec.rotQuat.fromAngleAxis((float) ang, wheelAxis);
                    rec.node.setLocalRotation(rec.rotQuat);
                }
                
                updateUnitDamage(unit, rec, bbox.yExtent);
        }

        private void updateUnitDamage(Unit unit, UnitRecord rec, float yExtent) {
                if (unit.getHp() < unit.maxHP) {
                    // damaged
                    float frac = (float) (unit.getHp() / unit.maxHP);
                    if (rec.damage == null) {
                        // create new damage indicator on top of model
                        float sy = yExtent * 2 + 0.1f;
                        Box box = new Box("dam", new Vector3f(), 0.5f, 0.02f, 0.05f);
                        rec.damage = box;
                        rec.damage.setModelBound(new BoundingBox());
                        rec.damage.updateModelBound();
                        rec.damage.getLocalTranslation().set(0, sy, 0);
                        box.setCullHint(Spatial.CullHint.Never);
//                      box.setDefaultColor(ColorRGBA.green);
                                box.setRenderState(damageTexture);
                        box.updateRenderState();
                        rec.groupNode.attachChild(box);

                        rec.groupNode.updateRenderState();
                    } else {
                        // scale indicator according to damage
                        rec.damage.setLocalScale(new Vector3f(frac, 1, 1));
                    }
                    rec.groupNode.updateModelBound();
                } else {
                    if (rec.damage != null) {
                        // no longer damaged, remove indicator
                        rec.groupNode.detachChild(rec.damage);
                        rec.damage = null;
                    }
                }
        }

        private UnitRecord createUnitNode(Unit unit) throws Exception {
                UnitRecord rec;
                Node node;
                if (unit.getUnitType() == UnitTypes.ARTIL)
                    node = modelCache.load(ModelType.ARTIL);
                else if (unit.getUnitType() == UnitTypes.HOVERCRAFT)
                    node = modelCache.load(ModelType.HOVER);
                else
                    node = modelCache.load(ModelType.UNIT);
                
                node.setModelBound(new BoundingBox());
                node.updateModelBound();
                
                 node.setRenderState(factionTextures[faction2texindex(unit.getFaction())]);

                rec = new UnitRecord();
                rec.node = node;
                unit_map.put(unit, rec);
                rec.groupNode = new Node("grp");
                rec.groupNode.attachChild(node);
                unitsNode.attachChild(rec.groupNode);
                
                unitsNode.updateGeometricState(0, true);
                unitsNode.updateRenderState();
                return rec;
        }

    /**
     * creates a light for the terrain.
     */
    private void buildLighting() {
        /** Set up a basic, default light. */
        DirectionalLight light = new DirectionalLight();
        light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        light.setDirection(new Vector3f(1,-1,0));
        light.setEnabled(true);

          /** Attach the light to a lightState and the lightState to rootNode. */
        LightState lightState = display.getRenderer().createLightState();
        lightState.setEnabled(true);
        lightState.attach(light);
        rootNode.setRenderState(lightState);
    }

        @Override
    protected void simpleInitGame() {
        display.setTitle("BitBath");

        // Initialize camera
        cam.setFrustumPerspective(45.0f, (float) display.getWidth()
                / (float) display.getHeight(), 1f, 1000f);
        cam.setLocation(new Vector3f(-10, 40, 20));
        cam.lookAt(new Vector3f(25, 0, 20), Vector3f.UNIT_Y);
        cam.update();
        
        buildLighting();

        skybox = new SkyMaker().buildSkyBox(display);
//        rootNode.attachChild(skybox);
        
        loadTextures();
                  
        if (false) {
//              scene = new Node("Scene graph node");
//              /** Create a ZBuffer to display pixels closest to the camera above farther ones.  */
//              ZBufferState buf = display.getRenderer().createZBufferState();
//              buf.setEnabled(true);
//              buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
//              scene.setRenderState(buf);
//              
//              //Time for a little optimization. We don't need to render back face triangles, so lets
//              //not. This will give us a performance boost for very little effort.
//              CullState cs = display.getRenderer().createCullState();
//              cs.setCullFace(CullState.Face.Back);
//              scene.setRenderState(cs);
        }
        
        initParticleSystem();
        tb = new TerrainMaker().buildTerrain(model, display);
        rootNode.attachChild(tb);
        createTextNodes();
        initLasers();
        growTrees();
        
        unitsNode = new Node("units");
        rootNode.attachChild(unitsNode);

        // Create side walls
        sideWalls = new Node("Walls");
        rootNode.attachChild(sideWalls);

//        makeBoxes();
//        makeWalls();

        // Make the object default colors shine through
        MaterialState ms = display.getRenderer().createMaterialState();
        ms.setColorMaterial(ColorMaterial.AmbientAndDiffuse);
//        rootNode.setRenderState(ms);
        
        try {
            MaterialState mscity = display.getRenderer().createMaterialState();
            mscity.setColorMaterial(ColorMaterial.Emissive);

            for (Unit unit: model.terra.units) {
                if (unit.getUnitType() != UnitTypes.CITY)
                                        continue;
                                Node cityNode = modelCache.load(ModelCache.ModelType.CITY);

                                cityNode.setRenderState(factionTextures[faction2texindex(unit.getFaction())]);
                                cityNode.setRenderState(mscity);

                cityNode.setModelBound(new BoundingBox());
                cityNode.updateModelBound();
                    float x = (float)unit.x;
                    float z = (float) unit.y;
                    float y = 0;
                    float characterMinHeight = tb.getHeight(x, z);
                    if (!Float.isInfinite(characterMinHeight) && !Float.isNaN(characterMinHeight)) {
                        y = characterMinHeight;
                    }
                    
                    UnitRecord rec = new UnitRecord();
                    rec.node = cityNode;
                    unit_map.put(unit, rec);
                    rec.groupNode = new Node("ugrp");
                    rec.groupNode.attachChild(cityNode);
                    rec.groupNode.setModelBound(new BoundingBox());
                    rec.groupNode.updateModelBound();

                    unitsNode.attachChild(rec.groupNode);
                    unitsNode.updateGeometricState(0, true);
                    BoundingBox bx = (BoundingBox)rec.groupNode.getWorldBound();
                    float bbox =0;
                    if (bx != null) bbox = (bx).yExtent; 
                    rec.groupNode.setLocalTranslation(x, y + bbox, z);
                    
            }
        } catch (Exception e) {   // Just in case anything happens
            e.printStackTrace();
        }
        }

        private void createTextNodes() {
                // Create score showing items
        gameTimeText = Text.createDefaultTextLabel("gameTimeText", "0");
        gameTimeText.setRenderQueueMode(Renderer.QUEUE_ORTHO);
        gameTimeText.setLightCombineMode(Spatial.LightCombineMode.Off);
        gameTimeText.setLocalTranslation(new Vector3f(0, 10, 1));
        rootNode.attachChild(gameTimeText);
        }

        private void initLasers() {
                lasersNode = new Node("lasers");
        BlendState as1 = display.getRenderer().createBlendState();
        as1.setBlendEnabled(true);
        as1.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        as1.setDestinationFunction(BlendState.DestinationFunction.One);
        as1.setEnabled(true);
        lasersNode.setRenderState(as1);
        rootNode.attachChild(lasersNode);

        lasersNode.setCullHint(CullHint.Never);
        lasersNode.setRenderState(laserTrailTextureState);
        // No lighting for clarity
//        lasersNode.setLightCombineMode(LightCombineMode.Off);
//        lasersNode.setRenderQueueMode(com.jme.renderer.Renderer.QUEUE_OPAQUE);

//        lasersNode.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
        }

    private void growTrees() {
        Node treesNode = new Node("trees");
        rootNode.attachChild(treesNode);

        TextureState treeTex = display.getRenderer().createTextureState();
        treeTex.setEnabled(true);
        Texture tr = TextureManager.loadTexture(
                TestTerrainTrees.class.getClassLoader().getResource(
                        "jmetest/data/texture/grass.jpg"), Texture.MinificationFilter.Trilinear,
                Texture.MagnificationFilter.Bilinear);
        treeTex.setTexture(tr);

        Pyramid p = new Pyramid("Pyramid", .3f, .6f);
        p.setModelBound(new BoundingBox());
        p.updateModelBound();
        p.setRenderState(treeTex);
        p.setTextureCombineMode(TextureCombineMode.Replace);
        
        for (int i = 0; i < 500; i++) {
            float x = (float) (Math.random() * model.terra.dimX);
            float z = (float) (Math.random() * model.terra.dimY);
            if (model.terra.getTerrain(x, z) != Terrain.FOREST) continue;
            Spatial s1 = new SharedMesh("tree"+i, p);
            s1.setLocalTranslation(new Vector3f(x, tb.getHeight(x, z) + 0.2f, z));
            treesNode.attachChild(s1);
        }
    }

    private int faction2texindex(Faction faction) {
        if (faction == null)
            return 0;
        return faction.index + 1;
    }

    private void loadTextures() {
        factionTextures = new TextureState[3];
        String[] tsrc = { "img/textures/bumpedmetal64.png", "img/textures/bumpedmetal-red64.png",
                "img/textures/yellowmtl64.png" };
        for (int i = 0; i < factionTextures.length; i++) {
            TextureState mytexture = display.getRenderer().createTextureState();
            mytexture.setEnabled(true);
            Texture texture1 = TextureManager.loadTexture(WarJme.class.getClassLoader().getResource(tsrc[i]),
                    Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear);
            texture1.setWrap(Texture.WrapMode.Repeat);
            float scale = 0.01f;
            texture1.setScale(new Vector3f(scale, scale, scale));
            mytexture.setTexture(texture1);
            factionTextures[i] = mytexture;
        }

        damageTexture = display.getRenderer().createTextureState();
        damageTexture.setEnabled(true);
        Texture texture1 = TextureManager.loadTexture(WarJme.class.getClassLoader().getResource("img/Grass.png"),
                Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear);
        texture1.setWrap(Texture.WrapMode.Repeat);
        float scale = 1f;
        texture1.setScale(new Vector3f(scale, scale, scale));
        damageTexture.setTexture(texture1);
        

        laserTrailTextureState = display.getRenderer().createTextureState();
        laserTrailTextureState.setEnabled(true);
        Texture t1 = TextureManager.loadTexture(
                TestBoxColor.class.getClassLoader().getResource(
                        "jmetest/data/texture/trail.png"),
                Texture.MinificationFilter.Trilinear,
                Texture.MagnificationFilter.Bilinear);
        laserTrailTextureState.setTexture(t1);
    }

    private void makeBoxes() {
        Box wall;
        wall = new Box("origin", new Vector3f(), 2, 2, 2);
        wall.setModelBound(new BoundingBox());
        wall.updateModelBound();
        wall.getLocalTranslation().set(0, 22, 0);
        wall.setDefaultColor(ColorRGBA.green);
        sideWalls.attachChild(wall);

        wall = new Box("5050", new Vector3f(), 1, 1, 1);
        wall.setModelBound(new BoundingBox());
        wall.updateModelBound();
        wall.getLocalTranslation().set(50, 22, 50);
        sideWalls.attachChild(wall);
    }

        private void initParticleSystem() {
                ExplosionFactory.warmup();
        }
        
    public void newCityBuild(City city) {
    }

    public void newExplosion(double x, double y) {
                ParticleMesh explosion = ExplosionFactory.getExplosion();
                
                float ht = tb.getHeight((float)x, (float)y);
                if (!Float.isInfinite(ht) && !Float.isNaN(ht)) {
                    ht = 0.1f;
                }
                explosion.setOriginOffset(new Vector3f((float)x, ht + 0.2f, (float)y));
                explosion.forceRespawn();
                rootNode.attachChild(explosion);
    }

    public void newExplosionSmall(double x, double y) {
                ParticleMesh explosion = ExplosionFactory.getSmallExplosion();
                
                float ht = tb.getHeight((float)x, (float)y);
                if (!Float.isInfinite(ht) && !Float.isNaN(ht)) {
                    ht = 0.1f;
                }
                explosion.setOriginOffset(new Vector3f((float)x, ht + 0.2f, (float)y));
                explosion.forceRespawn();
                rootNode.attachChild(explosion);
    }

    public void newLaser(double startX, double startY, double destX, double destY, Color color) {
        float sy = tb.getHeight((float)startX, (float)startY);
        float dy = tb.getHeight((float)destX, (float)destY);
        float ups = 0.2f;
        LaserFire lf = new LaserFire();
            lf.p0 = new Vector3f((float)startX , sy + ups, (float)startY);
                lf.p1 = new Vector3f((float)destX + random.nextFloat() * 0.2f - 0.1f, dy + ups, (float)destY + random.nextFloat() * 0.2f - 0.1f);
                if (false) {
                        ColorRGBA c = new ColorRGBA(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.2f);
                        Vector3f[] vertex = new Vector3f[] { lf.p0, lf.p1 };
                Line line = new Line("lsr", vertex, null, new ColorRGBA[] { c, c}, null);
                    lasersNode.attachChild(line);
                    lf.line = line;
                }
            lf.birth = timer.getTime();
            laserFire.add(lf);

            // Create the trail
        lf.trailMesh = new TrailMesh("TrailMesh", 40);
        lf.trailMesh.setUpdateSpeed(10.0f);
        lf.trailMesh.setFacingMode(TrailMesh.FacingMode.Billboard);
        lf.trailMesh.setUpdateMode(TrailMesh.UpdateMode.Step);
        lf.trailMesh.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
        lf.trailMesh.setCullHint(CullHint.Never);
        lf.trailMesh.setTrailFront(lf.p0, 0.1f, 0.01f);
        lf.trailMesh.resetPosition(lf.p0);
        lasersNode.attachChild(lf.trailMesh);

            lasersNode.updateRenderState();
    }

    public void newProjectile(double startX, double startY, double destX, double destY) {
    }

    public void newWreck(double x, double y) {
    }

    public void setModel(WarModel model) {
        this.model = model;
    }

        public void deadUnit(Unit unit) {
                UnitRecord rec = unit_map.get(unit);
                if (rec != null) {
                        unitsNode.detachChild(rec.groupNode);
                        unit_map.remove(unit);
                }
        }

    public void changedAllegiance(City city) {
        UnitRecord rec = unit_map.get(city);
        if (rec != null) {
            rec.node.setRenderState(factionTextures[faction2texindex(city.getFaction())]);
            rec.node.updateRenderState();
            unitsNode.updateRenderState();
        }
    }
}

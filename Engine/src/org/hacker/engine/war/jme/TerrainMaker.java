package org.hacker.engine.war.jme;

import javax.swing.ImageIcon;

import jmetest.terrain.TestTerrain;

import org.hacker.engine.war.WarModel;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.terrain.TerrainBlock;
import com.jmex.terrain.util.AbstractHeightMap;
import com.jmex.terrain.util.CombinerHeightMap;
import com.jmex.terrain.util.MidPointHeightMap;
import com.jmex.terrain.util.ProceduralTextureGenerator;

public class TerrainMaker {

	/**
	 * build the height map and terrain block.
	 */
	public TerrainBlock buildTerrain(WarModel model, DisplaySystem display) {
		AbstractHeightMap randHeightMap = new MidPointHeightMap(128, 0.7f);
		AbstractHeightMap terraMap = new TerrainHeightMap(128, model.terra);
		float mixin = 0.5f;
		AbstractHeightMap combinedMap = new CombinerHeightMap(terraMap, mixin, randHeightMap, 1f - mixin, CombinerHeightMap.ADDITION);
		AbstractHeightMap heightMap = new SwampHeightMap(combinedMap, model.terra);
		// Scale the data
		Vector3f terrainScale = new Vector3f((float) (model.terra.dimX / heightMap.getSize()), 0.008f,
				(float) (model.terra.dimX / heightMap.getSize()));
		// Vector3f terrainScale = new Vector3f(4, 0.0575f, 4);
		// create a terrainblock
		TerrainBlock tb = new TerrainBlock("Terrain", heightMap.getSize(), terrainScale, heightMap.getHeightMap(), new Vector3f(0, 0, 0));

		tb.setModelBound(new BoundingBox());
		tb.updateModelBound();

		// generate a terrain texture with 2 textures
		ProceduralTextureGenerator pt = new ProceduralTextureGenerator(heightMap);
		pt.addTexture(new ImageIcon(TestTerrain.class.getClassLoader().getResource("jmetest/data/texture/flare2.png")), -128, 0, 128);
		// .getResource("jmetest/data/texture/dirt.jpg")), -128, 0, 128);
		pt.addTexture(new ImageIcon(TestTerrain.class.getClassLoader().getResource("jmetest/data/texture/grass.jpg")), 0, 128, 255);
		// .getResource("jmetest/data/texture/grassb.png")), -128, 0, 128);
		pt.addTexture(new ImageIcon(TestTerrain.class.getClassLoader().getResource("jmetest/data/texture/highest.jpg")), 128, 255, 384);
		pt.createTexture(32);

		// assign the texture to the terrain
		TextureState ts = display.getRenderer().createTextureState();
		Texture t1 = TextureManager.loadTexture(pt.getImageIcon().getImage(), Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear, true);
		ts.setTexture(t1, 0);

		// load a detail texture and set the combine modes for the two terrain textures.
		Texture t2 = TextureManager.loadTexture(TestTerrain.class.getClassLoader().getResource("jmetest/data/texture/Detail.jpg"),
				Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear);

		ts.setTexture(t2, 1);
		t2.setWrap(Texture.WrapMode.Repeat);

		t1.setApply(Texture.ApplyMode.Combine);
		t1.setCombineFuncRGB(Texture.CombinerFunctionRGB.Modulate);
		t1.setCombineSrc0RGB(Texture.CombinerSource.CurrentTexture);
		t1.setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
		t1.setCombineSrc1RGB(Texture.CombinerSource.PrimaryColor);
		t1.setCombineOp1RGB(Texture.CombinerOperandRGB.SourceColor);

		t2.setApply(Texture.ApplyMode.Combine);
		t2.setCombineFuncRGB(Texture.CombinerFunctionRGB.AddSigned);
		t2.setCombineSrc0RGB(Texture.CombinerSource.CurrentTexture);
		t2.setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
		t2.setCombineSrc1RGB(Texture.CombinerSource.Previous);
		t2.setCombineOp1RGB(Texture.CombinerOperandRGB.SourceColor);

		tb.setRenderState(ts);
		// set the detail parameters.
		tb.setDetailTexture(1, 16);
		tb.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		// scene.attachChild(tb);
		// rootNode.attachChild(tb);
		return tb;
	}

}

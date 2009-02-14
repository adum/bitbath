package org.hacker.engine.war.jme;

import jmetest.TutorialGuide.TestPongCool;

import com.jme.image.Texture;
import com.jme.scene.Skybox;
import com.jme.scene.Spatial;
import com.jme.scene.Spatial.TextureCombineMode;
import com.jme.scene.state.CullState;
import com.jme.scene.state.FogState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

public class SkyMaker {

	public Skybox buildSkyBox(DisplaySystem display) {
		Skybox skybox = new Skybox("skybox", 10, 10, 10);
	
	    String dir = "jmetest/data/skybox1/";
	    Texture north = TextureManager.loadTexture(TestPongCool.class
	            .getClassLoader().getResource(dir + "1.jpg"),
	            Texture.MinificationFilter.BilinearNearestMipMap,
	            Texture.MagnificationFilter.Bilinear);
	    Texture south = TextureManager.loadTexture(TestPongCool.class
	            .getClassLoader().getResource(dir + "3.jpg"),
	            Texture.MinificationFilter.BilinearNearestMipMap,
	            Texture.MagnificationFilter.Bilinear);
	    Texture east = TextureManager.loadTexture(TestPongCool.class
	            .getClassLoader().getResource(dir + "2.jpg"),
	            Texture.MinificationFilter.BilinearNearestMipMap,
	            Texture.MagnificationFilter.Bilinear);
	    Texture west = TextureManager.loadTexture(TestPongCool.class
	            .getClassLoader().getResource(dir + "4.jpg"),
	            Texture.MinificationFilter.BilinearNearestMipMap,
	            Texture.MagnificationFilter.Bilinear);
	    Texture up = TextureManager.loadTexture(TestPongCool.class
	            .getClassLoader().getResource(dir + "6.jpg"),
	            Texture.MinificationFilter.BilinearNearestMipMap,
	            Texture.MagnificationFilter.Bilinear);
	    Texture down = TextureManager.loadTexture(TestPongCool.class
	            .getClassLoader().getResource(dir + "5.jpg"),
	            Texture.MinificationFilter.BilinearNearestMipMap,
	            Texture.MagnificationFilter.Bilinear);
	
	    skybox.setTexture(Skybox.Face.North, north);
	    skybox.setTexture(Skybox.Face.West, west);
	    skybox.setTexture(Skybox.Face.South, south);
	    skybox.setTexture(Skybox.Face.East, east);
	    skybox.setTexture(Skybox.Face.Up, up);
	    skybox.setTexture(Skybox.Face.Down, down);
	    skybox.preloadTextures();
	
	    CullState cullState = display.getRenderer().createCullState();
	    cullState.setCullFace(CullState.Face.None);
	    cullState.setEnabled(true);
	    skybox.setRenderState(cullState);
	
	    ZBufferState zState = display.getRenderer().createZBufferState();
	    zState.setEnabled(false);
	    skybox.setRenderState(zState);
	
	    FogState fs = display.getRenderer().createFogState();
	    fs.setEnabled(false);
	    skybox.setRenderState(fs);
	
	    skybox.setLightCombineMode(Spatial.LightCombineMode.Off);
	    skybox.setCullHint(Spatial.CullHint.Never);
	    skybox.setTextureCombineMode(TextureCombineMode.Replace);
	    skybox.updateRenderState();
	
	    skybox.lockBounds();
	    skybox.lockMeshes();
	
	    return skybox;
	}

}

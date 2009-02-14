
package org.hacker.engine.war.jme;

import java.util.logging.Logger;

import org.hacker.engine.war.Terra;

import com.jme.math.FastMath;
import com.jme.system.JmeException;
import com.jmex.terrain.util.AbstractHeightMap;

public class TerrainHeightMap extends AbstractHeightMap {
    private static final Logger logger = Logger
            .getLogger(TerrainHeightMap.class.getName());
    private Terra terra;
    
    public TerrainHeightMap(int size, Terra terra) {
        if(!FastMath.isPowerOfTwo(size)) {
            throw new JmeException("Size must be (2^N) sized.");
        }
        this.size = size;
        this.terra = terra;

        load();
    }

    public boolean load() {
        float height;
        double heightReducer;
        float[][] tempBuffer;

        if (null != heightData) {
            unloadHeightMap();
        }

        height = size / 2f;

        heightData = new float[size*size];
        tempBuffer = new float[size][size];
        
        float[] tval = { 0f, 40f, -40f };

        //displace the center of the square.
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
//                tempBuffer[i][j] = (float) (Math.random() * height);
                double x = terra.dimX * i / size;
                double y = terra.dimY * j / size;
                int terrain = terra.getTerrain(x, y);
                tempBuffer[i][j] = tval[terrain];
            }
        }

        normalizeTerrain(tempBuffer);

        //transfer the new terrain into the height map.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setHeightAtPoint(tempBuffer[i][j], i, j);
            }
        }

        logger.info("Created Heightmap using terra");
        return true;
    }
}

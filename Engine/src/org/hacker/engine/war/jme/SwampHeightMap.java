
package org.hacker.engine.war.jme;

import java.util.logging.Logger;

import org.hacker.engine.war.Terra;
import org.hacker.engine.war.Terrain;

import com.jmex.terrain.util.AbstractHeightMap;

/**
 * like source height map, but floor the swamp areas
 */
public class SwampHeightMap extends AbstractHeightMap {
    private static final Logger logger = Logger
            .getLogger(SwampHeightMap.class.getName());
    
        private AbstractHeightMap srcMap;

        private Terra terra;

        public SwampHeightMap(AbstractHeightMap srcMap, Terra terra) {
                this.srcMap = srcMap;
                this.size = srcMap.getSize();
        this.terra = terra;
                load();
        }

        /**
         */
        public boolean load() {
                if (null != heightData) {
                        unloadHeightMap();
                }

                heightData = new float[size*size];

                float[] temp1 = srcMap.getHeightMap();

                for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                                heightData[i + (j*size)] = temp1[i + (j * size)];
                double x = terra.dimX * i / size;
                double y = terra.dimY * j / size;
                int terrain = terra.getTerrain(x, y);
                if (terrain == Terrain.SWAMP)
                        heightData[i + (j*size)] = 0;
                        }
                }

                logger.info("Created swamp heightmap");

                return true;
        }

}

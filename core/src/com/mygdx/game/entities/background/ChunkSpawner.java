package com.mygdx.game.entities.background;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 09.03.2018.
 */

class ChunkSpawner {

    /*
    map ids:
    1 - random pipes
    2 - squares
    3 - randomness
    4 - mosaic
    5 -
    */

    static boolean[][]  spawnChunk(int size, int map_id){
        boolean[][] chunkMap;
        chunkMap = new boolean[size][size];
        switch (map_id){
            case 1: // random pipes
                if (size > 14) break;
                int flag = MathUtils.random(2);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        switch (flag){
                            case 0:
                                if (j % 3 == 0) chunkMap[i][j] = true;
                                break;
                            case 1:
                                if (i % 3 == 0) chunkMap[i][j] = true;
                                break;
                            case 2:
                                if (i % 3 == 0 || j % 3 == 0) chunkMap[i][j] = true;
                                break;
                        }
                    }
                }
                break;
            case 2: // squares
                if (size > 11) break;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (j % 3 == 0 || i % 3 == 0) chunkMap[i][j] = true;
                    }
                }
                break;
            case 3: // randomness
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        chunkMap[i][j] = Utils.randomBoolean(0.7f);
                    }
                }
                break;
            case 4: // mosaic
                if (size > 11) break;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if ((i + (size) * j) % 2 == 1) chunkMap[i][j] = true;
                    }
                }
                break;
            case 5: // chess
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        chunkMap[i][j] = Utils.randomBoolean(0.7f);
                    }
                }
                break;
        }


        return chunkMap;
    }
}

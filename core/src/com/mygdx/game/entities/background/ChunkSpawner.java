package com.mygdx.game.entities.background;

/**
 * Created by Asus123 on 09.03.2018.
 */

class ChunkSpawner {

    static boolean[][] spawnChunk(int size, String key_tag){
        float probability = 0.7f;

        boolean[][] chunkMap;
        chunkMap = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i % 3 == 0 || j % 3 == 0) chunkMap[i][j] = true;
            }
        }
        return chunkMap;
    }

    enum BridgeInChunk{}
}

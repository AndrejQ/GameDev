package com.mygdx.game.entities.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 03.03.2018.
 */

public class Background {
    Layer[] layers;
    private int map_id;
    public Background(int layersNumber) {
//        map_id = MathUtils.random(1, 4);
        map_id = layersNumber;
        Gdx.app.log("Background", "map id = " + map_id);
        layers = new Layer[layersNumber];
        Color randomColor = Utils.randomColor();
        for (int i = 0; i < layersNumber; i++) {
            // TODO: 18.03.2018 config color: gray || colored
            layers[i] = new Layer(i, Utils.shadesOfColor(randomColor, layersNumber - i, layersNumber));
        }
    }

    // for uploading new chunks around gg
    public void update(Vector2 cameraPosition){
        for (Layer layer : layers) {
            layer.update(cameraPosition);
        }
    }

    public void render(ShapeRenderer renderer){
        for (int i = layers.length - 1; i >= 0; i--) {
            layers[i].render(renderer);
        }
    }

    private class Layer {
        Vector2 cameraPositionParallax;
        int layerIndex;
        float depth;
        float depthChunkSize;
        Color colorTheme;
        Array<Chunk> allChunks;
        Chunk[] active9Chunks;  // 0 1 2
                                // 3 4 5
                                // 6 7 8

        public Layer(int layerIndex, Color colorTheme) {
            this.colorTheme = colorTheme;
            active9Chunks = new Chunk[9];
            cameraPositionParallax = new Vector2();
            depth = 10 * layerIndex;

            // depthChunkSize grows up with depth
            depthChunkSize = Utils.pow(Constants.BACKGROUND_CHUNK_SIZE, (int) depth / 5);
            depthChunkSize = Constants.BACKGROUND_CHUNK_SIZE * (1 + depth / 10);
            Gdx.app.log("layer: "  + layerIndex, "size: " + depthChunkSize);

            active9Chunks[0] = new Chunk(-1, 1, (int) depthChunkSize);
            active9Chunks[1] = new Chunk(0, 1, (int) depthChunkSize);
            active9Chunks[2] = new Chunk(1, 1, (int) depthChunkSize);
            active9Chunks[3] = new Chunk(-1, 0, (int) depthChunkSize);
            active9Chunks[4] = new Chunk(0, 0, (int) depthChunkSize);
            active9Chunks[5] = new Chunk(1, 0, (int) depthChunkSize);
            active9Chunks[6] = new Chunk(-1, -1, (int) depthChunkSize);
            active9Chunks[7] = new Chunk(0, -1, (int) depthChunkSize);
            active9Chunks[8] = new Chunk(1, -1, (int) depthChunkSize);
            allChunks = new Array<Chunk>(active9Chunks);
        }

        private void update(Vector2 cameraPosition){
            cameraPositionParallax = new Vector2(cameraPosition);
            int ggChunkIndex = -1;
            // check if gg is in middle chunk
            if (active9Chunks[4].contains(cameraPosition)) return;
            for (int i = 0; i < 9; i++) {
                if (active9Chunks[i].contains(cameraPosition)) ggChunkIndex = i;
            }
            switch (ggChunkIndex){
                case 1:
                    active9Chunks = shift(active9Chunks, 0, 1);
                    break;
                case 3:
                    active9Chunks = shift(active9Chunks, -1, 0);
                    break;
                case 5:
                    active9Chunks = shift(active9Chunks, 1, 0);
                    break;
                case 7:
                    active9Chunks = shift(active9Chunks, 0, -1);
                    break;
                case 0:
                    active9Chunks = shift(active9Chunks, -1, 1);
                    break;
                case 2:
                    active9Chunks = shift(active9Chunks, 1, 1);
                    break;
                case 6:
                    active9Chunks = shift(active9Chunks, -1, -1);
                    break;
                case 8:
                    active9Chunks = shift(active9Chunks, 1, -1);
                    break;
            }
        }

        // shiftX right == 1, left == -1, shiftY up == 1, down == -1
        private Chunk[] shift(Chunk[] active9Chunks, int shiftX, int shiftY){
            Chunk[] newChunks = new Chunk[9];
            for (int i = 0; i < 9; i++) {
//                newChunks[i] = new Chunk(active9Chunks[i].xOrder + shiftX,
//                        active9Chunks[i].yOrder + shiftY,
//                        active9Chunks[i].chunkSize);
                newChunks[i] = uploadChunk(active9Chunks[i].xOrder + shiftX,
                        active9Chunks[i].yOrder + shiftY);
                newChunks[i].setActiveChunkOrder(i);
            }
            return newChunks;
        }

        private Chunk uploadChunk(int xOrder, int yOrder){
            for (Chunk chunk : allChunks){
                if (chunk.xOrder == xOrder && chunk.yOrder == yOrder) return chunk;
            }
            Chunk newChunk = new Chunk(xOrder, yOrder, active9Chunks[0].chunkSize);
            allChunks.add(newChunk);
            return newChunk;
        }

        private void render(ShapeRenderer renderer){
            for (Chunk chunk : active9Chunks) {
                chunk.render(renderer);
            }
        }

        private class Chunk {
            private int xOrder, yOrder, chunkSize, activeChunkOrder;
            private float cellSize = Constants.BACKGROUND_CELL_SIZE * Constants.BACKGROUND_CHUNK_SIZE / depthChunkSize;
            Cell[][] cells;

            // chunkSize -> number of cells in one dimension
            public Chunk(int xOrder, int yOrder, int chunkSize) {
                boolean up, down, left, right; // cell existence
                this.xOrder = xOrder;
                this.yOrder = yOrder;
                this.chunkSize = chunkSize;
                cells = new Cell[chunkSize][chunkSize];
                boolean[][] chunkMap = ChunkSpawner.spawnChunk(chunkSize, map_id);
                for (int i = 0; i < cells.length; i++) {
                    for (int j = 0; j < cells[0].length; j++) {
//                        Vector2 cellPosition = new Vector2(
//                                xOrder * chunkSize * cellSize + (i % chunkSize) * cellSize,
//                                yOrder * chunkSize * cellSize + (i / chunkSize) * cellSize
//                        );
                        Vector2 cellPosition = new Vector2(
                                xOrder * chunkSize * cellSize + i * cellSize,
                                yOrder * chunkSize * cellSize + j * cellSize
                        );
                        cells[i][j] = new Cell(cellPosition, cellSize, colorTheme, !chunkMap[i][j]);
                    }
                }
            }

            private void render(ShapeRenderer renderer){
                for (int i = 0; i < cells.length; i++){
                    for (int j = 0; j < cells[0].length; j++) {
                        cells[i][j].render(renderer);
                    }
                }
            }

            boolean contains(Vector2 cameraPosition){
                return new Rectangle(xOrder * chunkSize * cellSize,
                        yOrder * chunkSize * cellSize,
                        chunkSize * cellSize,
                        chunkSize * cellSize)
                        .contains(cameraPosition.x - cameraPositionParallax.x * (1 - 1 / (0.5f * depth + 1)),
                                cameraPosition.y - cameraPositionParallax.y * (1 - 1 / (0.5f * depth + 1)));
            }

            public void setActiveChunkOrder(int activeChunkOrder) {
                this.activeChunkOrder = activeChunkOrder;
            }

            class Cell{
                Vector2 position;
                boolean isTransparent;
                private float size;
                private Color color;

                private Cell(Vector2 position, float size, Color color, boolean isTransparent) {
                    this.isTransparent = isTransparent;
                    this.position = position;
                    this.size = size;
                    float colorRandomness = MathUtils.random(-1f, 1f) / 30;
                    this.color = new Color(
                            color.r + colorRandomness,
                            color.g + colorRandomness,
                            color.b + colorRandomness,
                            color.a);
                }

                public void render(ShapeRenderer renderer){
                    if (isTransparent) return;

                    float x = position.x + cameraPositionParallax.x * (1 - 1 / (0.5f * depth + 1));
                    float y = position.y + cameraPositionParallax.y * (1 - 1 / (0.5f * depth + 1));

                    // TODO: 22.03.2018 vrode rabotaet no ne osobo :/
                    if (Utils.outOfScreen(new Vector2(x, y), cameraPositionParallax, size)) return;
//                    if (activeChunkOrder <= 2){
//                        if (Utils.outOfVerticalUp(new Vector2(x, y), cameraPositionParallax, size)){
//                            return;
//                        }
//                    } else if (activeChunkOrder >= 6){
//                        if (Utils.outOfVerticalDown(new Vector2(x, y), cameraPositionParallax, size)){
//                            return;
//                        }
//                    }

                    renderer.setColor(color);
                    renderer.set(ShapeRenderer.ShapeType.Filled);
                    renderer.rect(x, y, size, size);
                }
            }
        }


    }
}

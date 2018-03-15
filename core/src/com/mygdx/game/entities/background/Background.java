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
    public Background(int layersNumber) {
        layers = new Layer[layersNumber];
        for (int i = 0; i < layersNumber; i++) {
            layers[i] = new Layer(i, Utils.shadesOfColor(Color.LIGHT_GRAY, layersNumber - i, layersNumber));
        }
    }

    // for uploading new chunks around gg
    public void update(Vector2 cameraPosition){
        for (Layer layer : layers) {
            layer.update(cameraPosition);
        }
    }

    public void render(ShapeRenderer renderer){
//        for (Layer layer : layers) {
//            layer.render(renderer);
//        }
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
            int xOrder;
            int yOrder;
            private int chunkSize;
            private float cellSize = Constants.BACKGROUND_CELL_SIZE * Constants.BACKGROUND_CHUNK_SIZE / depthChunkSize;
            Cell[] cells;

            // chunkSize -> number of cells in one dimension
            public Chunk(int xOrder, int yOrder, int chunkSize) {
                this.xOrder = xOrder;
                this.yOrder = yOrder;
                this.chunkSize = chunkSize;
                cells = new Cell[chunkSize * chunkSize];
                for (int i = 0; i < cells.length; i++) {
                    Vector2 cellPosition = new Vector2(
                            xOrder * chunkSize * cellSize + (i % chunkSize) * cellSize,
                            yOrder * chunkSize * cellSize + (i / chunkSize) * cellSize
                    );
                    cells[i] = new Cell(cellPosition, cellSize, colorTheme, Utils.randomBoolean(0.7f));
                }
            }

            private void render(ShapeRenderer renderer){
                for (int i = 0; i < cells.length; i++){
                    cells[i].render(renderer);
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
                this.color = new Color(color.r + colorRandomness,
                        color.g + colorRandomness,
                        color.b + colorRandomness,
                        color.a);
            }

            public void render(ShapeRenderer renderer){
                if (isTransparent) return;
                renderer.setColor(color);
                renderer.set(ShapeRenderer.ShapeType.Filled);
                renderer.rect(position.x + cameraPositionParallax.x * (1 - 1 / (0.5f * depth + 1)),
                        position.y + cameraPositionParallax.y * (1 - 1 / (0.5f * depth + 1)),
                        size, size);
            }
        }
    }
}

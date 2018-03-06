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

import java.util.ArrayList;

/**
 * Created by Asus123 on 03.03.2018.
 */

public class Background {
    Layer[] layers;
    public Background(int layersNumber) {
        layers = new Layer[layersNumber];
        for (int i = 0; i < layersNumber; i++) {
            layers[i] = new Layer(0, Utils.randomColor());
        }
    }

    // for uploading new chunks around gg
    public void update(Vector2 ggPosition){
        for (Layer layer : layers) {
            layer.update(ggPosition);
        }
    }

    public void render(ShapeRenderer renderer){
        for (Layer layer : layers) {
            layer.render(renderer);
        }
    }

    private class Layer {
        float depth;
        Color colorTheme;
        Array<Chunk> allChunks;
        Chunk[] active9Chunks;  // 0 1 2
                                // 3 4 5
                                // 6 7 8

        public Layer(float depth, Color colorTheme) {
            this.colorTheme = colorTheme;
            active9Chunks = new Chunk[9];

            active9Chunks[0] = new Chunk(-1, 1, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[1] = new Chunk(0, 1, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[2] = new Chunk(1, 1, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[3] = new Chunk(-1, 0, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[4] = new Chunk(0, 0, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[5] = new Chunk(1, 0, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[6] = new Chunk(-1, -1, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[7] = new Chunk(0, -1, Constants.BACKGROUND_CHUNK_SIZE);
            active9Chunks[8] = new Chunk(1, -1, Constants.BACKGROUND_CHUNK_SIZE);
            allChunks = new Array<Chunk>(active9Chunks);
            this.depth = depth;
        }

        private void update(Vector2 ggPosition){
            int ggChunkIndex = -1;
            for (int i = 0; i < 9; i++) {
                if (active9Chunks[i].contains(ggPosition)) ggChunkIndex = i;
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
            private float cellSize = Constants.BACKGROUND_CELL_SIZE;
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
                    cells[i] = new Cell(cellPosition, cellSize, colorTheme);
                }
            }

            private void render(ShapeRenderer renderer){
                for (int i = 0; i < cells.length; i++){
                    cells[i].render(renderer);
                }
            }

            boolean contains(Vector2 ggPosition){
                return new Rectangle(xOrder * chunkSize * cellSize,
                        yOrder * chunkSize * cellSize,
                        chunkSize * cellSize,
                        chunkSize * cellSize)
                        .contains(ggPosition);
            }
        }

        class Cell{
            Vector2 position;
            private float size;
            private Color color;

            private Cell(Vector2 position, float size, Color color) {
                this.position = position;
                this.size = size;
                this.color = new Color(Math.min(color.r + MathUtils.random(-1f, 1f) / 30, 1),
                        Math.min(color.g + MathUtils.random(-1f, 1f) / 30, 1),
                        Math.min(color.b + MathUtils.random(-1f, 1f) / 30, 1),
                        color.a);
            }

            public void render(ShapeRenderer renderer){
                renderer.setColor(color);
                renderer.set(ShapeRenderer.ShapeType.Filled);
                renderer.rect(position.x, position.y, size, size);
            }
        }
    }
}

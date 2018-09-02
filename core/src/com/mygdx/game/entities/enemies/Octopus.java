package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GG;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.entities.physicalObjects.Chain;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;

public class Octopus extends Enemy {
    Chain tentackle;

    public Octopus(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        tentackle = new Chain(new Vector2(position), new Vector2(0,1).add(position), 100, 10, 10, level);
        radius = 10;
    }

    @Override
    void velocityUpdate() {
        super.velocityUpdate();
    }

    @Override
    public void missileCatch(Missile missile) {
        super.missileCatch(missile);
    }

    @Override
    public void collideWithOtherEnemy(Enemy otherEnemy) {
        super.collideWithOtherEnemy(otherEnemy);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        tentackle.points[0].position = new Vector2(position);
        tentackle.update(delta);
        // attach chain to the octopus
        tentackle.points[0].position = new Vector2(position);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.setColor(Color.BLACK);
        renderer.rect(position.x-5, position.y-5, 5, 10);
        tentackle.render(batch, renderer);
    }

    @Override
    public void collideWithGG(GG gg) {
        super.collideWithGG(gg);
    }

    @Override
    public void death() {
        super.death();
    }
}

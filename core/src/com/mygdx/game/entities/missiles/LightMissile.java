package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.particles.Light;
import com.mygdx.game.entities.particles.SparkleParticle;
import com.mygdx.game.entities.particles.TriangleParticle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 15.12.2017.
 */

public class LightMissile extends Missile {

    Light lightParticle;

    public LightMissile(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        startTime = TimeUtils.nanoTime();
        damage = Constants.LIGHT_MISSILE_DAMAGE;
        lifetime = Constants.LIGHT_MISSILE_LIFETIME;

        // add light
        lightParticle = new Light(this.position, this.velocity, 2*Constants.LIGHT_MISSILE_LENGTH, level);
        level.particleManager.particles.add(lightParticle);
    }


    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);

        float s = Constants.LIGHT_MISSILE_LENGTH * Constants.LIGHT_MISSILE_WIDTH;
        float dynamicalLength = Constants.LIGHT_MISSILE_LENGTH * (1 -
                0.4f * MathUtils.sin(Constants.LIGHT_MISSILE_WOBBLE_FREQUENCY * Utils.timeElapsed(startTime)));
        float dynamicalWidth = s / dynamicalLength;

        Vector2 norVelocity = new Vector2(velocity).nor();
        Vector2 front = new Vector2(position).add(new Vector2(norVelocity).scl(dynamicalLength / 2));

//        // (position - velocity.nor, position)
//        renderer.setColor(Color.YELLOW);
//        renderer.rectLine(front,
//                new Vector2(position).add(new Vector2(norVelocity).scl(- dynamicalLength)),
//                dynamicalWidth);
//
//        front = new Vector2(position).add(new Vector2(norVelocity).scl(0.6f * dynamicalLength / 2));
//
//        renderer.setColor(Color.WHITE);
//        renderer.rectLine(new Vector2(front).add(new Vector2(norVelocity).scl(-1+0.6f)),
//                new Vector2(position).add(new Vector2(norVelocity).scl(- 0.6f * dynamicalLength)),
//                0.6f * dynamicalWidth);

        // (position - velocity.nor, position)
        renderer.setColor(Color.YELLOW);
        drawMissile(renderer, front, norVelocity, dynamicalLength, dynamicalWidth, 1);
        front = new Vector2(position).add(new Vector2(norVelocity).scl(0.6f * dynamicalLength / 2));
        renderer.setColor(Color.WHITE);
        drawMissile(renderer, front, norVelocity, dynamicalLength, dynamicalWidth, 0.6f);

    }

    // draw fancy missile
    private void drawMissile(ShapeRenderer renderer, Vector2 front, Vector2 norVelocity, float dynamicalLength, float dynamicalWidth, float coefficient){
        renderer.rectLine(new Vector2(front).add(new Vector2(norVelocity).scl(-1+coefficient)),
                new Vector2(position).add(new Vector2(norVelocity).scl(- coefficient * dynamicalLength)),
                coefficient * dynamicalWidth);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        triangleParticleTrace();
    }

    private void triangleParticleTrace(){
        Vector2[] positions = Utils.pointsOnLineWithDensity(position, previousPosition,
                Constants.TRIANGLE_PARTICLE_DENSITY);

        // cycles for making appropriate array of sparkles
        TriangleParticle[] triangleParticles = new TriangleParticle[positions.length];
        for (int i = 0; i < positions.length; i++) {
            triangleParticles[i] = new TriangleParticle(positions[i], new Vector2(), level);
        }
        sparkleTrace(triangleParticles);
    }

    @Override
    public void missileExplode(Enemy hostEnemy, Vector2 direction) {
//        level.particleManager.particles.removeValue(lightParticle, false);

        float Bound = Constants.SPARKLE_PARTICLE_START_VELOCITY;
        float startVelocity = Constants.SPARKLE_PARTICLE_START_VELOCITY;

        // TODO: 19.12.2017 redirect sparkles (to be able to make regular explosions of sparkles)
        for (int i = 0; i < Constants.SPARKLE_PARTICLE_NUMBER; i++) {
            //speed of particles are random + direction
            level.particleManager.particles.add(new SparkleParticle(hostEnemy, new Vector2(position),
                    //directed velocity of sparkle
                    Utils.randomVector(Bound).add(new Vector2(direction).scl(startVelocity)), level));
        }
    }
}

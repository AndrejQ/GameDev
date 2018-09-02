package com.mygdx.game.entities.physicalObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GG;
import com.mygdx.game.entities.MaterialPoint;
import com.mygdx.game.entities.background.Background;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Utils;

public class Chain {
    Link[] links;
    public MaterialPoint[] points;
    Color color = new Color();
    int n;
    float chainLength, widthMax, linkMass, diametr, springLen;
    float k = 30;

//    n - number of links
    public Chain(Vector2 basePosition, Vector2 tailPos, float chainLength, int n, float widthMax, Level level) {
        this.chainLength = chainLength;
        this.n = n;
        springLen = chainLength / n;
        links = new Link[n];
        points = new MaterialPoint[n + 1];
        this.widthMax = widthMax;
        linkMass = 3;
        for (int i = 0; i < n + 1; i++) {
            points[i] = new MaterialPoint(new Vector2(basePosition)
                    .add(new Vector2(tailPos).sub(basePosition).scl(i * springLen)),
                    new Vector2(),
                    level);
            points[i].setFriction(0.3f);
        }
        for (int i = 0; i < n; i++) {
            links[i] = new Link(
                    new Vector2(), // position is counting in link.update()
                    new Vector2(),
                    level
            );
            links[i].setMass(linkMass);
        }
    }

    public void update(float delta){
        for (int i = 0; i < n; i++) {
            float currentLen = points[i].position.dst(points[i + 1].position);
            links[i].currentLength = currentLen;
            float delta_len = springLen - currentLen;
//            !!!!! pulse conservation law
            points[i].velocity.add(points[i].position.cpy().sub(points[i + 1].position)
                    .scl(k * delta_len / currentLen));
            points[i + 1].velocity.add(points[i].position.cpy().sub(points[i + 1].position)
                    .scl(k * -delta_len / currentLen));
        }

//       pushing tips
//        points[0].velocity.add(points[0].position.cpy().sub(points[n].position).scl(0.08f));
//        points[n].velocity.add(points[0].position.cpy().sub(points[n].position).scl(-0.08f));


        // update all points
        for (int i = 0; i < n + 1; i++) {
            // fixed rope explosion
            points[i].update(Math.min(delta, 1 / 20f));
        }

        for (int i = 0; i < n; i++) {
            links[i].update(delta);
        }

        // grani4nie uslovia
        links[0].v1 = new Vector2(points[0].position);
        links[0].v2 = new Vector2(points[0].position);
        links[n - 1].v3 = new Vector2(points[n].position);
        links[n - 1].v4 = new Vector2(points[n].position);

        for (int i = 0; i < n - 1; i++) {
            Vector2 perpendicular1 = new Vector2(points[i + 1].position).sub(points[i].position).scl(1 / links[i].currentLength).rotate90(1);
            Vector2 perpendicular2 = new Vector2(points[i + 2].position).sub(points[i + 1].position).scl(1 / links[i + 1].currentLength).rotate90(1);
            Vector2 perpendicular = new Vector2(perpendicular1).add(perpendicular2).nor();

            // current width
            perpendicular.scl(widthMax * (n - i - 1) / n);

            links[i].v3 = new Vector2(points[i + 1].position).add(perpendicular.cpy().scl(-1));
            links[i].v4 = new Vector2(points[i + 1].position).add(perpendicular);
            links[i + 1].v1 = new Vector2(points[i + 1].position).add(perpendicular.cpy().scl(-1));
            links[i + 1].v2 = new Vector2(points[i + 1].position).add(perpendicular);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        for (int i = 0; i < n; i++) {
            links[i].render(batch, renderer);
//            renderer.setColor(Color.BLACK);
//            renderer.rectLine(points[i].position, points[i + 1].position, 2);
        }
    }

    class Link extends Enemy{
        Vector2 v1 = new Vector2();
        Vector2 v2 = new Vector2();
        Vector2 v3 = new Vector2();
        Vector2 v4 = new Vector2();
        float currentLength;

        public Link(Vector2 position, Vector2 velocity, Level level) {
            super(position, velocity, level);
            this.radius = springLen / 2;
        }

        public void update(float delta) {
            position = new Vector2(v1).add(v2).add(v3).add(v4).scl(0.25f);
        }

        public void render(SpriteBatch batch, ShapeRenderer renderer) {
//            renderer.setColor(color);
            renderer.triangle(v1.x, v1.y, v2.x, v2.y, v4.x, v4.y);
            renderer.triangle(v1.x, v1.y, v3.x, v3.y, v4.x, v4.y);
        }
    }
}

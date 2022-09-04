package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysX {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    public PhysX() {
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    public Body addObject(RectangleMapObject object, BodyDef.BodyType customType, String name) {
        Rectangle rect = object.getRectangle();
        BodyDef.BodyType type;

        if (customType == null) {
            String objectBodyType = object.getProperties().get("BodyType", String.class);
            switch (objectBodyType) {
                case "KinematicBody": {
                    type = BodyDef.BodyType.KinematicBody;
                    break;
                }
                case "DynamicBody": {
                    type = BodyDef.BodyType.DynamicBody;
                    break;
                }
                default: {
                    type = BodyDef.BodyType.StaticBody;
                    break;
                }
            }
        } else {
            type = customType;
        }

        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        def.type = type;
        def.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

        Float defaultGravityScale = object.getProperties().get("gravityScale", Float.class);
        def.gravityScale = defaultGravityScale != null ? defaultGravityScale : 1;

        polygonShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        fdef.shape = polygonShape;

        Float friction = object.getProperties().get("friction", Float.class);
        fdef.friction = friction != null ? friction : 0.1f;

        fdef.density = 1;

        Float restitution = object.getProperties().get("restitution", Float.class);
        fdef.restitution = restitution != null ? restitution : 0;

        Body body = world.createBody(def);
        body.createFixture(fdef)
                .setUserData(name);

        polygonShape.dispose();
        return body;
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public void step() {
        world.step(1/60f, 3, 3);
    }

    public void debugDraw(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}

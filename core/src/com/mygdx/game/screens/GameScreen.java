package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.CustomAnimation;
import com.mygdx.game.Main;
import com.mygdx.game.PhysX;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final CustomAnimation animation;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private static final float STEP = 130000f;
    private final TiledMap map;
    private final Rectangle mapSize;
    //    private final ShapeRenderer shapeRenderer;
    private final int[] bg;
    private final int[] l1;
    private final PhysX physX;
    private final RectangleMapObject hero;
    private final Body body;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();

        animation = new CustomAnimation("atlas/skeleton/skeleton.atlas", 1, 3, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("map/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        bg = new int[1];
        bg[0] = map.getLayers().getIndex("фон");
        l1 = new int[1];
        l1[0] = map.getLayers().getIndex("трава");

        mapSize = ((RectangleMapObject) map.getLayers().get("объекты").getObjects().get("граница")).getRectangle();

        camera.viewportHeight = mapSize.getHeight();
        camera.viewportWidth = (camera.viewportHeight * 4) / 3.0f;

        physX = new PhysX();
        hero = (RectangleMapObject) map.getLayers().get("объекты").getObjects().get("герой");
        body = physX.addObject(hero, null, hero.getName());

        Array<RectangleMapObject> impediments = map.getLayers().get("препятствия").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject o : impediments) {
            physX.addObject(o, null, o.getName());
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float xForce = 0;
        float yForce = 0;
        boolean flipX = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xForce = -STEP;
            if (animation.getFrame().isFlipX()) {
                flipX = true;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xForce = STEP;
            if (!animation.getFrame().isFlipX()) {
                flipX = true;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && Math.abs(body.getLinearVelocity().y) < 0.001f) {
            yForce = STEP * 400;
        }

//        if (animation.getFrame().isFlipX() && body.getLinearVelocity().x < 0.6 || !animation.getFrame().isFlipX() && body.getLinearVelocity().x > 1) {
//            flipX = true;
//        }

        body.applyForceToCenter(xForce, yForce, true);
        hero.getRectangle().setCenter(body.getWorldCenter());


        camera.zoom = mapSize.getHeight() / Gdx.graphics.getHeight();
        camera.update();
        ScreenUtils.clear(Color.BLACK);

        animation.setTime(delta);
        animation.getFrame().flip(flipX, false);

        camera.position.x = body.getPosition().x;
        camera.position.y = mapSize.getY() + mapSize.getHeight() / 2;
        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(animation.getFrame(), hero.getRectangle().getX(), hero.getRectangle().getY(), hero.getRectangle().getWidth(), hero.getRectangle().getHeight());
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }

        mapRenderer.render(l1);

        physX.step();
        physX.debugDraw(camera);
        System.out.printf("\n x = %f, y = %f", body.getLinearVelocity().x, body.getLinearVelocity().y);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
//        animation.dispose();
        physX.dispose();
    }
}

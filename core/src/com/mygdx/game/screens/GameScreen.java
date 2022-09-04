package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.PhysX;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
//    private final CustomAnimation animation;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private static final float STEP = 2.5f;
    private final TiledMap map;
    private final Rectangle mapSize;
    private final RectangleMapObject hero;
//    private final ShapeRenderer shapeRenderer;
    private final int[] bg;
    private final int[] l1;
    private final PhysX physX;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
//        animation = new CustomAnimation("atlas/black_cat.atlas", 0, 0, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("map/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        bg = new int[1];
        bg[0] = map.getLayers().getIndex("фон");
        l1 = new int[1];
        l1[0] = map.getLayers().getIndex("трава");

        hero = (RectangleMapObject) map.getLayers().get("объекты").getObjects().get("герой");
        mapSize = ((RectangleMapObject) map.getLayers().get("объекты").getObjects().get("граница")).getRectangle();

        camera.position.x = hero.getRectangle().getX();

        camera.viewportHeight = mapSize.getHeight();
        camera.viewportWidth = (camera.viewportHeight * 4) / 3.0f;
        camera.position.y = mapSize.getY() + mapSize.getHeight() / 2;
//        shapeRenderer = new ShapeRenderer();
        physX = new PhysX();

        Array<RectangleMapObject> impediments = map.getLayers().get("препятствия").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject o : impediments) {
            physX.addObject(o, BodyDef.BodyType.StaticBody, o.getName());
        }
        physX.addObject(hero, BodyDef.BodyType.DynamicBody, hero.getName());


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += STEP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= STEP;
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.UP) ) {
//            camera.position.y += STEP;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            camera.position.y -= STEP;
//        }

        camera.zoom = mapSize.getHeight() / Gdx.graphics.getHeight();
        camera.update();
        ScreenUtils.clear(Color.BLACK);

//        animation.setTime(delta);

        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        batch.draw(animation.getFrame(), mapSize.getX(), mapSize.getY());
        batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }

//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.GOLD);

//        Array<RectangleMapObject> impediments = map.getLayers().get("препятствия").getObjects().getByType(RectangleMapObject.class);
//        for (RectangleMapObject o : impediments) {
//            Rectangle mapObject = o.getRectangle();
//            shapeRenderer.rect(mapObject.getX(), mapObject.getY(), mapObject.getWidth(), mapObject.getHeight());
//        }
//        shapeRenderer.rect(mapSize.getX(), mapSize.getY(), mapSize.getWidth(), mapSize.getHeight());
//        shapeRenderer.end();

        mapRenderer.render(l1);

        physX.step();
        physX.debugDraw(camera);
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

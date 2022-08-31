package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
//    private final CustomAnimation animation;
    private final OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static final float STEP = 2.5f;
    private Rectangle mapSize;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
//        animation = new CustomAnimation("", 0, 0, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("map/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        RectangleMapObject cameraViewRectangle = (RectangleMapObject) map.getLayers().get("объекты").getObjects().get("камера");
        mapSize = ((RectangleMapObject) map.getLayers().get("объекты").getObjects().get("граница")).getRectangle();

        camera.position.x = cameraViewRectangle.getRectangle().getX();

        camera.viewportHeight = mapSize.getHeight();
        camera.viewportWidth = (camera.viewportHeight * 4) / 3.0f;
        camera.position.y = mapSize.getHeight() / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && camera.position.x + STEP <= mapSize.getWidth()) {
            camera.position.x += STEP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && camera.position.x - STEP > 0) {
            camera.position.x -= STEP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && camera.position.y + STEP < mapSize.getHeight()) {
            camera.position.y += STEP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && camera.position.y - STEP > 0) {
            camera.position.y -= STEP;
        }

        camera.zoom = mapSize.getHeight() / Gdx.graphics.getHeight();
        camera.update();
        ScreenUtils.clear(Color.BLACK);

//        animation.setTime(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        batch.draw(animation.getFrame(), 0, 0);
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }

//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.GOLD);
//        shapeRenderer.rect(mapSize.getX(), mapSize.getY(), mapSize.getWidth(), mapSize.getHeight());
//        shapeRenderer.end();

        /*
         * Для отладки
         */
        Gdx.graphics.setTitle("x = " + camera.position.x);
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
//        animation.dispose();
    }
}

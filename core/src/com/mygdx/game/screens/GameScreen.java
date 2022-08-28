package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.CustomAnimation;
import com.mygdx.game.Main;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final CustomAnimation animation;
    private final OrthographicCamera camera;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        animation = new CustomAnimation("", 0, 0, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(Color.ORANGE);

        animation.setTime(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(animation.getFrame(), 0, 0);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
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
        animation.dispose();
    }
}

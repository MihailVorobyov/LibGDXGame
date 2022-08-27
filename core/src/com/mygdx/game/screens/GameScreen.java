package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.CustomAnimation;
import com.mygdx.game.Main;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final CustomAnimation animation;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        animation = new CustomAnimation("", 0, 0, Animation.PlayMode.LOOP);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ORANGE);

        animation.setTime(delta);
        
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

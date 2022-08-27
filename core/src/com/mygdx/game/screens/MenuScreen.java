package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;

public class MenuScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture img;
    private final Rectangle startRect;
    private final ShapeRenderer shapeRenderer;

    public MenuScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture("menu.png");
        startRect = new Rectangle(0, 0, img.getWidth(), img.getHeight());
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ORANGE);

        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(startRect.x, startRect.getY(), startRect.getWidth(), startRect.getHeight());
        shapeRenderer.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (startRect.contains(x, y)) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
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
        img.dispose();
        shapeRenderer.dispose();
    }
}

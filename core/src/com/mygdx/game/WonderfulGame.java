package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.input;

public class WonderfulGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private static long clicks;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("cat.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		int x = input.getX() - img.getWidth() / 2;
		int y = graphics.getHeight() - input.getY() - img.getHeight() / 2;
		if (input.isButtonJustPressed(Input.Buttons.LEFT)) {
			clicks++;
			graphics.setTitle("Left mouse pressed " + clicks + " times");
		}
		batch.draw(img, x, y);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

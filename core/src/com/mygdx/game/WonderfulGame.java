package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.input;

public class WonderfulGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private CustomAnimation animation;
	private String direction;	// Направление движения
	private int xPosition;	// Положение персонажа на жкране
	private float speed;	// Базовая скорость движения
	private float realSpeed;	// Действующая скорость движения
	@Override
	public void create () {
		batch = new SpriteBatch();
		animation = new CustomAnimation("img_1.png", 6, 1, Animation.PlayMode.LOOP);
		direction = "right";
		speed = 8.0f;
		realSpeed = speed;
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		animation.setTime(graphics.getDeltaTime());

		boolean rightBorderCollision = "right".equals(direction) && realSpeed > 0 && xPosition >= (graphics.getWidth() - animation.getFrame().getRegionWidth());
		boolean leftBorderCollision = "left".equals(direction) && realSpeed < 0 && xPosition <= 0;

		// Управление направлением движения
		if (rightBorderCollision || input.isKeyJustPressed(Input.Keys.LEFT)) {
			direction = "left";
		} else if (leftBorderCollision || input.isKeyJustPressed(Input.Keys.RIGHT)) {
			direction = "right";
		}

		// Меняем направление персонажа
		if (!animation.getFrame().isFlipX() && "left".equals(direction)	// Повернуть влево
				|| animation.getFrame().isFlipX() && "right".equals(direction)) {	// Повернуть вправо
			animation.getFrame().flip(true, false);
		}


		realSpeed = "right".equals(direction) ? speed : -speed;
		xPosition += realSpeed;

		batch.begin();
		batch.draw(animation.getFrame(), xPosition, 0);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		animation.dispose();
	}
}

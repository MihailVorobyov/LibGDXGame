package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.Gdx.graphics;

public class CustomAnimation {
    private final Texture texture;
    private final Animation<TextureRegion> animation;
    private float time;
    public CustomAnimation(String name, int col, int row, Animation.PlayMode playMode) {
        texture = new Texture(name);

        int xTileWidth = texture.getWidth() / col;
        int yTileWidth = texture.getHeight() / row;

        TextureRegion region0 = new TextureRegion(texture);
        TextureRegion[][] regions0 = region0.split(xTileWidth, yTileWidth);
        TextureRegion[] region1 = new TextureRegion[regions0.length * regions0[0].length];
        int count = 0;
        for (int i = 0; i < regions0.length; i++) {
            for (int j = 0; j < regions0[0].length; j++) {
                region1[count++] = regions0[i][j];
            }
        }
        animation = new Animation<>(1 / 5f, region1);
        animation.setPlayMode(playMode);

        time += graphics.getDeltaTime();
    }

    public TextureRegion getFrame() {
        return animation.getKeyFrame(time);
    }

    public void setTime(float time) {
        this.time += time;
    }

    public void zeroTime() {
        this.time = 0;
    }

    public boolean isAnimationOver() {
        return animation.isAnimationFinished(time);
    }

    public void setMode(Animation.PlayMode playMode) {
        this.animation.setPlayMode(playMode);
    }

    public void dispose() {
        texture.dispose();
    }
}

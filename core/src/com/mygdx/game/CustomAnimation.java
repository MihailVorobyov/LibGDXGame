package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.Gdx.graphics;

public class CustomAnimation {
    private final TextureAtlas atlas;
    private final Animation<TextureAtlas.AtlasRegion> animation;
    private float time;
    public CustomAnimation(String path, int col, int row, Animation.PlayMode playMode) {
        atlas = new TextureAtlas(path);
        animation = new Animation<>(1 / 24f, atlas.findRegions("fast-run"));
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
        atlas.dispose();
    }
}

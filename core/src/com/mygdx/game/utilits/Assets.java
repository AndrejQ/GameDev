package com.mygdx.game.utilits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public StarsAssets starsAssets;
    public LightAssets lightAssets;

    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        starsAssets = new StarsAssets(atlas);
        lightAssets = new LightAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class StarsAssets {

        public final Animation starBlink;

        public StarsAssets(TextureAtlas atlas) {
            Array<AtlasRegion> starsRegions = new Array<AtlasRegion>();
            starsRegions.add(atlas.findRegion("smallerStar"));
            starsRegions.add(atlas.findRegion("largeStar"));

            starBlink = new Animation(Constants.STAR_BLINK_DURATION, starsRegions, Animation.PlayMode.LOOP);
        }


    }

    public class LightAssets {
        public final AtlasRegion lihgt;

        public LightAssets(TextureAtlas atlas) {
            lihgt = atlas.findRegion("lightSprite");
        }
    }

}

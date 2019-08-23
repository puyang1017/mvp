package com.android.puy.puymvpjava.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;


public class GlideLoader implements ILoader {

    @Override
    public void init(Context context) {

    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options);
    }

    @Override
    public void loadNet(ImageView target, String url, Options options, boolean anim) {
        if (anim) {
            load(getRequestManager(target.getContext()).load(url), target, options);
        } else {
            loadNoAnim(getRequestManager(target.getContext()).load(url), target, options);
        }
    }

    @Override
    public void loadNet(ImageView target, String url, Options options, NetLoadCallback netLoadCallback) {
        load(getRequestManager(target.getContext()).load(url), target, options, netLoadCallback);
    }

    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        DrawableTypeRequest request = getRequestManager(context).load(url);
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(new SimpleTarget<GlideBitmapDrawable>() {

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (callback != null) {
                            callback.onLoadFailed(e);
                        }
                    }

                    @Override
                    public void onResourceReady(GlideBitmapDrawable resource, GlideAnimation<? super GlideBitmapDrawable> glideAnimation) {
                        if (resource != null && resource.getBitmap() != null) {
                            if (callback != null) {
                                callback.onLoadReady(resource.getBitmap());
                            }
                        }
                    }

                });
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options, boolean cache) {
        load(getRequestManager(target.getContext()).load(file), target, options, cache);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }

    private RequestManager getRequestManager(Context context) {
        if (context instanceof Activity) {
            return Glide.with((Activity) context);
        }
        return Glide.with(context);
    }

    private void load(DrawableTypeRequest request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(target);
    }

    private void loadNoAnim(DrawableTypeRequest request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId).dontAnimate();
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(target);
    }

    private void load(DrawableTypeRequest request, ImageView target, Options options, final NetLoadCallback netLoadCallback) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .listener(new RequestListener() {
                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                        netLoadCallback.onLoadReady(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        netLoadCallback.onLoadReady(true);
                        return false;
                    }
                })
                .into(target);
    }

    private void load(DrawableTypeRequest request, ImageView target, Options options, boolean cache) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        if (cache) {
            wrapScaleType(request, options)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .crossFade()
                    .into(target);
        } else {
            wrapScaleType(request, options)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .crossFade()
                    .into(target);
        }

    }

    private DrawableTypeRequest wrapScaleType(DrawableTypeRequest request, Options options) {
        if (options != null
                && options.scaleType != null) {
            switch (options.scaleType) {
                case MATRIX:
                    break;

                case FIT_XY:
                    break;

                case FIT_START:
                    break;

                case FIT_END:
                    break;

                case CENTER:
                    break;

                case CENTER_INSIDE:
                    break;

                case FIT_CENTER:
                    request.fitCenter();
                    break;

                case CENTER_CROP:
                    request.centerCrop();
                    break;
            }
        }

        return request;
    }
}

package com.android.puy.puymvpjava.imageloader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

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
    public void loadNet(ImageView target, String url, Options options, NetLoadCallback netLoadCallback, boolean anim) {
        if (anim) {
            load(getRequestManager(target.getContext()).load(url), target, options, netLoadCallback);
        } else {
            loadNoAnim(getRequestManager(target.getContext()).load(url), target, options, netLoadCallback);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        RequestBuilder<Drawable> requestBuilder = getRequestManager(context).load(url);
        if (options == null) options = Options.defaultOptions();

        RequestOptions requestOptions = new RequestOptions();
        if (options.loadingResId != Options.RES_NONE) {
            requestOptions.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId);
        }

        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions()
                .crossFade();
        wrapScaleType(requestBuilder, options)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(transitionOptions)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (callback != null) {
                            callback.onLoadFailed(errorDrawable);
                        }
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (callback != null) {
                            callback.onLoadReady(resource);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNet(Context context, String url, Options options, LoadCallback callback, boolean anim) {
        RequestBuilder<Drawable> requestBuilder = getRequestManager(context).load(url);
        if (options == null) options = Options.defaultOptions();

        RequestOptions requestOptions = new RequestOptions();
        if (options.loadingResId != Options.RES_NONE) {
            if (anim) {
                requestOptions.placeholder(options.loadingResId);
            } else {
                requestOptions.placeholder(options.loadingResId).dontAnimate();
            }
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId);
        }

        RequestBuilder<Drawable> drawableRequestBuilder = wrapScaleType(requestBuilder, options)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        if (anim) {
            drawableRequestBuilder.transition(new DrawableTransitionOptions().crossFade());
        }
        drawableRequestBuilder.into(new CustomTarget<Drawable>() {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                if (callback != null) {
                    callback.onLoadFailed(errorDrawable);
                }
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (callback != null) {
                    callback.onLoadReady(resource);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

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

    @SuppressLint("CheckResult")
    private void load(RequestBuilder<Drawable> request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        RequestOptions requestOptions = new RequestOptions();
        if (options.loadingResId != Options.RES_NONE) {
            requestOptions.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId);
        }
        wrapScaleType(request, options)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(target);
    }

    @SuppressLint("CheckResult")
    private void loadNoAnim(RequestBuilder<Drawable> request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        RequestOptions requestOptions = new RequestOptions();
        if (options.loadingResId != Options.RES_NONE) {
            requestOptions.placeholder(options.loadingResId).dontAnimate();
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(target);
    }

    @SuppressLint("CheckResult")
    private void load(RequestBuilder<Drawable> request, ImageView target, Options options, final NetLoadCallback netLoadCallback) {
        if (options == null) options = Options.defaultOptions();

        RequestOptions requestOptions = new RequestOptions();
        if (options.loadingResId != Options.RES_NONE) {
            requestOptions.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        netLoadCallback.onLoadReady(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        netLoadCallback.onLoadReady(true);
                        return false;
                    }
                })
                .into(target);
    }

    @SuppressLint("CheckResult")
    private void loadNoAnim(RequestBuilder<Drawable> request, ImageView target, Options options, final NetLoadCallback netLoadCallback) {
        if (options == null) options = Options.defaultOptions();

        RequestOptions requestOptions = new RequestOptions();
        if (options.loadingResId != Options.RES_NONE) {
            requestOptions.placeholder(options.loadingResId).dontAnimate();
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(requestOptions)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        netLoadCallback.onLoadReady(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        netLoadCallback.onLoadReady(true);
                        return false;
                    }
                })
                .into(target);
    }

    @SuppressLint("CheckResult")
    private void load(RequestBuilder<Drawable> request, ImageView target, Options options, boolean cache) {
        if (options == null) options = Options.defaultOptions();

        RequestOptions requestOptions = new RequestOptions();
        if (options.loadingResId != Options.RES_NONE) {
            requestOptions.placeholder(options.loadingResId);
        }

        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId);
        }
        if (cache) {
            wrapScaleType(request, options)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(target);
        } else {
            wrapScaleType(request, options)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(target);
        }

    }

    private RequestBuilder<Drawable> wrapScaleType(RequestBuilder<Drawable> request, Options options) {
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
                    request.centerInside();
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

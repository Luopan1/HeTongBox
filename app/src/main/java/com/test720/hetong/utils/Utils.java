package com.test720.hetong.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2017/5/22.
 */

public class Utils {

    public static void setImag(Activity context, String url, final ImageView imageView)
    {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setImageResource(R.mipmap.zanweitu);
        Glide.with(context.getApplicationContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imageView.setImageBitmap(resource);
            }
        });
    }


    public static List<String> getListString(int index)
    {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            strings.add("1");
        }
        return strings;
    }


}

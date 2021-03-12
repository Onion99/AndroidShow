package com.onion.android.app.binding_adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class CommonBindingAdapter{

    @BindingAdapter(value = {"imageUrl","placeHolder"}, requireAll = false)
    public static void loadImgUrl(ImageView view, String url, Drawable placeHolder){
        Glide.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
    }
}
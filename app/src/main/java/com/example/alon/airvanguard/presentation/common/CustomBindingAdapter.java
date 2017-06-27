package com.example.alon.airvanguard.presentation.common;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * View bindings utility class.
 */

public class CustomBindingAdapter {

    /**
     * Load the image located in the given
     * {@code url} into the image {@code view}.
     *
     * @param view {@link ImageView} that holds the loaded image.
     * @param url image url.
     */
    @BindingAdapter("bind:imageUrl")
    public static void loadImageUrl(ImageView view,String url) {
        Glide.with(view.getContext())
                .load(url)
                .centerCrop()
                .into(view);
    }
}

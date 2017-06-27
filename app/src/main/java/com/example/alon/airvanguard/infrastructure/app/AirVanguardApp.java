package com.example.alon.airvanguard.infrastructure.app;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.alon.airvanguard.infrastructure.di.component.AppComponent;
import com.example.alon.airvanguard.infrastructure.di.component.DaggerAppComponent;
import com.example.alon.airvanguard.infrastructure.di.module.AppModule;
import com.example.alon.airvanguard.presentation.common.BaseContract;
import com.google.firebase.FirebaseApp;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom {@link Application}.
 */



public class AirVanguardApp extends Application {

    private static AppComponent sAppComponent;
    private static List<BaseContract.Presenter> sPresentersStack = new ArrayList<>(0);

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }
        });
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    /**
     *
     * @param presenter
     */
    public static void pushPresenter(BaseContract.Presenter presenter) {
        sPresentersStack.add(presenter);
    }

    /**
     *
     */
    public static void popPresenter() {
        if (!sPresentersStack.isEmpty()) {
            sPresentersStack.remove(sPresentersStack.size() - 1);
        }
    }

    /**
     *
     * @return
     */
    @Nullable
    public static BaseContract.Presenter getPresenter() {
        if (!sPresentersStack.isEmpty()) {
            return sPresentersStack.get(sPresentersStack.size() - 1);
        }else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public static boolean isPresentersStackEmpty() {
        return sPresentersStack.isEmpty();
    }
}

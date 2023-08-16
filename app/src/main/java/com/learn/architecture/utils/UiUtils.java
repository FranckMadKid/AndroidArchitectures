package com.learn.architecture.utils;

import android.view.View;

import androidx.annotation.NonNull;

public class UiUtils {

    public static void showView(@NonNull final View view)
    {
        view.setVisibility(View.VISIBLE);
    }

    public static void hideView(@NonNull final View view)
    {
        view.setVisibility(View.GONE);
    }



}

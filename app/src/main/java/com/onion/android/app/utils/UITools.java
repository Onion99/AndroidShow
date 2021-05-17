package com.onion.android.app.utils;

import android.app.Activity;
import android.graphics.Insets;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowInsets;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class UITools {

    public static int getScreenWidth(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().width() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }

    public static int getHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static int getCoolColor() {
        Stack<Integer> colors = new Stack<>();
        Stack<Integer> recycle = new Stack<>();
        recycle.addAll(Arrays.asList(
                0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
                0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
                0xff009688, 0xff4caf50, 0xff8bc34a, 0xffcddc39,
                0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722,
                0xff795548, 0xff9e9e9e, 0xff607d8b, 0xff333333
        ));
        if (colors.size() == 0) {
            while (!recycle.isEmpty()) {
                // // 删除此堆栈顶部的对象，并将该对象作为此函数的值返回
                colors.push(recycle.pop());
            }
            // 乱序 使用默认的随机性源随机排列指定的列表。 所有排列都以近似相等的可能性发生
            Collections.shuffle(colors);
        }
        Integer color = colors.pop();
        // 将项目推入此堆栈的顶部。 这具有与以下功能完全相同的效果：addElement(item)
        recycle.push(color);
        return color;
    }
}

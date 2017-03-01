package cn.piorpua.baselib.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Author: piorpua<br>
 * Mail: helloworld.hnu@gmail.com<br>
 * Date Created: 16/12/15
 *
 * <p>Brief: UI 相关辅助方法</p>
 */
public final class UserInterfaceHelper {

    private UserInterfaceHelper() {}

    /***
     * {@link Looper#myLooper()}
     * @return NULL if Looper fail to prepare
     */
    public static @Nullable Looper prepareLooper() {
        Looper loop = Looper.myLooper();
        if (loop != null) {
            return loop;
        }

        try {
            Looper.prepare();
            return Looper.myLooper();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*** {@link Looper#getMainLooper()}
     * @return NULL if MainLooper fail to prepare
     */
    public static @Nullable Looper prepareMainLooper() {
        Looper loop = Looper.getMainLooper();
        if (loop != null) {
            return loop;
        }

        try {
            Looper.prepareMainLooper();
            return Looper.getMainLooper();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * {@link View#setVisibility(int)}
     * @return true if visibility of the view changed, otherwise false
     */
    public static boolean setVisibility(@Nullable View view, int visibility) {
        if (view == null) {
            return false;
        }

        if (view.getVisibility() == visibility) {
            return false;
        }

        view.setVisibility(visibility);
        return true;
    }

    /***
     * before called {@link TextView#setCompoundDrawables(Drawable, Drawable, Drawable, Drawable)},
     * {@link Drawable#setBounds(int, int, int, int)} with own instrinsic spaces.
     */
    public static void setCompoundDrawables(
            @Nullable TextView view,
            @Nullable Drawable left, @Nullable Drawable top,
            @Nullable Drawable right, @Nullable Drawable bottom) {

        if (view == null) {
            return;
        }

        if (left != null) {
            left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
        }

        if (top != null) {
            top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        }

        if (right != null) {
            right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        }

        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
        }

        view.setCompoundDrawables(left, top, right, bottom);
    }

    /***
     * Safely to combine {@link EditText#getText()} and {@link Editable#toString()}
     */
    public static @Nullable String getText(@Nullable EditText view) {
        if (view == null) {
            return null;
        }

        Editable edit = view.getText();
        if (edit == null) {
            return null;
        }

        return edit.toString();
    }

    /**
     * Hides the input method.
     * @param ctx context
     * @param view The currently focused view
     * @return success or not.
     */
    public static boolean hideInputMethod(@Nullable Context ctx, @Nullable View view) {
        if (ctx == null || view == null) {
            return false;
        }

        try {
            InputMethodManager imm = (InputMethodManager)
                    ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            return imm != null && imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Show the input method.
     * @param ctx context
     * @param view The currently focused view, which would like to receive soft keyboard input
     * @return success or not.
     */
    public static boolean showInputMethod(@Nullable Context ctx, @Nullable View view) {
        if (ctx == null || view == null) {
            return false;
        }

        try {
            InputMethodManager imm = (InputMethodManager)
                    ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            return imm != null && imm.showSoftInput(view, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /*** 给指定字符串包装上 Html 字体颜色 标签 */
    public static @Nullable String wrapperHtmlFontColorTag(
            @Nullable String color, @Nullable String content) {

        if (TextUtils.isEmpty(color) || TextUtils.isEmpty(content)) {
            return null;
        }

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<font color=\"").append(color)
                .append("\">").append(content).append("</font>");
        return strBuilder.toString();
    }

    /*** 给指定字符串包装上 Html 下划线 标签 */
    public static @Nullable String wrapperHtmlUnderlineTag(@Nullable String content) {
        return wrapperHtmlTag("u", content);
    }

    public static @ColorInt int parseColor(@Nullable String strColor, @ColorInt int defColor) {
        if (TextUtils.isEmpty(strColor)) {
            return defColor;
        }

        if (!strColor.startsWith("#")) {
            return defColor;
        }

        int length = strColor.length();

        // Use a long to avoid rollovers on #ffXXXXXX
        String subString = strColor.substring(1);
        long color = Long.parseLong(subString, 16);
        if (length == 7) {
            // Set the alpha value
            color |= 0x00000000ff000000;
            return (int) color;
        }

        return defColor;
    }

    /*** 给指定字符串包装上 Html 指定标签 */
    private static @Nullable String wrapperHtmlTag(
            @Nullable String tag, @Nullable String content) {

        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(content)) {
            return null;
        }

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<").append(tag).append(">")
                .append(content).append("</").append(tag).append(">");
        return strBuilder.toString();
    }
}

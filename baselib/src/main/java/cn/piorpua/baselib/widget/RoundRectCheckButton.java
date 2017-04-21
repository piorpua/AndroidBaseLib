package cn.piorpua.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import cn.piorpua.baselib.R;
import cn.piorpua.baselib.helper.UserInterfaceHelper;

/**
 * Author: piorpua<br>
 * Mail: helloworld.hnu@gmail.com<br>
 * Date Created: 17/3/9
 *
 * <p>Brief: 圆角状态按钮</p>
 *
 * XML 完整定义说明(标注 * 号的表示 存在默认配置)
 * <pre>
 *     RoundRectCheckButton:minimumHeight               最小高度(*)
 *     RoundRectCheckButton:checkable                   是否可选(*) <b>[默认为 true, 若为 false, 则 未选定/选定 状态 不可变]</b>
 *     RoundRectCheckButton:checked                     未选定/选定 状态(*)
 *     RoundRectCheckButton:iconPadding                 图标与文字间距(*)
 *     RoundRectCheckButton:horizontalPadding           水平方向间距(*)
 *     RoundRectCheckButton:verticalPadding             垂直方向间距(*)
 *     RoundRectCheckButton:colorUnCheck                未选定 状态下 全局(圆角矩形背景, 绘制图标, 文字)色值
 *     RoundRectCheckButton:colorCheck                  选定 状态下 全局(圆角矩形背景, 绘制图标, 文字)色值
 *     RoundRectCheckButton:rectStrokeWidth             圆角矩形背景边框宽度(*)
 *     RoundRectCheckButton:rectFill                    圆角矩形背景是否填充(*) <b>[默认为不填充, 若填充, 则 边框宽度 为 0]</b>
 *     RoundRectCheckButton:rectColor                   圆角矩形背景色值 <b>[若有配置, 则忽略 未选定/选定 状态下配置的色值]</b>
 *     RoundRectCheckButton:rectColorUnCheck            未选定 状态下 圆角矩形背景色值
 *     RoundRectCheckButton:rectColorCheck              选定 状态下 圆角矩形背景色值
 *     RoundRectCheckButton:icon                        图标 <b>[若有配置, 则忽略 未选定/选定 状态下配置的图标]</b>
 *     RoundRectCheckButton:iconUnCheck                 未选定 状态下 图标
 *     RoundRectCheckButton:iconCheck                   选定 状态下 图标
 *     RoundRectCheckButton:drawIconStrokeWidth         绘制图标 线宽(*)
 *     RoundRectCheckButton:drawIconUnCheck             未选定 状态下 绘制图标({@link RoundRectCheckButton.DrawIcon}) <b>[若 icon 相关已定义, 则忽略该配置]</b>
 *     RoundRectCheckButton:drawIconCheck               选定 状态下 绘制图标({@link RoundRectCheckButton.DrawIcon}) <b>[若 icon 相关已定义, 则忽略该配置]</b>
 *     RoundRectCheckButton:drawIconColorUnCheck        未选定 状态下 绘制图标色值
 *     RoundRectCheckButton:drawIconColorCheck          选定 状态下 绘制图标色值
 *     RoundRectCheckButton:textSize                    字体大小(*)
 *     RoundRectCheckButton:textColor                   字体颜色 <b>[若有配置, 则忽略 未选定/选定 状态下配置的色值]</b>
 *     RoundRectCheckButton:textColorUnCheck            未选定 状态下 字体颜色
 *     RoundRectCheckButton:textColorCheck              选定 状态下 字体颜色
 *     RoundRectCheckButton:text                        文字 <b>[若有配置, 则忽略 未选定/选定 状态下配置的文字]</b>
 *     RoundRectCheckButton:textUnCheck                 未选定 状态下 文字
 *     RoundRectCheckButton:textCheck                   选定 状态下 文字
 * </pre>
 *
 * 示例
 * <pre>
 *     1. 关注按钮(可使用 <b>style="@style/FollowCheckButtonStyle"</b>)
 *     <com.mgtv.widget.RoundRectCheckButton xmlns:RoundRectCheckButton="http://schemas.android.com/apk/res-auto"
 *          android:id="@+id/btnFollow"
 *          style="@style/FollowCheckButtonStyle"
 *          RoundRectCheckButton:checked="true" />
 * </pre>
 */
public final class RoundRectCheckButton extends View {

    /*** 绘制图标 */
    public enum DrawIcon {

        /*** 未定义 */
        UNKNOWN(0),
        /*** 加号 */
        ADD(1);

        /*** 根据 ID 获取相对应常量 */
        public static @NonNull DrawIcon fromID(int id) {
            for (DrawIcon icon : values()) {
                if (icon.mID == id) {
                    return icon;
                }
            }
            return UNKNOWN;
        }

        private final int mID;

        DrawIcon(int id) {
            mID = id;
        }
    }

    /*** 默认最小高度(px) */
    private static final int DEFAULT_MINIMUM_HEIGHT = 68;
    /*** 默认是否可选 */
    private static final boolean DEFAULT_CHECKABLE = true;
    /*** 默认 未选定/选定 状态 */
    private static final boolean DEFAULT_CHECKED = false;

    /*** 默认图标文字间距(px) */
    private static final int DEFAULT_ICON_PADDING = 8;
    /*** 默认水平方向间距(px) */
    private static final int DEFAULT_HORIZONTAL_PADDING = 0;
    /*** 默认水平方向间距(px) */
    private static final int DEFAULT_VERTICAL_PADDING = 0;

    /*** 默认矩形线宽(px) */
    private static final int DEFAULT_RECT_STROKE_WIDTH = 2;
    /*** 默认矩形是否填充 */
    private static final boolean DEFAULT_RECT_FILL = false;

    /*** 默认绘制图标大小与文字高度的比例 */
    private static final float DEFAULT_DRAWICON_PERCENT = 0.8f;

    /*** 默认绘制图标线宽(px) */
    private static final int DEFAULT_DRAWICON_STROKE_WIDTH = 3;


    /*** 画笔: 背景矩形 */
    private Paint mRectPaint;
    /*** 画笔: 绘制图标 */
    private Paint mIconPaint;
    /*** 画笔: 文字 */
    private Paint mTextPaint;

    /*** 最小高度(px) */
    private int mMinimumHeight;
    /*** 是否可选, 默认为 {@link RoundRectCheckButton#DEFAULT_CHECKABLE}. 若为 false, 则 {@link RoundRectCheckButton#mChecked} 不可变 */
    private boolean mCheckable;
    /*** 选定/未选定 标志位 */
    private boolean mChecked;

    /*** 图标与文字的间距(px) */
    private int mIconPadding;
    /*** 水平方向间距(px) */
    private int mHorizontalPadding;
    /*** 垂直方向间距(px) */
    private int mVerticalPadding;

    /*** 背景矩形是否填充 标志位 */
    private boolean mRectFill;
    /*** 背景矩形线宽(px): 若背景矩形填充({@link RoundRectCheckButton#mRectFill}), 则为 0 */
    private int mRectStrokeWidth;
    /*** 矩形颜色: 未选定 */
    private @ColorInt int mRectColorUnCheck;
    /*** 矩形颜色: 选定 */
    private @ColorInt int mRectColorCheck;

    /*** 图标: 未选定 */
    private @Nullable Drawable mIconUnCheck;
    /*** 图标: 选定 */
    private @Nullable Drawable mIconCheck;

    /*** 绘制图标线宽(px) */
    private int mDrawIconStrokeWidth;
    /*** 绘制图标: 未选定 */
    private @Nullable DrawIcon mDrawIconUnCheck;
    /*** 绘制图标: 选定 */
    private @Nullable DrawIcon mDrawIconCheck;
    /*** 绘制图标颜色: 未选定 */
    private @ColorInt int mDrawIconColorUnCheck;
    /*** 绘制图标颜色: 选定 */
    private @ColorInt int mDrawIconColorCheck;

    /*** 字体大小(px) */
    private int mTextSize;
    /*** 文字颜色: 未选定 */
    private @ColorInt int mTextColorUnCheck;
    /*** 文字颜色: 选定 */
    private @ColorInt int mTextColorCheck;
    /*** 文字: 未选定 */
    private @Nullable String mTextUnCheck;
    /*** 文字: 选定 */
    private @Nullable String mTextCheck;

    public RoundRectCheckButton(Context context) {
        this(context, null);
    }

    public RoundRectCheckButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectCheckButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectCheckButton);

        mMinimumHeight = a.getDimensionPixelSize(
                R.styleable.RoundRectCheckButton_minimumHeight, DEFAULT_MINIMUM_HEIGHT);
        mCheckable = a.getBoolean(R.styleable.RoundRectCheckButton_checkable, DEFAULT_CHECKABLE);
        mChecked = a.getBoolean(R.styleable.RoundRectCheckButton_checked, DEFAULT_CHECKED);

        mIconPadding = a.getDimensionPixelSize(
                R.styleable.RoundRectCheckButton_iconPadding, DEFAULT_ICON_PADDING);
        mHorizontalPadding = a.getDimensionPixelSize(
                R.styleable.RoundRectCheckButton_horizontalPadding, DEFAULT_HORIZONTAL_PADDING);
        mVerticalPadding = a.getDimensionPixelSize(
                R.styleable.RoundRectCheckButton_verticalPadding, DEFAULT_VERTICAL_PADDING);

        int colorUnCheck = a.getColor(
                R.styleable.RoundRectCheckButton_colorUnCheck, Color.TRANSPARENT);
        int colorCheck = a.getColor(
                R.styleable.RoundRectCheckButton_colorCheck, Color.TRANSPARENT);

        mRectStrokeWidth = a.getDimensionPixelSize(
                R.styleable.RoundRectCheckButton_rectStrokeWidth, DEFAULT_RECT_STROKE_WIDTH);
        mRectFill = a.getBoolean(R.styleable.RoundRectCheckButton_rectFill, DEFAULT_RECT_FILL);
        if (a.hasValue(R.styleable.RoundRectCheckButton_rectColor)) {
            mRectColorUnCheck = mRectColorCheck = a.getColor(
                    R.styleable.RoundRectCheckButton_rectColor, Color.TRANSPARENT);
        } else {
            mRectColorUnCheck = a.getColor(
                    R.styleable.RoundRectCheckButton_rectColorUnCheck, colorUnCheck);
            mRectColorCheck = a.getColor(
                    R.styleable.RoundRectCheckButton_rectColorCheck, colorCheck);
        }

        if (a.hasValue(R.styleable.RoundRectCheckButton_icon)) {
            mIconUnCheck = mIconCheck = a.getDrawable(R.styleable.RoundRectCheckButton_icon);
        } else {
            mIconUnCheck = a.getDrawable(R.styleable.RoundRectCheckButton_iconUnCheck);
            mIconCheck = a.getDrawable(R.styleable.RoundRectCheckButton_iconCheck);
        }

        mDrawIconStrokeWidth = a.getDimensionPixelSize(
                R.styleable.RoundRectCheckButton_drawIconStrokeWidth,
                DEFAULT_DRAWICON_STROKE_WIDTH);

        mDrawIconUnCheck = DrawIcon.fromID(a.getInt(
                R.styleable.RoundRectCheckButton_drawIconUnCheck,
                DrawIcon.UNKNOWN.ordinal()));
        mDrawIconColorUnCheck = a.getColor(
                R.styleable.RoundRectCheckButton_drawIconColorUnCheck, colorUnCheck);

        mDrawIconCheck = DrawIcon.fromID(a.getInt(
                R.styleable.RoundRectCheckButton_drawIconCheck,
                DrawIcon.UNKNOWN.ordinal()));
        mDrawIconColorCheck = a.getColor(
                R.styleable.RoundRectCheckButton_drawIconColorCheck, colorCheck);

        mTextSize = a.getDimensionPixelSize(
                R.styleable.RoundRectCheckButton_textSize, 0);
        if (a.hasValue(R.styleable.RoundRectCheckButton_textColor)) {
            mTextColorUnCheck = mTextColorCheck = a.getColor(
                    R.styleable.RoundRectCheckButton_textColor, Color.TRANSPARENT);
        } else {
            mTextColorUnCheck = a.getColor(
                    R.styleable.RoundRectCheckButton_textColorUnCheck, colorUnCheck);
            mTextColorCheck = a.getColor(
                    R.styleable.RoundRectCheckButton_textColorCheck, colorCheck);
        }
        if (a.hasValue(R.styleable.RoundRectCheckButton_text)) {
            mTextUnCheck = mTextCheck = a.getString(R.styleable.RoundRectCheckButton_text);
        } else {
            mTextUnCheck = a.getString(R.styleable.RoundRectCheckButton_textUnCheck);
            mTextCheck = a.getString(R.styleable.RoundRectCheckButton_textCheck);
        }

        a.recycle();

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(mRectFill ? Paint.Style.FILL : Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(mRectStrokeWidth);

        mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIconPaint.setStyle(Paint.Style.FILL);
        mIconPaint.setStrokeWidth(mDrawIconStrokeWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
    }

    /*** 设置是否可选 */
    public void setCheckable(boolean value) {
        if (mCheckable == value) {
            return;
        }
        mCheckable = value;
        invalidate();
    }

    /*** 是否可选 */
    public boolean isCheckable() {
        return mCheckable;
    }

    /*** 设置 未选定/选定 状态 */
    public void setChecked(boolean value) {
        if (!mCheckable || mChecked == value) {
            return;
        }
        mChecked = value;
        invalidate();
    }

    /*** 获取 未选定/选定 状态 */
    public boolean isChecked() {
        return mChecked;
    }

    /*** 设置 未选定 状态下 全局颜色 */
    public void setColorUnCheck(@ColorInt int color) {
        setGlobalColor(false, color);
    }

    /*** 设置 选定 状态下 全局颜色 */
    public void setColorCheck(@ColorInt int color) {
        setGlobalColor(true, color);
    }

    /*** 设置 未选定 状态下 绘制图标 */
    public void setDrawIconUnCheck(@Nullable DrawIcon icon) {
        setDrawIcon(false, icon);
    }

    /*** 设置 选定 状态下 绘制图标 */
    public void setDrawIconCheck(@Nullable DrawIcon icon) {
        setDrawIcon(true, icon);
    }

    /*** 设置字体大小(px) */
    public void setTextSize(int size) {
        if (mTextSize == size) {
            return;
        }
        mTextSize = size;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    /*** 设置 未选定 状态下 文字 */
    public void setTextUnCheck(@Nullable String text) {
        setText(false, text);
    }

    /*** 设置 选定 状态下 文字 */
    public void setTextCheck(@Nullable String text) {
        setText(true, text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 计算说明
        // 高度: [图标高度 与 文字高度 最大值] + [垂直方向间距]
        // 宽度: [图标宽度] + [文字宽度] + [图标文字间距(如果同时有图标、文字)] + [水平方向间距] + [高度(两个半圆)]

        final int textHeight = (int) UserInterfaceHelper.measureTextHeight(mTextPaint);

        int heightUnCheck = mVerticalPadding + Math.max(textHeight, getIconHeight(false));
        int heightCheck = mVerticalPadding + Math.max(textHeight, getIconHeight(true));
        int height = Math.max(heightUnCheck, heightCheck);

        height = Math.max(height, mMinimumHeight);

        int widthUnCheck = (int) (mHorizontalPadding + height +
                getIconWidth(false) + getIconPadding(false, mTextUnCheck) +
                UserInterfaceHelper.measureTextWidth(mTextPaint, mTextUnCheck));
        int widthCheck = (int) (mHorizontalPadding + height +
                getIconWidth(true) + getIconPadding(true, mTextCheck) +
                UserInterfaceHelper.measureTextWidth(mTextPaint, mTextCheck));
        int width = Math.max(widthUnCheck, widthCheck);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(
                MeasureSpec.EXACTLY == widthMode ? widthSize : width,
                MeasureSpec.EXACTLY == heightMode ? heightSize : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        // 背景(需考虑边框大小)

        float halfRectStrokeWidth = mRectStrokeWidth / 2.0f;

        Path path = new Path();
        path.arcTo(new RectF(halfRectStrokeWidth, halfRectStrokeWidth,
                height - mRectStrokeWidth, height - mRectStrokeWidth), 90.0f, 180.0f);
        path.lineTo(width - height / 2.0f, halfRectStrokeWidth);
        path.arcTo(new RectF(width - height + halfRectStrokeWidth, halfRectStrokeWidth,
                width - halfRectStrokeWidth, height - halfRectStrokeWidth), 270.0f, 180.0f);
        path.close();

        mRectPaint.setColor(getRectColor(mChecked));
        canvas.drawPath(path, mRectPaint);

        // 图标

        Drawable icon = getIcon(mChecked);
        if (icon != null) {
            int iconWidth = getIconWidth(mChecked);
            int iconHeight = getIconHeight(mChecked);

            int iconLeft = height / 2;
            int iconTop = (height - iconHeight) / 2;
            int iconRight = iconLeft + iconWidth;
            int iconBottom = iconTop + iconHeight;

            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            icon.draw(canvas);
        } else {
            onDrawIcon(canvas);
        }

        // 文字

        mTextPaint.setColor(getTextColor(mChecked));

        String text = getText(mChecked);
        if (!TextUtils.isEmpty(text)) {
            int iconSpace = getIconWidth(mChecked) + getIconPadding(mChecked, text);
            float textWidth = UserInterfaceHelper.measureTextWidth(mTextPaint, text);
            float textLeftX = height / 2.0f + iconSpace;
            float textCenterOffsetX = (width - height - iconSpace - textWidth) / 2.0f;

            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float textCenterVerticalBaselineY = height / 2.0f
                    - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2.0f;

            canvas.drawText(text, textLeftX + textCenterOffsetX,
                    textCenterVerticalBaselineY, mTextPaint);
        }
    }

    /*** 绘制图标 */
    private void onDrawIcon(Canvas canvas) {
        DrawIcon drawIcon = getDrawIcon(mChecked);
        if (drawIcon == null || DrawIcon.UNKNOWN == drawIcon) {
            return;
        }

        mIconPaint.setColor(getDrawIconColor(mChecked));

        if (DrawIcon.ADD == drawIcon) {
            int height = getMeasuredHeight();

            int iconWidth = getIconWidth(mChecked);
            int iconHeight = getIconHeight(mChecked);

            float iconLeft = height / 2.0f;
            float iconTop = (height - iconHeight) / 2.0f;

            float startX, startY, stopX, stopY;

            // 横线
            startX = iconLeft;
            startY = iconTop + iconHeight / 2.0f;
            stopX = startX + iconWidth;
            stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, mIconPaint);

            // 竖线
            startX = iconLeft + iconWidth / 2.0f;
            startY = iconTop;
            stopX = startX;
            stopY = startY + iconHeight;
            canvas.drawLine(startX, startY, stopX, stopY, mIconPaint);
        }
    }

    /*** 获取矩形色值 */
    private @ColorInt int getRectColor(boolean checked) {
        return checked ? mRectColorCheck : mRectColorUnCheck;
    }

    /*** 获取图标 */
    private @Nullable Drawable getIcon(boolean checked) {
        return checked ? mIconCheck : mIconUnCheck;
    }

    /*** 获取文字色值 */
    private @ColorInt int getDrawIconColor(boolean checked) {
        return checked ? mDrawIconColorCheck : mDrawIconColorUnCheck;
    }

    private @Nullable DrawIcon getDrawIcon(boolean checked) {
        return checked ? mDrawIconCheck : mDrawIconUnCheck;
    }

    /*** 获取文字色值 */
    private @ColorInt int getTextColor(boolean checked) {
        return checked ? mTextColorCheck : mTextColorUnCheck;
    }

    /*** 获取文字 */
    private @Nullable String getText(boolean checked) {
        return checked ? mTextCheck : mTextUnCheck;
    }

    /*** 根据图标和文字获取间距 */
    private int getIconPadding(boolean checked, @Nullable String text) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }

        Drawable icon = getIcon(checked);
        if (icon != null) {
            return mIconPadding;
        }

        DrawIcon drawIcon = getDrawIcon(checked);
        if (drawIcon != null && DrawIcon.UNKNOWN != drawIcon) {
            return mIconPadding;
        }

        return 0;
    }

    /*** 获取图标宽度 */
    private int getIconWidth(boolean checked) {
        Drawable icon = getIcon(checked);
        if (icon != null) {
            return icon.getIntrinsicWidth();
        }

        DrawIcon drawIcon = getDrawIcon(checked);
        if (drawIcon != null && DrawIcon.UNKNOWN != drawIcon) {
            float textHeight = UserInterfaceHelper.measureTextHeight(mTextPaint);
            return (int) (textHeight * DEFAULT_DRAWICON_PERCENT);
        }

        return 0;
    }

    /*** 获取图标高度 */
    private int getIconHeight(boolean checked) {
        Drawable icon = getIcon(checked);
        if (icon != null) {
            return icon.getIntrinsicHeight();
        }

        DrawIcon drawIcon = getDrawIcon(checked);
        if (drawIcon != null && DrawIcon.UNKNOWN != drawIcon) {
            float textHeight = UserInterfaceHelper.measureTextHeight(mTextPaint);
            return (int) (textHeight * DEFAULT_DRAWICON_PERCENT);
        }

        return 0;
    }

    /*** 设置全局颜色 */
    private void setGlobalColor(boolean checked, @ColorInt int color) {
        if (checked) {
            if (mRectColorCheck == color &&
                    mDrawIconColorCheck == color &&
                    mTextColorCheck == color) {

                return;
            }

            mRectColorCheck = color;
            mDrawIconColorCheck = color;
            mTextColorCheck = color;
        } else {
            if (mRectColorUnCheck == color &&
                    mDrawIconColorUnCheck == color &&
                    mTextColorUnCheck == color) {

                return;
            }

            mRectColorUnCheck = color;
            mDrawIconColorUnCheck = color;
            mTextColorUnCheck = color;
        }

        invalidate();
    }

    /*** 设置 绘制图标 */
    private void setDrawIcon(boolean checked, @Nullable DrawIcon icon) {
        if (checked) {
            if (mDrawIconCheck == icon) {
                return;
            }
            mDrawIconCheck = icon;
        } else {
            if (mDrawIconUnCheck == icon) {
                return;
            }
            mDrawIconUnCheck = icon;
        }
        invalidate();
    }

    /*** 设置 文字 */
    private void setText(boolean checked, @Nullable String text) {
        if (checked) {
            if (TextUtils.equals(mTextCheck, text)) {
                return;
            }
            mTextCheck = text;
        } else {
            if (TextUtils.equals(mTextUnCheck, text)) {
                return;
            }
            mTextUnCheck = text;
        }
        invalidate();
    }
}

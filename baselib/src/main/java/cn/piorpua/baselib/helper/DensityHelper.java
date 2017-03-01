package cn.piorpua.baselib.helper;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Author: piorpua<br>
 * Mail: helloworld.hnu@gmail.com<br>
 * Date Created: 17/2/23
 *
 * <p>Brief: 分辨率相关辅助类</p>
 */
public final class DensityHelper {

    private DensityHelper() {}

    /*** 根据手机分辨率，将 dp 转换为 px */
    public static int dp2px(@NonNull Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*** 根据手机分辨率，将 px 转换为 dp */
    public static int px2dp(@NonNull Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /*** 根据手机分辨率，将 sp 转换为 px */
    public static int sp2px(@NonNull Context ctx, float spValue) {
        final float fontScale = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /*** 根据手机分辨率，将 px 转换为 sp */
    public static int px2sp(@NonNull Context ctx, float pxValue) {
        final float fontScale = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}

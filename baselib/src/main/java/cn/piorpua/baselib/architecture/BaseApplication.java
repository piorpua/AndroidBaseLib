package cn.piorpua.baselib.architecture;

import android.app.Application;

/**
 * Author: piorpua<br>
 * Mail: helloworld.hnu@gmail.com<br>
 * Date Created: 17/2/23
 *
 * <p>Brief: 基础 {@link Application}</p>
 */
public class BaseApplication extends Application {

    private static Application sIns;

    public static Application getIns() {
        return sIns;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sIns = this;
    }
}

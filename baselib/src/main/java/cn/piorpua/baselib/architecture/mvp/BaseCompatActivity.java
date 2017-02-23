package cn.piorpua.baselib.architecture.mvp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Author: piorpua<helloworld.hnu@gmail.com><br>
 * Date Created: 17/1/12
 *
 * <p>Brief: 基础 {@link AppCompatActivity}</p>
 *
 * @param <P> {@link MVPBasePresenter}
 */
public class BaseCompatActivity<P extends MVPBasePresenter> extends AppCompatActivity {

    private @Nullable P mPresenter;

    /*** 连接 Presenter */
    protected final void attachPresenter(P p) {
        mPresenter = p;
    }

    /*** 获取 Presenter */
    protected final @Nullable P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mPresenter != null) {
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }
}

package cn.piorpua.baselib.architecture.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import cn.piorpua.baselib.component.ReferenceHandler;

/**
 * Author: piorpua<helloworld.hnu@gmail.com><br>
 * Date Created: 17/1/12
 *
 * <p>Brief: MVP - <b>Presenter</b></p>
 *
 * 1. 提供对 {@link MVPBaseView} 的引用;<br>
 * 2. 提供与 {@link Activity} 部分相匹配与其生命周期相关的空方法;<br>
 * 3. 提供内部主线程;<br>
 *
 * @param <View> {@link MVPBaseView}
 */
public abstract class MVPBasePresenter<View extends MVPBaseView> {

    /*** 内部主线程 */
    private static final class InnerHandler extends ReferenceHandler<MVPBasePresenter> {

        public InnerHandler(MVPBasePresenter presenter) {
            super(presenter);
        }

        @Override
        protected void handleMessageSticky(
                @NonNull MVPBasePresenter presenter, @NonNull Message msg) {

            presenter.onHandleMessage(msg);
        }

        @Override
        protected boolean checkHost(@NonNull MVPBasePresenter presenter) {
            return !presenter.isDestroyed();
        }
    }

    /*** View 引用 */
    private @Nullable Reference<View> mViewRef;

    /*** 内部主线程 */
    private @NonNull InnerHandler mMainHandler;

    /*** 是否被销毁 ( {@link MVPBasePresenter#onDestroy()} ) 标识位。仅在 <b>主线程</b> 判断时有意义。 */
    private boolean mDestroyed;

    public MVPBasePresenter(View view) {
        mViewRef = new WeakReference<View>(view);
        mMainHandler = new InnerHandler(this);
    }

    /*** 获取 View */
    protected final @Nullable View getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    /*** 是否被销毁 */
    @MainThread
    public final boolean isDestroyed() {
        return mDestroyed;
    }

    /*** Call when {@link Activity#onResume()} */
    @MainThread
    public void onResume() {
        // DO NOTHING
    }

    /*** Call when {@link Activity#onDestroy()} */
    @MainThread
    public void onDestroy() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }

        mMainHandler.detachReference();

        mDestroyed = true;
    }

    /*** Call when {@link Activity#onActivityResult(int, int, Intent)} */
    @MainThread
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // DO NOTHING
    }

    // Handle Message >>>

    public final Message obtainMessage(int what) {
        return mMainHandler.obtainMessage(what);
    }

    public final void sendEmptyMessage(int what) {
        mMainHandler.sendEmptyMessage(what);
    }

    public final void sendEmptyMessageDelayed(int what, long delayMillis) {
        mMainHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    public final void sendMessage(Message msg) {
        mMainHandler.sendMessage(msg);
    }

    public final void removeMessage(int what) {
        mMainHandler.removeMessages(what);
    }

    public final boolean hasMessages(int what) {
        return mMainHandler.hasMessages(what);
    }

    protected void onHandleMessage(@NonNull Message msg) {
        // DO NOTHING
    }

    // <<< Handle Message
}

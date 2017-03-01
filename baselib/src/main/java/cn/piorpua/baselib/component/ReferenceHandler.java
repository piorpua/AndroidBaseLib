package cn.piorpua.baselib.component;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Author: piorpua<br>
 * Mail: helloworld.hnu@gmail.com<br>
 * Date: 17/1/12
 *
 * <p>带引用的 {@link Handler}</p>
 *
 * @param <Host> 被引用对象
 */
public abstract class ReferenceHandler<Host> extends Handler {

    protected abstract void handleMessageSticky(@NonNull Host host, @NonNull Message msg);

    private @Nullable WeakReference<Host> mReference;

    public ReferenceHandler(Host host) {
        mReference = new WeakReference<Host>(host);
    }

    @Override
    public final void handleMessage(Message msg) {
        if (mReference == null || msg == null) {
            return;
        }

        Host host = mReference.get();
        if (host == null) {
            return;
        }

        if (!checkHost(host)) {
            return;
        }

        handleMessageSticky(host, msg);
    }

    /*** 清除引用 */
    public final void detachReference() {
        if (mReference == null) {
            return;
        }
        mReference.clear();
        mReference = null;
    }

    /**
     * 测试被引用对象有效性<br>
     * 子类重写该方法可用于控制 {@link ReferenceHandler#handleMessageSticky(Object, Message)} 的调用
     * @return 默认返回 <b>true</b>。若返回 <b>false</b>，
     * 将不会调用方法 <b>{@link ReferenceHandler#handleMessageSticky(Object, Message)}</b>。
     */
    protected boolean checkHost(@NonNull Host host) {
        return true;
    }
}

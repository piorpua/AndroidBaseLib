package cn.piorpua.baselib.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: piorpua<helloworld.hnu@gmail.com><br>
 * Date Created: 17/1/12
 *
 * <p>Brief: 基础 {@link android.support.v7.widget.RecyclerView.Adapter}</p>
 *
 * 1. 提供对列表的操作支持(添加/删除/获取);<br>
 * 2. 提供基本 清除/销毁 方法;<br>
 * 3. 提供 {@link Context}, {@link List}, ... 获取, 点击监听器;<br>
 *
 * @param <V> 数据类型
 */
public abstract class BaseRecyclerAdapter<V>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /*** Item 点击监听器 */
    public interface OnItemClickListener {

        void onItemClicked(View view, int position, int componentID, @Nullable Object objExt);
    }

    /*** 基础 {@link RecyclerView.ViewHolder} */
    protected static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    private @Nullable Context mCtx;
    private @Nullable List<V> mList;
    private @Nullable OnItemClickListener mOnItemClickListener;

    public BaseRecyclerAdapter() {
        this(null);
    }

    public BaseRecyclerAdapter(Context ctx) {
        mCtx = ctx;
        mList = new LinkedList<V>();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /*** 获取 Context 引用 */
    protected final @Nullable Context getContext() {
        return mCtx;
    }

    /*** 获取 List 引用 */
    protected final @Nullable List<V> getList() {
        return mList;
    }

    /*** 获取 OnItemClickListener 引用 */
    protected final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    /*** 是否为空 */
    public final boolean isEmpty() {
        return mList == null || mList.isEmpty();
    }

    /*** 获取整个 List 的拷贝 */
    public final @Nullable List<V> getListCopy() {
        if (mList == null || mList.isEmpty()) {
            return null;
        }

        List<V> list = new ArrayList<V>();
        list.addAll(mList);
        return list;
    }

    /*** 设置点击监听器 */
    public final void setOnItemComponentExtClickListener(@Nullable OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    /*** 获取指定位置的 Value {@link List#get(int)} */
    public final @Nullable V getItem(int position) {
        try {
            return mList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*** 获取指定 Value 的位置，不存在 或者 查询出错 则返回 -1 */
    public final int indexOf(V value) {
        try {
            return mList.indexOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*** 销毁 */
    public void destroy() {
        mCtx = null;
        if (mList != null) {
            mList.clear();
            mList = null;
        }
        mOnItemClickListener = null;
    }

    /*** 清除整个列表 */
    public void clear() {
        if (mList != null) {
            mList.clear();
        }
    }

    /*** 将 Value 添加到列表头 */
    public boolean addFront(@Nullable V value) {
        return addValue(0, value);
    }

    /*** 将 Value 添加到列表尾 */
    public boolean addBottom(@Nullable V value) {
        return addValue(-1, value);
    }

    /*** 将 List<Value> 添加到列表头 */
    public boolean addListFront(@Nullable List<V> list) {
        return addList(0, list);
    }

    /*** 将 List<Value> 添加到列表尾 */
    public boolean addListBottom(@Nullable List<V> list) {
        return addList(-1, list);
    }

    /*** 将 Value 添加到列表指定位置 */
    public boolean addValue(int index, @Nullable V value) {
        if (value == null || mList == null) {
            return false;
        }

        if (index < 0 || index > mList.size()) {
            return mList.add(value);
        }

        int size = mList.size();
        mList.add(index, value);
        return size != mList.size();
    }

    /*** 将 List<Value> 添加到列表指定位置 */
    public boolean addList(int index, @Nullable List<V> list) {
        if (list == null || list.isEmpty() || mList == null) {
            return false;
        }

        boolean modified = false;
        try {
            modified = index < 0 || index > mList.size() ?
                    mList.addAll(list) : mList.addAll(index, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modified;
    }

    /*** 删除 {@link List#remove(int)}*/
    public V remove(int index) {
        try {
            return mList.remove(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*** 删除 {@link List#remove(Object)}*/
    public boolean remove(V value) {
        try {
            return mList.remove(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

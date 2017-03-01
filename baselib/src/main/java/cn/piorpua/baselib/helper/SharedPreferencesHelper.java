package cn.piorpua.baselib.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Author: piorpua<br>
 * Mail: helloworld.hnu@gmail.com<br>
 * Date: 17/1/12
 *
 * <p>辅助类 {@link SharedPreferences}</p>
 */
public class SharedPreferencesHelper {

    private final @NonNull SharedPreferences mPreferences;

    public SharedPreferencesHelper(Context ctx, String name) {
        this(ctx, name, Context.MODE_PRIVATE);
    }

    public SharedPreferencesHelper(Context ctx, String name, int mode) {
        if (ctx == null) {
            throw new IllegalArgumentException("Illegal SharedPreferences context.");
        }

        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Illegal SharedPreferences name.");
        }

        mPreferences = ctx.getSharedPreferences(name, mode);
    }

    public String getString(String key) {
        return getString(key, "");
    }
    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public boolean putString(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        return safeCommit(editor);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }
    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public boolean putInt(String key, int value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        return safeCommit(editor);
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }
    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public boolean putLong(String key, long value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(key, value);
        return safeCommit(editor);
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }
    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public boolean putFloat(String key, float value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putFloat(key, value);
        return safeCommit(editor);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public boolean putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        return safeCommit(editor);
    }

    private boolean safeCommit(SharedPreferences.Editor editor) {
        try {
            return editor.commit();
        } catch (StringIndexOutOfBoundsException e) {
        }
        return false;
    }
}

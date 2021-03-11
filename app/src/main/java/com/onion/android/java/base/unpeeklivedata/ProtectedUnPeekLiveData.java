package com.onion.android.java.base.unpeeklivedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.HashMap;
import java.util.Map;

public class ProtectedUnPeekLiveData<T> extends LiveData<T> {

    // 是否允许空值
    protected boolean isAllowNullValue;
    // 用来存储observer
    private final HashMap<Integer, Boolean> observers = new HashMap<>();
    /* 在Activity中观察 */
    public void observeInActivity(AppCompatActivity activity,
                                  Observer<? super T> observer) {
        LifecycleOwner owner = activity;
        Integer storeId = System.identityHashCode(activity.getViewModelStore());
        observer(storeId, owner, observer);
    }
    /* 在Fragment中观察 */
    public void observeInFragment(Fragment fragment,
                                  Observer<? super T> observer) {
        LifecycleOwner owner = fragment.getViewLifecycleOwner();
        Integer storeId = System.identityHashCode(fragment.getViewModelStore());
        observer(storeId, owner, observer);
    }
    /* 重新Observer */
    private void observer(@NonNull Integer storeId,
                          @NonNull LifecycleOwner owner,
                          @NonNull Observer<? super T> observer) {
        if(observers.get(storeId) == null){
            observers.put(storeId, true);
        }

        super.observe(owner, changeValue -> {
            if(!observers.get(storeId)){
                observers.put(storeId, true);
                if( changeValue != null || isAllowNullValue){
                    observer.onChanged(changeValue);
                }
            }
        });
    }

    /**
     * 重写的 setValue 方法，默认不接收 null
     * 可通过 Builder 配置允许接收
     * 可通过 Builder 配置消息延时清理的时间
     * @param value
     */
    @Override
    protected void setValue(T value) {
        if( value != null || isAllowNullValue ){
            for (Map.Entry<Integer,Boolean> entry: observers.entrySet()){
                entry.setValue(false);
            }
            super.setValue(value);
        }
    }
    // 清空
    protected void clear(){ super.setValue(null); }
}

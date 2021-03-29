package com.onion.android.java.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import dagger.android.AndroidInjection;

public abstract class PlexBaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    public T mBinding;
//    PlexBaseActivity<T extends ViewDataBinding,V extends ViewModel>
//    public V mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dagger-依赖注入（zi）
        AndroidInjection.inject(this);
        // DataBinding 绑定
        mBinding = DataBindingUtil.setContentView(this, getBindingContent(savedInstanceState));
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Optional.ofNullable(mBinding).ifPresent(ViewDataBinding::unbind);
    }

    //    public void initViewModel(){
//        if(mViewModel == null){
//            Class modelClass;
//            // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
//            // 然后将其转换ParameterizedType
//            // getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组。
//            // [0]就是这个数组中第一个了。
//            Type type = getClass().getGenericSuperclass();
//            /**
//             * ParameterizedType 就是参数化类型的意思
//             * 声明类型中带有“<>”的都是参数化类型，比如 List<Integer>，Map<String,Long>
//             * getActualTypeArguments()返回Type[]，即“<>”里的参数，比如Map<String,Integer>
//             * getRawType()返回Tpye，得到“<>”前面的类型，比如List<String>
//             * getOwnerType()返回Type，O<T>.I<S>类型变量调用时会返回O <T>，比如Map.Entry<Long,Short>
//             * */
//            if( type instanceof ParameterizedType){
//                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
//                mViewModel = (V) createViewModel(this, modelClass);
//            }else{
//                throw new NullPointerException();
//            }
//        }
//    }

    public abstract void initView();

    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);
// ViewModelProviders it is deprecated
//    /**
//     * 创建ViewModel
//     *
//     * @param cls
//     * @param <T>
//     * @return
//     */
//    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
//        return ViewModelProviders.of(activity).get(cls);
//    }

}

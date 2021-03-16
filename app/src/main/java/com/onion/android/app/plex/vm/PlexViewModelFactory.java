package com.onion.android.app.plex.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class PlexViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    /**
     *  @Inject注解可以出现在三种类成员之前，表示该成员需要注入依赖项。按运行时的处理顺序这三种成员类型是：
     * （1）构造方法
     * （2）方法
     * （3）属性
     * */
    @Inject
    public PlexViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if(creator == null){
            // Map.entrySet() 返回Map中包含的<key ,value>集合
            for ( Map.Entry< Class<? extends ViewModel> , Provider<ViewModel>> entry : creators.entrySet()){
                /**
                 * A isAssignableFrom B 判断A类是不是继承B类 - 从类继承的角度去判断
                 * A instanceof B 判断A是否是B类的子类 - 从实例继承的角度去判断
                 * */
               if(modelClass.isAssignableFrom(entry.getKey())){
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try{
            return (T) creator.get();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }
}

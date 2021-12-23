package com.onion.android.app.plex.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexItemGenresBinding;

import org.jetbrains.annotations.NotNull;

public class ItemAdapter extends PagedListAdapter<Media, ItemAdapter.ItemViewHolder> {

    private static final DiffUtil.ItemCallback<Media> ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Media>() {
                @Override
                public boolean areItemsTheSame(Media oldItem, Media newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Media oldItem, @NotNull Media newItem) {
                    return oldItem.equals(newItem);
                }
            };
    public Context context;


    public ItemAdapter(Context context) {
        super(ITEM_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(PlexItemGenresBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    /**
     * 内部类作用：
     * 更好的封装性
     * 内部类成员可以直接访问外部类的私有数据，因为内部类被当成其外部类成员，但外部类不能访问内部类的实现细节，例如内部类的成员变量
     * 匿名内部类适合用于创建那些仅需要一次使用的类
     * <p>
     * 静态内部类：https://blog.csdn.net/cd18333612683/article/details/79129503
     * 使用static来修饰一个内部类，则这个内部类就属于外部类本身，而不属于外部类的某个对象。称为静态内部类（也可称为类内部类）
     * 这样的内部类是类级别的，static关键字的作用是把类的成员变成类相关，而不是实例相关
     * 注意：
     * 1.非静态内部类中不允许定义静态成员
     * 2.外部类的静态成员不可以直接使用非静态内部类
     * 3.静态内部类，不能访问外部类的实例成员，只能访问外部类的类成员
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final PlexItemGenresBinding binding;

        public ItemViewHolder(@NonNull PlexItemGenresBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Media media) {
            Tools.loadHttpImg(binding.itemMovieImage, media.getPosterPath());
        }
    }
}

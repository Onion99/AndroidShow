package com.onion.android.app.plex.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.databinding.PlexItemSearchBinding;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Media> mediaList;

    public SearchAdapter(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(PlexItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.onBind(mediaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private PlexItemSearchBinding searchBinding;

        public SearchViewHolder(@NonNull PlexItemSearchBinding binding) {
            super(binding.getRoot());
            searchBinding = binding;
        }

        void onBind(Media media) {
            searchBinding.setMedia(media);
        }
    }
}

package com.onion.android.app.plex.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.onion.android.R;
import com.onion.android.app.plex.data.local.entity.History;
import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.model.genres.Genre;
import com.onion.android.app.plex.data.model.media.MediaModel;
import com.onion.android.app.plex.data.repository.MediaRepository;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.ItemFeaturedBinding;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    private List<Media> castList;
    private Context context;
    private MediaRepository mediaRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Random random;
    private History history;
    private static final int PRELOAD_TIME_S = 2;
    private static final String TAG = "FeaturedAdapter";
    protected SimpleExoPlayer mMoviePlayer;
    MediaModel mMediaModel;

    @Inject
    public FeaturedAdapter() {
    }

    public void addFeatured(List<Media> castList, Context context,
                            MediaRepository mediaRepository){
        this.castList = castList;
        this.context = context;
        this.mediaRepository = mediaRepository;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeaturedBinding binding = ItemFeaturedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FeaturedViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (castList != null) {
            if(castList.size() <= 20){
                return castList.size();
            }else {
                return 20;
            }
        } else {
            return 0;
        }
    }
    class FeaturedViewHolder extends RecyclerView.ViewHolder {
        private final ItemFeaturedBinding binding;
        FeaturedViewHolder (@NonNull ItemFeaturedBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        void onBind(final int position) {
            final Media media = castList.get(position);
            if (mMoviePlayer !=null) {
                mMoviePlayer.release();
            }
            binding.PlayButtonIcon.setOnClickListener(view -> {

            });
            if (media.getName() !=null) {
                binding.movietitle.setText(media.getName());
                for (Genre genre : media.getGenres()) {
                    binding.mgenres.setText(genre.getName());
                }
                binding.infoTrailer.setOnClickListener(v -> {
                });
                binding.rootLayout.setOnClickListener(v -> {
                });
                binding.moviePremuim.setVisibility(media.getPremuim() == 1? View.VISIBLE:View.GONE);
            }else {
                onLoadMovies(media);
            }

            Tools.onLoadMediaCover(context,binding.itemMovieImage,media.getPosterPath());


        }


        private void onLoadMovies(Media media) {
            if (mediaRepository.hasHistory(Integer.parseInt(media.getTmdbId()))) {
                binding.PlayButtonIcon.setBackground(context.getResources().getDrawable(R.drawable.btn_gradient_watch_video,null));
                binding.PlayButtonIcon.setText("Resume");
            }else {
                binding.PlayButtonIcon.setBackground(context.getResources().getDrawable(R.drawable.btn_gradient,null));
                binding.PlayButtonIcon.setText("Lecture");

            }
            binding.moviePremuim.setVisibility(media.getPremuim() == 1? View.VISIBLE:View.GONE);
            binding.infoTrailer.setOnClickListener(v -> {});
            binding.movietitle.setText(media.getTitle());
            for (Genre genre : media.getGenres()) {
                binding.mgenres.setText(genre.getName());
            }
            binding.rootLayout.setOnClickListener(view -> {});
            binding.PlayButtonIcon.setOnClickListener(view -> {});
        }
    }

}

package com.onion.android.app.plex.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.onion.android.R;
import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.model.genres.Genre;
import com.onion.android.app.plex.data.repository.MediaRepository;
import com.onion.android.app.plex.ui.MainActivity;
import com.onion.android.app.utils.GlideApp;
import com.onion.android.app.utils.UITools;
import com.onion.android.databinding.PlexItemFeaturedBinding;

import java.util.List;

import javax.inject.Inject;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    private List<Media> castList;
    private Context context;
    private MediaRepository mediaRepository;
    protected SimpleExoPlayer mMoviePlayer;

    @Inject
    public FeaturedAdapter() {
    }

    public void addFeatured(List<Media> castList, Context context,
                            MediaRepository mediaRepository) {
        this.castList = castList;
        this.context = context;
        this.mediaRepository = mediaRepository;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlexItemFeaturedBinding binding = PlexItemFeaturedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FeaturedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (castList != null) {
            return castList.size();
        } else {
            return 0;
        }
    }

    class FeaturedViewHolder extends RecyclerView.ViewHolder {
        private final PlexItemFeaturedBinding binding;

        FeaturedViewHolder(@NonNull PlexItemFeaturedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        void onBind(final int position) {
            final Media media = castList.get(position);
            if (mMoviePlayer != null) mMoviePlayer.release();
            if (media.getName() != null) {
                binding.movietitle.setText(media.getName());
                for (Genre genre : media.getGenres()) {
                    binding.mgenres.setText(genre.getName());
                }
                binding.infoTrailer.setOnClickListener(v -> {
                });
                binding.rootLayout.setOnClickListener(v -> {
                });
                binding.moviePremuim.setVisibility(media.getPremuim() == 1 ? View.VISIBLE : View.GONE);
            } else {
                onLoadMovies(media);
            }
            GlideApp.with(context).asBitmap().load(media.getPosterPath())
                    .fitCenter()
                    .placeholder(new ColorDrawable(UITools.getCoolColor()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(withCrossFade())
                    .override(UITools.getScreenWidth((MainActivity) context), UITools.getHeight((MainActivity) context))
                    .into(binding.itemMovieImage);
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

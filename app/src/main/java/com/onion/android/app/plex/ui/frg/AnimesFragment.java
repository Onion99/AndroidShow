package com.onion.android.app.plex.ui.frg;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.onion.android.R;
import com.onion.android.app.base.PlexBaseFragment;
import com.onion.android.app.plex.ui.adapter.ItemAdapter;
import com.onion.android.app.plex.ui.adapter.decoration.SpacingItemDecoration;
import com.onion.android.app.plex.vm.GenresViewModel;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexFragmentLibraryGenresBinding;

public class AnimesFragment extends PlexBaseFragment<PlexFragmentLibraryGenresBinding> {

    private GenresViewModel genresViewModel;
    private ItemAdapter itemAdapter;


    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_library_genres;
    }

    @Override
    public void initView() {
        itemAdapter = new ItemAdapter(requireContext());
        initData();
    }

    private void initData() {
        genresViewModel = viewModelProvider.get(GenresViewModel.class);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        binding.recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(requireContext(), 0), true));
        binding.recyclerView.setItemViewCacheSize(12);
        genresViewModel.animePagedList.observe(getViewLifecycleOwner(), series -> {
            binding.noMoviesFound.setVisibility(View.GONE);
            itemAdapter.submitList(series);
        });
        binding.recyclerView.setAdapter(itemAdapter);
    }
}

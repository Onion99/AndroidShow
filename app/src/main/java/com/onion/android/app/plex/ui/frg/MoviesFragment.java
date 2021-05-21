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

public class MoviesFragment extends PlexBaseFragment<PlexFragmentLibraryGenresBinding> {

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

    @Override
    public void initViewModel() {
        genresViewModel = mViewModelProvider.get(GenresViewModel.class);
    }

    private void initData() {
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        mBinding.recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(requireContext(), 0), true));
        mBinding.recyclerView.setItemViewCacheSize(12);
        genresViewModel.moviePagedList.observe(getViewLifecycleOwner(), movies -> {
            mBinding.noMoviesFound.setVisibility(View.GONE);
            itemAdapter.submitList(movies);
        });
        mBinding.recyclerView.setAdapter(itemAdapter);
    }

}


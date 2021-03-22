package com.onion.android.app.plex.ui.frg;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.onion.android.R;
import com.onion.android.app.plex.data.repository.MediaRepository;
import com.onion.android.app.plex.ui.adapter.FeaturedAdapter;
import com.onion.android.app.plex.ui.adapter.decoration.SpacingItemDecoration;
import com.onion.android.app.plex.vm.HomeViewModel;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexFragmentHomeBinding;
import com.onion.android.java.base.PlexBaseFragment;

import javax.inject.Inject;

public class HomeFragment extends PlexBaseFragment<PlexFragmentHomeBinding, HomeViewModel> {

    private PagerSnapHelper pagerSnapHelper;
    private FeaturedAdapter mFeaturedAdapter;

    @Inject
    MediaRepository mediaRepository;

    @Override
    public void initView() {
        onLoadFeaturedMovies();
    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_home;
    }

    @Override
    public Class<HomeViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }

    // Display Featured Movies Details
    private void onLoadFeaturedMovies() {

        mBinding.rvFeatured.setHasFixedSize(true);
        mBinding.rvFeatured.setNestedScrollingEnabled(false);
        mBinding.rvFeatured.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvFeatured.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(requireActivity(), 0), true));
        mBinding.rvFeatured.setAdapter(mFeaturedAdapter);
        mBinding.rvFeatured.setItemViewCacheSize(8);
        pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mBinding.rvFeatured);
        mBinding.indicator.attachToRecyclerView(mBinding.rvFeatured, pagerSnapHelper);
        mBinding.indicator.createIndicators(mFeaturedAdapter.getItemCount(),0);
        mFeaturedAdapter.registerAdapterDataObserver(mBinding.indicator.getAdapterDataObserver());

        mViewModel.featuredMoviesMutableLiveData.observe(getViewLifecycleOwner(), featured -> mFeaturedAdapter.addFeatured(featured.getFeatured(),requireActivity(), mediaRepository));
    }

}
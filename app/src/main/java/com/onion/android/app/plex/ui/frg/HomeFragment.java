package com.onion.android.app.plex.ui.frg;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.onion.android.R;
import com.onion.android.app.plex.data.repository.MediaRepository;
import com.onion.android.app.plex.di.injector.Injectable;
import com.onion.android.app.plex.ui.adapter.FeaturedAdapter;
import com.onion.android.app.plex.ui.adapter.decoration.SpacingItemDecoration;
import com.onion.android.app.plex.vm.HomeViewModel;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexFragmentHomeBinding;
import com.onion.android.app.base.PlexBaseFragment;

import javax.inject.Inject;

public class HomeFragment extends PlexBaseFragment<PlexFragmentHomeBinding> implements Injectable {

    @Inject
    FeaturedAdapter mFeaturedAdapter;
    @Inject
    HomeViewModel viewModel;
    @Inject
    MediaRepository mediaRepository;

    @Override
    public void initView() {
        if(Tools.checkIfHasNetwork(requireContext())){
            viewModel.initData();
            onLoadFeaturedMovies();
        }
    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_home;
    }

    // 加载流行影片
    private void onLoadFeaturedMovies() {
        mBinding.rvFeatured.setHasFixedSize(true);
        mBinding.rvFeatured.setNestedScrollingEnabled(false);
        mBinding.rvFeatured.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvFeatured.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(requireActivity(), 0), true));
        mBinding.rvFeatured.setAdapter(mFeaturedAdapter);
        mBinding.rvFeatured.setItemViewCacheSize(8);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mBinding.rvFeatured);
        mBinding.indicator.attachToRecyclerView(mBinding.rvFeatured, pagerSnapHelper);
        mBinding.indicator.createIndicators(mFeaturedAdapter.getItemCount(),0);
        mFeaturedAdapter.registerAdapterDataObserver(mBinding.indicator.getAdapterDataObserver());
        viewModel.featuredMoviesMutableLiveData.observe(getViewLifecycleOwner(), featured -> {
            mFeaturedAdapter.addFeatured(featured.getFeatured(), requireActivity(), mediaRepository);
            viewModel.mFeaturedLoaded = true;
            viewModel.mScrollLoaded = true;
            checkDataLoaded();
        });
    }

    private void checkDataLoaded(){
        if(viewModel.checkDataLoaded()){
            mBinding.scrollView.setVisibility(View.VISIBLE);
            mBinding.progressBar.setVisibility(View.GONE);
        }
    }
}
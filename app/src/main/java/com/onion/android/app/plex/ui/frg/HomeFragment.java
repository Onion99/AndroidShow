package com.onion.android.app.plex.ui.frg;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.onion.android.R;
import com.onion.android.app.base.PlexBaseFragment;
import com.onion.android.app.plex.data.repository.MediaRepository;
import com.onion.android.app.plex.ui.adapter.FeaturedAdapter;
import com.onion.android.app.plex.ui.adapter.LatestStreamAdapter;
import com.onion.android.app.plex.ui.adapter.decoration.SpacingItemDecoration;
import com.onion.android.app.plex.vm.HomeViewModel;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexFragmentHomeBinding;

import javax.inject.Inject;

public class HomeFragment extends PlexBaseFragment<PlexFragmentHomeBinding> {

    @Inject
    FeaturedAdapter mFeaturedAdapter;

    @Inject
    MediaRepository mediaRepository;

    HomeViewModel viewModel;

    @Override
    public void initViewModel() {
        viewModel = mViewModelProvider.get(HomeViewModel.class);
    }

    @Override
    public void initView() {
        if (Tools.checkIfHasNetwork(requireContext())) {
            viewModel.initData();
            onLoadFeaturedMovies();
            onLoadLatestChannel();
        }
    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_home;
    }

    // 获取热门影视
    private void onLoadFeaturedMovies() {
        // 当Item的高度如是固定的，设置这个属性为true可以提高性能，尤其是当RecyclerView有条目插入、删除时性能提升更明显。RecyclerView在条目数量改变，会重新测量、布局各个item，如果设置了setHasFixedSize(true)，由于item的宽高都是固定的，adapter的内容改变时，RecyclerView不会整个布局都重绘
        mBinding.rvFeatured.setHasFixedSize(true);
        // 启用或禁用此视图的嵌套滚动。
        mBinding.rvFeatured.setNestedScrollingEnabled(false);
        mBinding.rvFeatured.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvFeatured.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(requireActivity(), 0), true));
        mBinding.rvFeatured.setAdapter(mFeaturedAdapter);
        // 设置缓存大小
        mBinding.rvFeatured.setItemViewCacheSize(8);
        // 使RecyclerView像ViewPager一样的效果，一次只能滑一页，而且居中显示
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mBinding.rvFeatured);
        // banner 条监听 RecyclerView
        mBinding.indicator.attachToRecyclerView(mBinding.rvFeatured, pagerSnapHelper);
        mBinding.indicator.createIndicators(mFeaturedAdapter.getItemCount(), 0);
        // 注册一个新的观察者以侦听数据更改
        mFeaturedAdapter.registerAdapterDataObserver(mBinding.indicator.getAdapterDataObserver());
        viewModel.featuredMoviesMutableLiveData.observe(getViewLifecycleOwner(), featured -> {
            mFeaturedAdapter.addFeatured(featured.getFeatured(), requireActivity(), mediaRepository);
            viewModel.mFeaturedLoaded = true;
            viewModel.mScrollLoaded = true;
            checkDataLoaded();
        });
    }

    private void onLoadLatestChannel() {
        viewModel.latestStreamingMutableLiveData.observe(getViewLifecycleOwner(), latestStream -> {
            if (latestStream.getStreaming().isEmpty()) {
                mBinding.linearLatestChannels.setVisibility(View.GONE);
            }
            LatestStreamAdapter adapter = new LatestStreamAdapter();
            mBinding.rvLatestStreaming.setAdapter(adapter);
            mBinding.rvLatestStreaming.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
            mBinding.rvLatestStreaming.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(requireActivity(), 0), true));
            mBinding.rvLatestStreaming.setHasFixedSize(true);
            mBinding.rvLatestStreaming.setItemViewCacheSize(4);
            adapter.submitList(latestStream.getStreaming());
        });
    }

    private void onLoadRecommend() {
        viewModel.latestStreamingMutableLiveData.observe(getViewLifecycleOwner(), latestStream -> {
            if (latestStream.getStreaming().isEmpty()) {
                mBinding.linearLatestChannels.setVisibility(View.GONE);
            }
            LatestStreamAdapter adapter = new LatestStreamAdapter();
            mBinding.rvLatestStreaming.setAdapter(adapter);
            mBinding.rvLatestStreaming.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
            mBinding.rvLatestStreaming.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(requireActivity(), 0), true));
            mBinding.rvLatestStreaming.setHasFixedSize(true);
            mBinding.rvLatestStreaming.setItemViewCacheSize(4);
            adapter.submitList(latestStream.getStreaming());
        });
    }

    // 检查数据请求状态
    private void checkDataLoaded() {
        if (viewModel.checkDataLoaded()) {
            mBinding.scrollView.setVisibility(View.VISIBLE);
            mBinding.progressBar.setVisibility(View.GONE);
        }
    }
}
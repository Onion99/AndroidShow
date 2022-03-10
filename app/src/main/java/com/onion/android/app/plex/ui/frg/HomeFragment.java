package com.onion.android.app.plex.ui.frg;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.onion.android.R;
import com.onion.android.app.base.PlexBaseFragment;
import com.onion.android.app.plex.data.repository.MediaRepository;
import com.onion.android.app.plex.ui.adapter.FeaturedAdapter;
import com.onion.android.app.plex.ui.adapter.LatestStreamAdapter;
import com.onion.android.app.plex.ui.adapter.MovieAdapter;
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

    @Inject
    HomeViewModel viewModel;

    @Override
    public void initView() {
        binding.swipeContainer.setOnRefreshListener(() -> {
            viewModel.initData();
        });
        if (Tools.checkIfHasNetwork(requireContext())) {
            initData();
        } else {
            Toast.makeText(requireContext(), "网路错误,刷新一下吧", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_home;
    }

    private void initData() {
        viewModel.initData();
        loadFeaturedMovies();
        loadLatestChannel();
        loadRecommendMovies();
        loadTrendingMovies();
        loadReleaseMovies();
        loadPopularSeries();
        loadPopularMovies();
    }

    // 获取热门影视
    private void loadFeaturedMovies() {
        binding.rvFeatured.setAdapter(mFeaturedAdapter);
        initCommonRv(binding.rvFeatured);
        // 使RecyclerView像ViewPager一样的效果，一次只能滑一页，而且居中显示
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.rvFeatured);
        // banner 条监听 RecyclerView
        binding.indicator.attachToRecyclerView(binding.rvFeatured, pagerSnapHelper);
        binding.indicator.createIndicators(mFeaturedAdapter.getItemCount(), 0);
        // 注册一个新的观察者以侦听数据更改
        mFeaturedAdapter.registerAdapterDataObserver(binding.indicator.getAdapterDataObserver());
        viewModel.featuredMoviesMutableLiveData.observe(getViewLifecycleOwner(), featured -> {
            mFeaturedAdapter.addFeatured(featured.getFeatured(), requireActivity(), mediaRepository);
            viewModel.mFeaturedLoaded = true;
            viewModel.mScrollLoaded = true;
            binding.swipeContainer.setRefreshing(false);
            checkDataLoaded();
        });
    }

    private void loadLatestChannel() {
        // 获取代表Fragment's View生命周期的LifecycleOwner 。 在大多数情况下，这反映了 Fragment 本身的生命周期，但在detached Fragment 的情况下，Fragment 的生命周期可能比 View 本身的生命周期长得多
        viewModel.latestStreamingMutableLiveData.observe(getViewLifecycleOwner(), movies -> {
            if (movies.getStreaming().isEmpty()) {
                binding.llLatestChannels.setVisibility(View.GONE);
            }
            LatestStreamAdapter adapter = new LatestStreamAdapter();
            binding.rvLatestStreaming.setAdapter(adapter);
            initCommonRv(binding.rvLatestStreaming);
            adapter.submitList(movies.getStreaming());
        });
    }

    private void loadRecommendMovies() {
        viewModel.recommendedMovieMutableLiveData.observe(getViewLifecycleOwner(), movies -> {
            if (movies.getRecommended().isEmpty()) {
                binding.llRecommendMovies.setVisibility(View.GONE);
            }
            MovieAdapter adapter = new MovieAdapter();
            binding.rvRecommendMovies.setAdapter(adapter);
            initCommonRv(binding.rvRecommendMovies);
            adapter.submitList(movies.getRecommended());
        });
    }

    private void loadTrendingMovies() {
        viewModel.trendingMovieMutableLiveData.observe(getViewLifecycleOwner(), movies -> {
            if (movies.getTrending().isEmpty()) {
                binding.llTrendingMovies.setVisibility(View.GONE);
            }
            MovieAdapter adapter = new MovieAdapter();
            binding.rvTrendingMovies.setAdapter(adapter);
            initCommonRv(binding.rvTrendingMovies);
            adapter.submitList(movies.getTrending());
        });
    }

    private void loadReleaseMovies() {
        viewModel.movieReleaseMutableLiveData.observe(getViewLifecycleOwner(), movies -> {
            if (movies.getLatest().isEmpty()) {
                binding.llReleaseMovies.setVisibility(View.GONE);
            }
            MovieAdapter adapter = new MovieAdapter();
            binding.rvReleaseMovies.setAdapter(adapter);
            initCommonRv(binding.rvReleaseMovies);
            adapter.submitList(movies.getLatest());
        });
    }

    private void loadPopularSeries() {
        viewModel.popularSeriesMutableLiveData.observe(getViewLifecycleOwner(), movies -> {
            if (movies.getPopularSeries() == null || movies.getPopularSeries().isEmpty()) {
                binding.llPopularSeries.setVisibility(View.GONE);
            }
            MovieAdapter adapter = new MovieAdapter();
            binding.rvPopularSeries.setAdapter(adapter);
            initCommonRv(binding.rvPopularSeries);
            adapter.submitList(movies.getPopularSeries());
        });
    }


    private void loadPopularMovies() {
        viewModel.popularMoviesMutableLiveData.observe(getViewLifecycleOwner(), movies -> {
            if (movies.getPopularMovies().isEmpty()) {
                binding.llPopularMovies.setVisibility(View.GONE);
            }
            MovieAdapter adapter = new MovieAdapter();
            binding.rvPopularMovies.setAdapter(adapter);
            initCommonRv(binding.rvPopularMovies);
            adapter.submitList(movies.getPopularMovies());
        });
    }


    private void initCommonRv(RecyclerView recyclerView) {
        // 当Item的高度如是固定的，设置这个属性为true可以提高性能，尤其是当RecyclerView有条目插入、删除时性能提升更明显。RecyclerView在条目数量改变，会重新测量、布局各个item，如果设置了setHasFixedSize(true)，由于item的宽高都是固定的，adapter的内容改变时，RecyclerView不会整个布局都重绘
        recyclerView.setHasFixedSize(true);
        // 如果此属性设置为 true，则视图将被允许使用当前层次结构中的兼容父视图启动嵌套滚动操作。 如果此视图未实现嵌套滚动，则不会产生任何效果。 在嵌套滚动正在进行时禁用嵌套滚动具有stopping嵌套滚动的效果
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(requireActivity(), 0), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(4);
    }

    // 检查数据请求状态
    private void checkDataLoaded() {
        if (viewModel.checkDataLoaded()) {
            binding.scrollView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}
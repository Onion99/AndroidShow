package com.onion.android.app.plex.ui.frg;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.onion.android.R;
import com.onion.android.app.base.PlexBaseFragment;
import com.onion.android.app.plex.ui.adapter.SearchAdapter;
import com.onion.android.app.plex.ui.adapter.decoration.SpacingItemDecoration;
import com.onion.android.app.plex.vm.SearchViewModel;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexFragmentDiscoverBinding;

public class DiscoverFragment extends PlexBaseFragment<PlexFragmentDiscoverBinding> {

    SearchViewModel searchViewModel;

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_discover;
    }

    @Override
    public void initViewModel() {
        searchViewModel = mViewModelProvider.get(SearchViewModel.class);
    }

    @Override
    public void initView() {
        loadToolbar(mBinding.toolbar, null);
        setSystemBarTransparent(getActivity());
        mBinding.rvSuggested.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(requireActivity(), 0), true));
        searchViewModel.getSuggestedMovies();
        searchViewModel.movieDetailMutableLiveData.observe(getViewLifecycleOwner(), suggested -> {
            mBinding.setAdapter(new SearchAdapter(suggested.getSuggested()));
            mBinding.setVm(searchViewModel);
        });
    }
}

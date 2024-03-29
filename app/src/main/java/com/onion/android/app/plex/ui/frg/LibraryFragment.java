package com.onion.android.app.plex.ui.frg;

import static com.onion.android.app.constants.PlexConstants.SERVER_BASE_URL;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.onion.android.R;
import com.onion.android.app.base.PlexBaseFragment;
import com.onion.android.app.plex.ui.adapter.VpFragmentAdapter;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexFragmentLibraryBinding;

public class LibraryFragment extends PlexBaseFragment<PlexFragmentLibraryBinding> {

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_library;
    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
    }

    @Override
    public void initView() {
        loadToolbar(binding.toolbar, null);
        setHasOptionsMenu(true);
        Tools.loadHttpImg(binding.logoImageTop, SERVER_BASE_URL + "image/minilogo");
        setUpTabs();
    }

    private void setUpTabs() {
        setupViewPager(binding.viewPager);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    if (position == 0)
                        tab.setText(getResources().getString(R.string.movies));
                    else if (position == 1) {
                        tab.setText(getResources().getString(R.string.series));
                    } else {
                        tab.setText(getResources().getString(R.string.animes));
                    }
                }).attach();
    }

    private void setupViewPager(ViewPager2 viewPager) {
        VpFragmentAdapter vpFragmentAdapter = new VpFragmentAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setOffscreenPageLimit(3);
        vpFragmentAdapter.addFragment(new MoviesFragment());
        vpFragmentAdapter.addFragment(new SeriesFragment());
        vpFragmentAdapter.addFragment(new AnimesFragment());
        viewPager.setAdapter(vpFragmentAdapter);
    }
}

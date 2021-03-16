package com.onion.android.app.plex.ui.frg;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.onion.android.R;
import com.onion.android.databinding.PlexFragmentHomeBinding;
import com.onion.android.java.base.PlexBaseFragment;

public class HomeFragment extends PlexBaseFragment<PlexFragmentHomeBinding> {

    @Override
    public void initView() {

    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_fragment_home;
    }

}
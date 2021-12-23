package com.onion.android.app.plex.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.onion.android.R;
import com.onion.android.app.base.PlexBaseActivity;
import com.onion.android.app.plex.ui.frg.DiscoverFragment;
import com.onion.android.app.plex.ui.frg.HomeFragment;
import com.onion.android.app.plex.ui.frg.LibraryFragment;
import com.onion.android.databinding.PlexActivityMainBinding;

public class PlexMainActivity extends PlexBaseActivity<PlexActivityMainBinding> {


    @Override
    public void initViewModel() {

    }

    @Override
    public void initView() {
        hideSystemBar(this, true);
        changeFragment(new HomeFragment(), HomeFragment.class.getSimpleName());
        onNavigationItemClick();
    }

    private void onNavigationItemClick(){
        mBinding.navigation.setOnNavigationItemSelectedListener
                (item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            changeFragment(new HomeFragment(), HomeFragment.class
                                    .getSimpleName());
                            break;
                        case R.id.navigation_search:
                            changeFragment(new DiscoverFragment(), DiscoverFragment.class.getSimpleName());
                            break;
                        case R.id.navigation_browse:
                            changeFragment(new LibraryFragment(), LibraryFragment.class
                                    .getSimpleName());
                            break;
                        default:
                            break;
                    }
                    return true;
                });
    }

    private void changeFragment(Fragment fragment,String tagFragmentName){
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 在此FragmentManager关联的Fragment进行启动一系列编辑操作
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 首先获取当前显示的Fragment
        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if(currentFragment != null){
            fragmentTransaction.hide(currentFragment);
        }
        // 查找由给定标签标识的Fragment，该Fragment在从XML扩展时或在事务中添加时提供
        Fragment showFragment = fragmentManager.findFragmentByTag(tagFragmentName);
        if(showFragment == null){
            showFragment = fragment;
            // 将Fragment添加到活动状态
            fragmentTransaction.add(R.id.fragment_container, showFragment, tagFragmentName);
        }else{
            // 显示以前隐藏的Fragment。 这仅与将视图添加到容器的Fragment有关
            fragmentTransaction.show(showFragment);
        }
        // 在此FragmentManager中将当前活动的片段设置为主要导航片段。
        fragmentTransaction.setPrimaryNavigationFragment(showFragment);
        // 设置是否允许优化事务内和事务之间的操作。 这将删除多余的操作
        fragmentTransaction.setReorderingAllowed(true);
        // 允许状态丢失，避免一些配置变更导致的异常问题
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_activity_main;
    }


}
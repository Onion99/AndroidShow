package com.onion.android.java.base.strictdatabinding;


import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.onion.android.R;


public abstract class DataBindingActivity extends AppCompatActivity {

    private ViewDataBinding mBinding;
    private TextView mTvStrictModeTip;

    // 子类需要实现
    protected abstract void initViewMode();

    protected abstract DataBindingConfig getDataBindingConfig();

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewMode();

        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        ViewDataBinding binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0; i < bindingParams.size(); i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }

        mBinding = binding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // DataBinding 解绑
        mBinding.unbind();
        mBinding = null;
    }

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     * 可见此架构意图是在非必要情况下，不要直接操作View实例
     *
     * @return binding
     */
    protected ViewDataBinding getBinding() {
        if(isDebug() && mBinding != null){
            if(mTvStrictModeTip == null){
                mTvStrictModeTip = new TextView(getApplicationContext());
                mTvStrictModeTip.setAlpha(0.1f);
                mTvStrictModeTip.setTextSize(14);
                mTvStrictModeTip.setBackgroundColor(Color.TRANSPARENT);
                mTvStrictModeTip.setText(R.string.debug_activity_databinding_warning);
                ((ViewGroup)mBinding.getRoot()).addView(mTvStrictModeTip);
            }
        }
        return mBinding;
    }

    /**
     * Debug 状态判断
     * 位与运算符（&）: 两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0
     */
    public boolean isDebug() {
        return getApplicationContext().getApplicationInfo() != null &&
                (getApplicationContext().getApplicationInfo().flags &
                        ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}

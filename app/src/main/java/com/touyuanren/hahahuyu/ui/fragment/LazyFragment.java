package com.touyuanren.hahahuyu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.touyuanren.hahahuyu.widget.CustomProgressDialog;

/**
 * Created by Jing on 2016/9/26.
 */
public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;
    private CustomProgressDialog progressDialog;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new CustomProgressDialog(getActivity());
        /* 为true表示fragment可以显示menu菜单 */
        setHasOptionsMenu(true);
    }
    /**
     * dialog 开关控制器
     */
    protected void toggleShowLoading(boolean toggle) {
        if (toggle) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    public void showLoading() {
        toggleShowLoading(true);
    }

    public void hideLoading() {
        toggleShowLoading(false);
    }
    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}
}

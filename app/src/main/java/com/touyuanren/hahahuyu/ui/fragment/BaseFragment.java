package com.touyuanren.hahahuyu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.touyuanren.hahahuyu.widget.CustomProgressDialog;


/**
 * 所有Fragment的父类
 */
public abstract class BaseFragment extends Fragment {

    private CustomProgressDialog progressDialog;

    public Bundle savedInstanceState_;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new CustomProgressDialog(getActivity());
        /* 为true表示fragment可以显示menu菜单 */
        setHasOptionsMenu(true);
        savedInstanceState_ = savedInstanceState;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return getShowView(inflater);
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

    /**
     * 布局加载
     *
     * @return
     */
    public abstract View getShowView(LayoutInflater inflater);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    /**
     * message Toast
     *
     * @param stringID
     */
    public void showMessage(int stringID) {
        showMessage(getString(stringID));
    }

    /**
     * message Toast
     *
     * @param msg
     */
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示重新登录；进入到登录界面
     */
    public void loginAgain() {
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//        showMessage(R.string.please_login_now);
    }
}

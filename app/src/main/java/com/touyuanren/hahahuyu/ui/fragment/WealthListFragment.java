package com.touyuanren.hahahuyu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.touyuanren.hahahuyu.R;

/**
 * Created by Jing on 2016/1/27.
 * 按照分类，显示各种排名；
 */
public class WealthListFragment extends Fragment implements View.OnClickListener {

    /**
     * 主办方榜单
     */
    private RelativeLayout sponsor_wealthlist;
    /**
     * 选手榜单
     */
    private RelativeLayout playerList;
    /**
     * 赞助榜单
     */
    private RelativeLayout zanZhuList;
    /**
     * 当前的页面view
     */
    private View contentView;
    /**
     * 主办方列表
     */
    private SponsorListFrag mSponsorListFrag;
    /**
     * 选手列表
     */
    private PlayerListFrag mPlayerListFrag;
    /**
     * 赞助商列表
     */
    private ZanZhuListFrag mZanZhuListFrag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.wealth_list_fragment, null);
        initView();
        initData();
        return contentView;
    }

    private void initData() {
        clickChangeUI(0);
        changeFragment(R.id.rl_sponsor_rank);
    }

    private void initView() {
        sponsor_wealthlist = (RelativeLayout) contentView.findViewById(R.id.rl_sponsor_rank);
        playerList = (RelativeLayout) contentView.findViewById(R.id.rl_player_rank);
        zanZhuList = (RelativeLayout) contentView.findViewById(R.id.rl_zanzhu_list);
        //绑定监听
        sponsor_wealthlist.setOnClickListener(this);
        playerList.setOnClickListener(this);
        zanZhuList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sponsor_rank:
                clickChangeUI(0);
                changeFragment(R.id.rl_sponsor_rank);
                break;
            case R.id.rl_player_rank:
                clickChangeUI(1);
                changeFragment(R.id.rl_player_rank);
                break;
            case R.id.rl_zanzhu_list:
                clickChangeUI(2);
                changeFragment(R.id.rl_zanzhu_list);
                break;
        }
    }

    /**
     * 通过点击button切换fragment，
     *
     * @param checkedId：点击的button的id
     */
    private void changeFragment(int checkedId) {
        //Fragment事物对象
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        hideAllFragment(ft);

        /**
         * 每次Fragment切换时都需要实时修改标题内容
         */
        switch (checkedId) {
            case R.id.rl_sponsor_rank:
                if (null == mSponsorListFrag) {
                    mSponsorListFrag = new SponsorListFrag();
                }

                if (mSponsorListFrag.isAdded()) {
                    ft.show(mSponsorListFrag);
                } else {
                    ft.add(R.id.ll_fragment_container, mSponsorListFrag);
                }
                break;

            case R.id.rl_player_rank:
                if (null == mPlayerListFrag) {
                    mPlayerListFrag = new PlayerListFrag();
                }

                if (mPlayerListFrag.isAdded()) {
                    ft.show(mPlayerListFrag);
                } else {
                    ft.add(R.id.ll_fragment_container, mPlayerListFrag);
                }

                break;
            case R.id.rl_zanzhu_list:
                if (null == mZanZhuListFrag) {
                    mZanZhuListFrag = new ZanZhuListFrag();
                }

                if (mZanZhuListFrag.isAdded()) {
                    ft.show(mZanZhuListFrag);
                } else {
                    ft.add(R.id.ll_fragment_container, mZanZhuListFrag);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 在切换Fragment时隐藏所有的Fragment
     */
    private void hideAllFragment(FragmentTransaction ft) {
        if (null != ft) {
            if (null != mSponsorListFrag) {
                ft.hide(mSponsorListFrag);
            }

            if (null != mPlayerListFrag) {
                ft.hide(mPlayerListFrag);
            }

            if (null != mZanZhuListFrag) {
                ft.hide(mZanZhuListFrag);
            }
        }
    }

    /**
     * 修改点击按钮状态
     */
    private void clickChangeUI(int targetId) {
        sponsor_wealthlist.setSelected(false);
        playerList.setSelected(false);
        zanZhuList.setSelected(false);
        switch (targetId) {
            case 0:
                sponsor_wealthlist.setSelected(true);
                break;

            case 1:
                playerList.setSelected(true);
                break;

            case 2:
                zanZhuList.setSelected(true);
                break;
        }
    }
}
package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.FriendGroup;

import java.util.List;

/**
 * Created by Jing on 2017/2/21.
 */

public class FriendViewAdapter   extends BaseAdapter {
    private Context mContext;
    private List<FriendGroup> mFriendGroups;
    private boolean isShowGroupItem=false;

    public FriendViewAdapter(Context mContext,List<FriendGroup> mFriendGroups)
    {
        this.mContext=mContext;
        this.mFriendGroups = mFriendGroups;
    }

    @Override
    public int getCount()
    {
        return mFriendGroups.size();
    }

    @Override
    public Object getItem(int Index)
    {
        return mFriendGroups.get(Index);
    }

    @Override
    public long getItemId(int Index)
    {
        return Index;
    }

    @Override
    public View getView(final int Index, View mView, ViewGroup mParent)
    {
        mView= LayoutInflater.from(mContext).inflate(R.layout.layout_group, null);
        //设置分组的名称
        ((TextView)mView.findViewById(R.id.Group_GroupName)).setText(mFriendGroups.get(Index).getGroupName());
        //设置分组容量
        String mItemsCount=String.valueOf(mFriendGroups.get(Index).getGroupItems().size());
        ((TextView)mView.findViewById(R.id.Group_ItemCount)).setText(mItemsCount);
        //设置分组下的列表
        final ListView ItemsList=(ListView)mView.findViewById(R.id.GroupItemList);
        GroupItemAdapter mAdapter=new GroupItemAdapter(mContext, mFriendGroups.get(Index).getGroupItems());
        ItemsList.setAdapter(mAdapter);
        ItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int index, long id)
            {
                Toast.makeText(mContext, "Item "+index+" Click！", Toast.LENGTH_LONG).show();
            }
        });
        //设置分组小的列表高度
        setGroupHeight(ItemsList);
        //给分组添加Click事件
        final LinearLayout GroupLayout=(LinearLayout)mView.findViewById(R.id.GroupLayout);
//        final RelativeLayout GroupLayout=(RelativeLayout)mView.findViewById(R.id.GroupLayout);
        GroupLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!isShowGroupItem)
                {
                    ItemsList.setVisibility(View.VISIBLE);
//                    GroupLayout.setBackgroundResource(R.drawable.group_bg_open);
                    isShowGroupItem=true;
                }
                else
                {
                    ItemsList.setVisibility(View.GONE);
//                    GroupLayout.setBackgroundResource(R.drawable.grop_bg_close);
                    isShowGroupItem=false;
                }
            }
        });
        return mView;
    }

    /*
     * 这是一个神奇的方法，在所有的View嵌套问题中都需要解决这个问题
     */
    private void setGroupHeight(ListView mListView)
    {
        int mTotalHeight=0;
        ListAdapter mAdapter=mListView.getAdapter();
        for(int i=0;i<mAdapter.getCount();i++)
        {
            View ItemView=mAdapter.getView(i, null, mListView);
            ItemView.measure(0, 0);
            mTotalHeight+=ItemView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams mParams=mListView.getLayoutParams();
        mParams.height=mTotalHeight;
        mListView.setMinimumHeight(mTotalHeight);
    }

}

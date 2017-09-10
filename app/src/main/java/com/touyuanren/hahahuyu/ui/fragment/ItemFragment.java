package com.touyuanren.hahahuyu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jing on 2016/3/14.
 */
public class ItemFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View contextView = inflater.inflate(R.layout.fragment_item, container, false);
//        TextView mTextView = (TextView) contextView.findViewById(R.id.textview);
        TextView textView = new TextView(getContext());
        //获取Activity传递过来的参数
        Bundle mBundle = getArguments();
        String title = mBundle.getString("arg");
        textView.setText(title);

        return textView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

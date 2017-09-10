package com.touyuanren.hahahuyu.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.touyuanren.hahahuyu.R;

/**
 * Created by Administrator on 2017/4/7 0007.
 */
public class ShowPopUp extends PopupWindow {
    private Context context;

    public String num$;

    public ShowPopUp(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_reward, null);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.update();

        et = (EditText) view.findViewById(R.id.et_reward);
        rl = (RelativeLayout) view.findViewById(R.id.rl_ds_ok);
        iv = (ImageView) view.findViewById(R.id.iv_ds_close);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view);
            }
        });
    }

    EditText et;
    RelativeLayout rl;
    ImageView iv;

    public void et_click(View.OnClickListener listener){
        num$ = et.getText().toString();
        rl.setOnClickListener(listener);
    }

    public void show(View view) {
        if (!this.isShowing()) {
            this.showAtLocation(view, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }
}

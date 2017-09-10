package com.touyuanren.hahahuyu.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.touyuanren.hahahuyu.R;


/**
 * Created by ice_coffee on 2016/4/13.
 */
public class CustomProgressDialog extends Dialog
{
    public CustomProgressDialog(Context context)
    {
        super(context, R.style.confirm_dialog);
        View view = View.inflate(context, R.layout.dialog_progress_loading, null);

        setCanceledOnTouchOutside(false);

        setContentView(view);
    }

    /**
     * 重写onBackPressed方法
     * 当加载提示框显示的时候禁止back键的操作
     */
    @Override
    public void onBackPressed()
    {
        return;
    }
}

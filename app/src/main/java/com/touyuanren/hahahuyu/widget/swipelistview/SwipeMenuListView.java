package com.touyuanren.hahahuyu.widget.swipelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class SwipeMenuListView extends ListView implements SwipeMenuHelper.Callback{

    protected SwipeMenuHelper mHelper;

    public SwipeMenuListView(Context context) {
        super(context);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        mHelper = new SwipeMenuHelper(getContext(), this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercepted = super.onInterceptTouchEvent(ev);
        // ignore Multi-Touch
        if (ev.getActionIndex() != 0) return true;
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isIntercepted = mHelper.handleLvDownTouchEvent(ev, isIntercepted);
                break;
        }
        return isIntercepted;
    }

    @Override
    public View transformTouchingView(int touchingPosition, View touchingView) {
        return touchingView;
    }
//    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }

}

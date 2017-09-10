package com.touyuanren.hahahuyu.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.utils.CommonUtils;

public class ShowPicActivity extends Activity {
    private ImageView mImg;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showpic);
        mImg = (ImageView) findViewById(R.id.img);

        int Width = getWindowManager().getDefaultDisplay().getWidth();

        path = getIntent().getStringExtra("imgPath");
        if (path != null) {
            int scale = CommonUtils.getScale(getApplicationContext(), path, Width);
            Options opts = new Options();
            opts.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
            mImg.setImageBitmap(bitmap);
        }
    }

    public void back(View v) {
        onBackPressed();
    }

    public void delete(View v) {
            finish();

    }
}

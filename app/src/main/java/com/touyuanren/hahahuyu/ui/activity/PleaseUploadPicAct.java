package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.UserAlbumInfo;
import com.touyuanren.hahahuyu.ui.activity.account.UploadPicActivity;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/10/25.
 * 请上传照片;相册中没有照片
 */
public class PleaseUploadPicAct extends BaseActivity {
    private Button btn;
    private  int  index;
    private ArrayList<UserAlbumInfo> albumList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_please_uploadpic);
        Bundle bundle=getIntent().getExtras();
        index= bundle.getInt("index");
        albumList= (ArrayList<UserAlbumInfo>) bundle.getSerializable("albumlist");
        setTitleLeftBtn();
        btn = (Button) findViewById(R.id.btn_upload_pic_act);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(PleaseUploadPicAct.this, UploadPicActivity.class);
                Bundle  bundle=new Bundle();
                bundle.putInt("index", index);
                bundle.putSerializable("albumlist", albumList);
                bundle.putString("act", "PleaseUploadPicAct");
                mIntent.putExtras(bundle);
                startActivity(mIntent);
                finish();
            }
        });
    }
}

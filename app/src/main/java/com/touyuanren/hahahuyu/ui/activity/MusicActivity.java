package com.touyuanren.hahahuyu.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.recorder.Util;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

/**
 * Created by Administrator on 2017/6/26/026.
 */

public class MusicActivity extends BaseActivity{

    TextView music;
    EditText title,content,pirce;

    private static final int REQUEST_RECORD_AUDIO = 0;
    //录音文件路径
    private static final String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/recorded_audio.wav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity);
        ButterKnife.bind(this);

        setTitleLeftBtn();
        setTitleName("录制音频");
        initView();
        initRecord();
    }

    private void initView() {
        music= (TextView) findViewById(R.id.music_tv);
        title= (EditText) findViewById(R.id.music_et_titile);
        content= (EditText) findViewById(R.id.music_et_content);
        pirce= (EditText) findViewById(R.id.music_et_price);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorecd();
            }
        });
    }


    private void initRecord() {
        Util.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    private void recorecd() {
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(AUDIO_FILE_PATH)
                .setColor(ContextCompat.getColor(this, R.color.color_ceshi))
                .setRequestCode(REQUEST_RECORD_AUDIO)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)

                // Start recording
                .record();



    }

    // 使用系统当前日期加以调整作为照片的名称

//    public String getRecordFileName() {
//        Date curDate = new Date(System.currentTimeMillis());
//        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
//        return "Audio_"+formatter.format(curDate)+".wav";
//    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                //创建文件，将录音文件放进来
                File  file=new File(AUDIO_FILE_PATH);
                //上传到服务器
                postRecord(file);
            } else if (resultCode == RESULT_CANCELED) {
                FoToast.showToast("请检查你的网络");
            }
        }
    }

    private void postRecord(File file) {
        if (title.getText().toString().isEmpty()){
            FoToast.toast(MyApplication.getContext(), getString(R.string.please_input_title));
            return;
        }
        if (content.getText().toString().isEmpty()) {
            FoToast.toast(MyApplication.getContext(), getString(R.string.please_input_content));
            return;
        }
        if (pirce.getText().toString().isEmpty()) {
            FoToast.showToast("请输入价格");
            return;
        }
            //提交网络请求
            Map<String, String> formMap = new HashMap<>();
            formMap.put("cat_id","1");
            formMap.put("title",title.getText().toString());
            formMap.put("description",content.getText().toString());
            formMap.put("content",AUDIO_FILE_PATH);
        formMap.put("content","null");
            formMap.put("price",pirce.getText().toString());
            formMap.put("is_show","1");
        Log.e("data", ""+file.getName());
        FoHttp.upLoadFile2(HttpApi.ROOT_PATH + HttpApi.ADD_AUDIO, formMap, file, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }
            @Override
            public void onResponse(String response) {
                Log.e("data", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("1".equals(jsonObject.getString("status"))){
                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });
        }

}

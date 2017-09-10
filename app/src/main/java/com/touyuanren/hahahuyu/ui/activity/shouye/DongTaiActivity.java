package com.touyuanren.hahahuyu.ui.activity.shouye;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

/**
 * 发布动态
 * Created by Administrator on 2017/3/6 0006.
 */
public class DongTaiActivity extends BaseActivity {
    EditText et_dongtai_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongtai);
        setTitleLeftBtn();
        setTitleName(R.string.dongtaifabu);
        setTitleRightBtn(R.string.fabu, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DongTaiActivity.this, "发布", Toast.LENGTH_LONG).show();
//                fabu_dongtai()；
            }
        });

        init();
    }

    private void init() {
        et_dongtai_text = (EditText) findViewById(R.id.et_dongtai_text);
    }

    private void fabu_dongtai(String stringText) {
        //发布动态
//        String url = HttpApi.ROOT_PATH + HttpApi.FABU_DONGTAI;

//        Map<String, String> formMap = new HashMap<>();
//        formMap.put("act", "add_dt");
//        formMap.put("token", FoPreference.getString(FoContents.LOGIN_TOKEN));
//        formMap.put("content", stringText);
//        formMap.put(HttpApi.CHECKTOKEN, HttpApi.KEY_REQUEST);
//        FoHttp.getMsg(url, formMap, new StringCallback() {
//            @Override
//            public void onError(Request request, Exception e) {
//                Toast.makeText(DongTaiActivity.this, R.string.intent_error, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.e("response", response);
//                if (response != null) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String status = jsonObject.getString("status");
//                        if (status.equals("1")) {
//                            HuoDongInfoBean mHuoDongInfoBean = new Gson().fromJson(response, HuoDongInfoBean.class);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }
}

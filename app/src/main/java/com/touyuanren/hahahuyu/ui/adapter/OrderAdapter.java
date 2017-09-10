package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.OrderInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/10/1.
 * 订单适配器
 */
public class OrderAdapter extends BaseAdapter {
    ArrayList<OrderInfo> mList = new ArrayList<>();
    Context context;

    public OrderAdapter() {
    }

    public OrderAdapter(ArrayList<OrderInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setList(ArrayList<OrderInfo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_list, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title_order_item);
            viewHolder.orderNum = (TextView) convertView.findViewById(R.id.tv_order_number_detail);
            viewHolder.goodstype = (TextView) convertView.findViewById(R.id.tv_order_type_detail_item);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_pricer_order_item);
            viewHolder.topay = (TextView) convertView.findViewById(R.id.tv_topay_order_item);
            viewHolder.delete = (TextView) convertView.findViewById(R.id.tv_del_order_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.orderNum.setText(mList.get(position).getOrder_sn());
        viewHolder.price.setText(mList.get(position).getPrice() + "元");
        if (mList.get(position).getPay_status().equals("0")) {
            viewHolder.topay.setText("去支付");
            viewHolder.topay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payOder(position);
                }
            });
        } else if (mList.get(position).getPay_status().equals("2")) {
            viewHolder.topay.setText("交易成功");
        }

        switch (mList.get(position).getGoods_type()) {
            case "0":
                viewHolder.goodstype.setText("充值");
                break;
            case "1":
                viewHolder.goodstype.setText("发布押金");
                break;
            case "2":
                viewHolder.goodstype.setText("购票");
                break;
            case "3":
                viewHolder.goodstype.setText("赞助");
                break;
        }
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delOrder(position);
            }
        });
        return convertView;
    }

    private void payOder(final int position) {
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act","pay_order");
        formMap.put("order_sn", mList.get(position).getOrder_sn());
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.ORDER_PAY, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                if (response!=null){
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")){
                            FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                            notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public class ViewHolder {
        TextView title, orderNum, goodstype, price, topay, delete;
    }

    //获取订单列表
    public void delOrder(final int position) {
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "del_order");
        formMap.put("order_sn", mList.get(position).getOrder_sn());
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.TICKET_ORDER, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("response+delorder", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                            mList.remove(position);
                            notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}

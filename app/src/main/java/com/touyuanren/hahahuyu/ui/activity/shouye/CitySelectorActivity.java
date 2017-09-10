package com.touyuanren.hahahuyu.ui.activity.shouye;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/2/19.
 * 通过城市列表选择城市
 */
public class CitySelectorActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    /**
     * 选择或者定位的当前城市
     */
    private TextView text_currentCity;
    String[] letter = {"热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //原理上是通过每个城市与自己的首字母进行匹配，可以通过类实现；
    String[] cityLetter = {"热门", "Y", "C", "B", "H", "G", "W", "C", "S", "S", "C", "W", "H", "Z", "C", "D", "N", "L", "Q", "S", "S"
            , "L", "N", "H", "X", "H", "S", "Z", "G", "N", "J", "T", "S", "X", "A"};
    String[] cityName = {"上海", "银川", "长沙", "北京", "杭州", "广州", "武汉", "重庆", "上海", "深圳", "长春", "乌鲁木齐", "哈尔滨", "郑州", "成都", "大连", "南昌", "兰州", "齐齐哈尔", "汕头", "苏州"
            , "拉萨", "南京", "呼和浩特", "厦门", "合肥", "沈阳", "张家界", "贵州", "宁夏", "济南", "天津", "石家庄", "西安", "澳门"};
    List<String> letterToCity = new ArrayList<String>();
    int count;
    private ListView lv;

    private ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_sort_list);
        initView();
        initData();
        String str = "";
        for (int i = 0; i < letter.length; i++) {
            str = letter[i];
            boolean isAddLetter = false;
            for (int j = 0; j < cityLetter.length; j++) {
                if (str.equals(cityLetter[j])) {
                    if (!isAddLetter) {
                        letterToCity.add(str);
                        isAddLetter = true;
                    }
                    letterToCity.add(cityName[j]);
                }
            }
        }
        letterToCity.set(0, "热门城市");
        String ssstr = letterToCity.get(0);
        lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(new MyAdapter());

        lv.setOnItemClickListener(this);

        lv1 = (ListView) findViewById(R.id.listView2);
        lv1.setAdapter(new MyAdapter1());
        lv1.setOnItemClickListener(this);
    }

    public void initData() {

    }

    public void initView() {
        text_currentCity = (TextView) findViewById(R.id.current_location);
    }

    class MyAdapter1 extends BaseAdapter {
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return letter.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return letter[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.letter_list, null);
            TextView tv = (TextView) view.findViewById(R.id.letterListTextView);
            tv.setText(letter[position]);
            return view;
        }

    }

    class MyAdapter extends BaseAdapter {

        final static int TYPE_1 = 1;
        final static int TYPE_2 = 2;

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return letterToCity.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return letterToCity.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            for (int i = 0; i < letter.length; i++) {
                if (letterToCity.get(position).equals(letter[i])) {
                    return TYPE_1;
                }
            }
            return TYPE_2;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /**
             * 即使convertView缓存了一些布局，但是在重构时，根本不知道怎么样去让convertView返回你所需要的布局，这时你需
             * 要让adapter知道我当前有哪些布局，我重构Item时的布局选取规则，好让convertView能返回你需要的布局。
             * 需要重写以下两个函数
             * @Override
             * public int getItemViewType(int position) {}这个函数获取在getView中创建的视图的类型
             * @Override
             * public int getViewTypeCount() {}返回在getView中创建视图类型的数量
             * 至于这两个方法的详细用处，自己看api即可
             */
            ViewHolder1 vh1 = null;
            ViewHolder2 vh2 = null;
            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case TYPE_1:
                        convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.letter, null);
                        vh1 = new ViewHolder1();
                        vh1.tv = (TextView) convertView.findViewById(R.id.letterTextView);
                        convertView.setTag(vh1);
                        break;
                    case TYPE_2:
                        convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.city, null);
                        vh2 = new ViewHolder2();
                        vh2.tv = (TextView) convertView.findViewById(R.id.cityTextView);
                        convertView.setTag(vh2);
                        break;
                    default:
                        break;
                }
            } else {
                switch (type) {
                    case TYPE_1:
                        vh1 = (ViewHolder1) convertView.getTag();
                        break;
                    case TYPE_2:
                        vh2 = (ViewHolder2) convertView.getTag();
                        break;
                    default:
                        break;
                }
            }
            switch (type) {
                case TYPE_1:
                    vh1.tv.setText(letterToCity.get(position));
                    break;
                case TYPE_2:
                    vh2.tv.setText(letterToCity.get(position));
                    break;
                default:
                    break;
            }
            return convertView;

            //此方法不可取，刷新时view会刷新
//			boolean isLetter=false;
//			for(int i=0;i<letter.length;i++){
//				if(letterToCity.get(position).equals(letter[i])){
//					isLetter=true;
//					break;
//				}
//			}
//			ViewHolder vh=null;
//			if(convertView==null){
//				vh=new ViewHolder();
//				if(isLetter){
//					convertView=LayoutInflater.from(MainActivity.this).inflate(R.layout.letter, null);
//					vh.tv=(TextView)convertView.findViewById(R.id.letterTextView);
//				}else{
//					convertView=LayoutInflater.from(MainActivity.this).inflate(R.layout.city, null);
//					vh.tv=(TextView)convertView.findViewById(R.id.cityTextView);
//				}
//				convertView.setTag(vh);
//			}else{
//				vh = (ViewHolder) convertView.getTag();
//			}
//			vh.tv.setText(letterToCity.get(position));
//			return convertView;

            //城市列表，修改布局
//			boolean isLetter=false;
//			for(int i=0;i<letter.length;i++){
//				if(letterToCity.get(position).equals(letter[i])){
//					isLetter=true;
//					break;
//				}
//			}
//			View view;
//			ViewHolder vh;
//			if(convertView==null){
//				view=LayoutInflater.from(MainActivity.this).inflate(R.layout.letter_city, null);
//				vh=new ViewHolder();
//				vh.tv=(TextView)view.findViewById(R.id.letterTextView);
//				vh.ll=(LinearLayout)view.findViewById(R.id.ll);
//				convertView=view;
//				convertView.setTag(vh);
//			}else{
//				view=(View)convertView;
//				vh=(ViewHolder)convertView.getTag();
//			}
//			vh.tv.setText(letterToCity.get(position));
//			if(isLetter){
//				vh.ll.setBackgroundColor(Color.LTGRAY);
//				vh.tv.setPadding(5, 5, vh.tv.getPaddingRight(), 5);
//			}else{
//				vh.ll.setBackgroundColor(Color.WHITE);
//				vh.tv.setPadding(10, 10, vh.tv.getPaddingRight(), 10);
//			}
// 			return view;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        switch (parent.getId()) {
            case R.id.listView1:
                boolean isLetter = false;
                for (int i = 0; i < letter.length; i++) {
                    if (letter[i].equals(letterToCity.get(position))) {
                        isLetter = true;
                        break;
                    }
                }
                if (!isLetter) {
                    text_currentCity.setText(letterToCity.get(position));
//                    UserLocation.getInstance().setLocation_city(letterToCity.get(position));
                    Intent mIntent = new Intent(this, MainActivity.class);
                    mIntent.putExtra("location", letterToCity.get(position));
                    setResult(2000, mIntent);
                    Toast.makeText(this, letterToCity.get(position), Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.listView2:
                for (int i = 0; i < letterToCity.size(); i++) {
                    if (letter[position].equals(letterToCity.get(i))) {
                        lv.setSelection(i);
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }
}

class ViewHolder {
    TextView tv;
    LinearLayout ll;
}

class ViewHolder1 {
    TextView tv;
}

class ViewHolder2 {
    TextView tv;
}

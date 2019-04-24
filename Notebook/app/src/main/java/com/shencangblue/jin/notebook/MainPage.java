package com.shencangblue.jin.notebook;

package com.hb.system.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentinformationsystem.R;
import com.hb.bean.Student;
import com.hb.dao.SqlDao;

public class MainPage extends Activity implements  OnCheckedChangeListener {
    private EditText et_name;
    private Button bt_add;
    private String name;
    private RadioGroup rg_group;
    private TextView tv_showsex;
    private String showsex;
    private SQLServer dao;
    private Student stu;
    private Student stu2;
    private List<Student> list;
    private MyAdapter adapter;
    private ListView listView1;
    private PopupWindow pw;
    private TextView delete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        initView();
        initDate();

    }
    //初始化视图
    private void initView() {
        et_name=(EditText) findViewById(R.id.et_name);
        bt_add=(Button) findViewById(R.id.bt_add);
        rg_group=(RadioGroup) findViewById(R.id.rg_group);
        tv_showsex=(TextView) findViewById(R.id.tv_showsex);
        listView1=(ListView) findViewById(R.id.listView1);
    }
    //初始化数据
    private void initDate() {
        dao=new SQLServer(MainPage.this);
        list=dao.findAll();
        adapter=new MyAdapter();
        listView1.setAdapter(adapter);
        rg_group.setOnCheckedChangeListener(this);
        bt_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                name = et_name.getText().toString().trim();
                showsex = tv_showsex.getText().toString().trim();
                stu=new Student(name,showsex);

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(showsex)){
                    Toast.makeText(MainPage.this, "添加信息不能为空", Toast.LENGTH_LONG).show();

                }else{
                    stu2=new Student(name);
                    Student findName = dao.findName(stu2);
                    if(name.equals(findName.getName())){
                        Toast.makeText(MainPage.this, "添加的姓名不能一样！", Toast.LENGTH_SHORT).show();

                    }else{
                        boolean add = dao.add(stu);
                        if(add){
                            list=dao.findAll();
                            adapter.notifyDataSetInvalidated();
                            Toast.makeText(MainPage.this, "添加成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainPage.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        /**
         * listview的条目点击事件
         */
        listView1.setOnItemClickListener(new OnItemClickListener() {
            private String na;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                na = list.get(position).getName();
                View v = View.inflate(MainPage.this, R.layout.adapter_popu_window, null);
                if (pw != null) {
                    pw.dismiss();//让弹出的PopupWindow消失
                    pw = null;
                }
                pw = new PopupWindow(v, -2, -2);
                int [] location=new int[2];
                view.getLocationInWindow(location);
                pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pw.showAtLocation(parent,Gravity.RIGHT+ Gravity.TOP, 20,location[1]-5 );//设置显示的位置
                ScaleAnimation animation = new ScaleAnimation(0.3f, 1f, 0.3f, 1f, Animation.RELATIVE_TO_SELF,
                        Animation.RELATIVE_TO_SELF);//弹出的动画
                animation.setDuration(400);//设置动画时间
                v.startAnimation(animation);//开启动画
                delete = (TextView)v.findViewById(R.id.tv_delete);
                /**
                 * 删除每一个item上的数据
                 */
                delete.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dao.delete(na);
                        list.remove(position);//移除item的条目
                        list=dao.findAll();//调用查询所有重新再查找一遍
                        adapter.notifyDataSetChanged();//更新适配器
                    }
                });
            }
        });

        /**
         * listview的滑动监听
         * 当鼠标上下滑动的时候让PopupWindow消失
         */
        listView1.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if(pw!=null){
                    pw.dismiss();
                    pw=null;
                }
            }
        });
    }

    //按钮组的点击事件
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        //获取变更后的选中项的ID
        int radioButtonId = group.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)MainPage.this.findViewById(radioButtonId);
        //更新文本内容，以符合选中项
        tv_showsex.setText(rb.getText());
    }
    class MyAdapter extends BaseAdapter{

        private String sex2;
        private View view;
        private String name2;
        @SuppressLint("ViewHolder") @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null ;//设置静态类使其初始化
            if(convertView==null){

                holder = new ViewHolder();//创建holder对象
                view = View.inflate(MainPage.this, R.layout.item,null );

                holder.iv_head = (ImageView) view.findViewById(R.id.iv_head);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_n);
                holder.tv_sex = (TextView) view.findViewById(R.id.tv_s);

                view.setTag(holder);//用来保存一些数据结构。
            }else{
                view=convertView;//复用历史缓存
                holder=(ViewHolder) view.getTag();

            }
            name2 = list.get(position).getName();
            sex2 = list.get(position).getSex();
            if("男".equals(sex2)){            //区分性别
                holder.iv_head.setImageResource(R.drawable.nan);
            }else{
                holder.iv_head.setImageResource(R.drawable.nv);
            }
            holder.tv_name.setText(name2);
            holder.tv_sex.setText(sex2);
            return view;
        }
        @Override
        public int getCount() {
            return list.size();    //返回list集合中的数据个数
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }



    }
    //ViewHolder静态类
    static class ViewHolder{
        ImageView iv_head;
        TextView tv_name;
        TextView tv_sex;
    }

}
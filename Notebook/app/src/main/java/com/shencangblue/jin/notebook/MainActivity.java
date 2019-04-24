package com.shencangblue.jin.notebook;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText themeET, contentET, dateET;
    private Button chooseDateBtn, addBtn, queryBtn;
    private ListView reslutLV;
    private LinearLayout titleLL;
    private MyDataBaseHelper myDataBaseHelper;
    private SQLServer server;
    private List<Note>allNotes;
    private Note newNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }
    private void initView() {
        themeET = (EditText) findViewById(R.id.themeET);
        contentET = (EditText) findViewById(R.id.contentET);
        dateET = (EditText) findViewById(R.id.dateET);
        chooseDateBtn = (Button) findViewById(R.id.chooseDateBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        queryBtn = (Button) findViewById(R.id.queryBtn);
        reslutLV = (ListView) findViewById(R.id.resultLV);
        titleLL = (LinearLayout) findViewById(R.id.titleLL);
    }


    private void initData() {
        server = new SQLServer(MainActivity.this);
        allNotes = server.findAll();

        chooseDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(
                        MainActivity.this
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        dateET.setText(String.format("%d-%d-%d", new Object[]{year, month, dayOfMonth}));
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        MyListener myListener = new MyListener();
        addBtn.setOnClickListener(myListener);
        queryBtn.setOnClickListener(myListener);
    }



    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String themeStr = themeET.getText().toString().trim();
            String contentStr = contentET.getText().toString().trim();
            String dateStr = dateET.getText().toString().trim();
            switch (v.getId()) {
                case R.id.addBtn: {
                    titleLL.setVisibility(View.INVISIBLE);
                    if(TextUtils.isEmpty(themeStr) || TextUtils.isEmpty(contentStr)||TextUtils.isEmpty(dateStr)){
                        Toast.makeText(MainActivity.this, "添加信息不能为空", Toast.LENGTH_LONG).show();

                    }else{
                        newNote =new Note(themeStr,contentStr,dateStr);
                        Note findNote =server.findName(newNote);
                        if(themeStr.equals(findNote.getTheme())){
                            Toast.makeText(MainActivity.this, "添加的主题名不能一样！", Toast.LENGTH_SHORT).show();

                        }else{
                            boolean add = server.add(newNote);
                            if(add){
                                allNotes=server.findAll();
                                Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    //额外添加

                    break;
                }
                case R.id.queryBtn: {
                    titleLL.setVisibility(View.VISIBLE);


                    break;
                }
            }
        }

    }


    //初始化数据
    private void initDate() {
        adapter=new MainPage.MyAdapter();
        listView1.setAdapter(adapter);
        rg_group.setOnCheckedChangeListener(this);
        bt_add.setOnClickListener(new View.OnClickListener() {

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
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                pw.showAtLocation(parent, Gravity.RIGHT+ Gravity.TOP, 20,location[1]-5 );//设置显示的位置
                ScaleAnimation animation = new ScaleAnimation(0.3f, 1f, 0.3f, 1f, Animation.RELATIVE_TO_SELF,
                        Animation.RELATIVE_TO_SELF);//弹出的动画
                animation.setDuration(400);//设置动画时间
                v.startAnimation(animation);//开启动画
                delete = (TextView)v.findViewById(R.id.tv_delete);
                /**
                 * 删除每一个item上的数据
                 */
                delete.setOnClickListener(new View.OnClickListener() {

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
        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {

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
    class MyAdapter extends BaseAdapter {

        private String sex2;
        private View view;
        private String name2;
        @SuppressLint("ViewHolder") @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MainPage.ViewHolder holder=null ;//设置静态类使其初始化
            if(convertView==null){

                holder = new MainPage.ViewHolder();//创建holder对象
                view = View.inflate(MainPage.this, R.layout.item,null );

                holder.iv_head = (ImageView) view.findViewById(R.id.iv_head);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_n);
                holder.tv_sex = (TextView) view.findViewById(R.id.tv_s);

                view.setTag(holder);//用来保存一些数据结构。
            }else{
                view=convertView;//复用历史缓存
                holder=(MainPage.ViewHolder) view.getTag();

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


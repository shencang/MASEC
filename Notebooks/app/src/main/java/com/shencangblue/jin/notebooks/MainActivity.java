package com.shencangblue.jin.notebooks;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText themeET, contentET, dateET;
    private Button chooseDateBtn, addBtn, queryBtn;
    private ListView reslutLV;
    private LinearLayout titleLL;
    private MyDataBaseHelper myDataBaseHelper;
    private SQLServer server;
    private List<Note> allNotes;
    private Note newNote;
    private Note otherNote;
    private PopupWindow pw;
    private TextView deleteTv, careTv, modifyTv;
    private MyAdapter adapter;


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
//        deleteTv = (TextView) findViewById(R.id.tv_delete);
//        careTv =(TextView)findViewById(R.id.tv_care);
//        modifyTv = (TextView)findViewById(R.id.tv_modify);
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

        adapter = new MyAdapter();
        reslutLV.setAdapter(adapter);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String themeStr = themeET.getText().toString().trim();
                String contentStr = contentET.getText().toString().trim();
                String dateStr = dateET.getText().toString().trim();
                newNote = new Note(themeStr, contentStr, dateStr);

                if (TextUtils.isEmpty(themeStr) || TextUtils.isEmpty(contentStr) || TextUtils.isEmpty(dateStr)) {
                    Toast.makeText(MainActivity.this, "添加信息不能为空", Toast.LENGTH_LONG).show();

                } else {
                    otherNote = new Note(themeStr);
                    Log.e("ERROFORSQL", themeStr);
                    Note findTheme = server.findName(otherNote);
                    if (themeStr.equals(findTheme.getTheme())) {
                        Toast.makeText(MainActivity.this, "添加的主题名不能一样！", Toast.LENGTH_SHORT).show();

                    } else {
                        boolean add = server.add(newNote);
                        if (add) {
                            allNotes = server.findAll();
                            adapter.notifyDataSetInvalidated();
                            Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        /**
         * listview的条目点击事件
         */
        reslutLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private String na;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                //这里留意 getId()不一定对;
                na = allNotes.get(position).getId();
                View v = View.inflate(MainActivity.this, R.layout.adapter_popu_window, null);
                if (pw != null) {
                    pw.dismiss();//让弹出的PopupWindow消失
                    pw = null;
                }
                pw = new PopupWindow(v, -2, -2);
                int[] location = new int[2];
                view.getLocationInWindow(location);
                pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pw.showAtLocation(parent, Gravity.RIGHT + Gravity.TOP, 20, location[1] - 5);//设置显示的位置
                ScaleAnimation animation = new ScaleAnimation(0.3f, 1f, 0.3f, 1f, Animation.RELATIVE_TO_SELF,
                        Animation.RELATIVE_TO_SELF);//弹出的动画
                animation.setDuration(400);//设置动画时间
                v.startAnimation(animation);//开启动画
                deleteTv = (TextView) v.findViewById(R.id.tv_delete);
                careTv = (TextView) v.findViewById(R.id.tv_care);
                modifyTv = (TextView) v.findViewById(R.id.tv_modify);
                /**
                 * 删除每一个item上的数据
                 */
                deleteTv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        server.delete(na);
                        allNotes.remove(position);//移除item的条目
                        allNotes = server.findAll();//调用查询所有重新再查找一遍
                        adapter.notifyDataSetChanged();//更新适配器
                    }
                });
                /**
                 * 展开一个数据详情功能
                 */
                careTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Note exNote=(server.findId(na));
                        Log.e("-------------->",
                                na +" "+
                                        exNote.getId()
                                        +" "+exNote.getTheme()
                                        +" "+exNote.getContent()
                                        +" "+exNote.getDate());
//                        idTv.setText(exNote.getId());
//                        themeTv.setText(exNote.getTheme());
//                        contentTv.setText(exNote.getContent());
//                        dateTv.setText(exNote.getDate());
                         careCustomizeDialog(exNote);

                    }
                });
                /**
                 * 修改一个数据块功能
                 */
                modifyTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Note exNote=(server.findId(na));
                        Log.e("-------------->",
                                na +" "+
                                        exNote.getId()
                                        +" "+exNote.getTheme()
                                        +" "+exNote.getContent()
                                        +" "+exNote.getDate());
//                        idTv.setText(exNote.getId());
//                        themeTv.setText(exNote.getTheme());
//                        contentTv.setText(exNote.getContent());
//                        dateTv.setText(exNote.getDate());
                        modifyCustomizeDialog(exNote);

                    }
                });

            }
        });

        /**
         * listview的滑动监听
         * 当鼠标上下滑动的时候让PopupWindow消失
         */
        reslutLV.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (pw != null) {
                    pw.dismiss();
                    pw = null;
                }
            }
        });
        /**
         * 查询按钮监听
         */
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();

            }
        });
    }

    /**
     * 查询功能对话框
     */
    private void showInputDialog() {
        final String[] dResult = new String[1];
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("请输入要查询的主题").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        dResult[0] = editText.getText().toString().trim();

                        allNotes.clear();
                        //Log.e("found",newNotes.getId()+" "+newNotes.getTheme()+" "+newNotes.getContent()+" "+newNotes.getDate());
                        allNotes.add(server.findName(new Note(dResult[0])));//调用查询所有重新再查找一遍
                        adapter.notifyDataSetChanged();//更新适配器

                    }
                }).show();
    }

    /**
     * 自定义弹出框———数据详情页
     */
    private void careCustomizeDialog(final Note disNote) {
        /* @setView 装入自定义View ==> R.layout.dialog_customize
         * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
         * dialog_customize.xml可自定义更复杂的View
         */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.care_dialog,null);
        TextView idTv =
                (TextView) dialogView.findViewById(R.id.showIdTv);
        TextView themeTv =
                (TextView) dialogView.findViewById(R.id.showThemeTv);
        TextView contentTv =
                (TextView) dialogView.findViewById(R.id.showContentTv);
        TextView dateTv =
                (TextView) dialogView.findViewById(R.id.showDateTv);
        idTv.setText(disNote.getId());
        themeTv.setText(disNote.getTheme());
        contentTv.setText(disNote.getContent());
        dateTv.setText(disNote.getDate());
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
        customizeDialog.show();
    }

    /**
     * 自定义弹出框———数据修改页
     */
    private void modifyCustomizeDialog(Note exNote) {
        /* @setView 装入自定义View ==> R.layout.dialog_customize
         * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
         * dialog_customize.xml可自定义更复杂的View
         */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.modify_dialog,null);
        TextView idTV = (TextView)dialogView.findViewById(R.id.showIdTv_mod);
        final EditText themeETMod =
                (EditText) dialogView.findViewById(R.id.modThemeEt);
        final EditText contentETMod =
                (EditText) dialogView.findViewById(R.id.modContentEt);
        final EditText dateETMod =
                (EditText) dialogView.findViewById(R.id.modDateEt);
        Button chooseDateBtn =dialogView.findViewById(R.id.modifyDateBtn);
        idTV.setText(exNote.getId());
        SpannableString themeSS = new SpannableString(exNote.getTheme());
        SpannableString contentSS = new SpannableString(exNote.getContent());
        SpannableString dateSS = new SpannableString(exNote.getDate());
        themeETMod.setHint(themeSS);
        contentETMod.setHint(contentSS);
        dateETMod.setHint(dateSS);
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
                        dateETMod.setText(String.format("%d-%d-%d", new Object[]{year, month, dayOfMonth}));
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定修改",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        String themeStr = themeETMod.getText().toString().trim();
                        String contentStr = contentETMod.getText().toString().trim();
                        String dateStr = dateETMod.getText().toString().trim();
                        newNote = new Note(themeStr, contentStr, dateStr);

                        if (TextUtils.isEmpty(themeStr) || TextUtils.isEmpty(contentStr) || TextUtils.isEmpty(dateStr)) {
                            Toast.makeText(MainActivity.this, "修改信息不能使得信息为空", Toast.LENGTH_LONG).show();

                        } else {
                            otherNote = new Note(themeStr);
                            Log.e("ERROFORSQL", themeStr);
                            Note findTheme = server.findName(otherNote);
                            if (themeStr.equals(findTheme.getTheme())) {
                                Toast.makeText(MainActivity.this, "修改的主题名不能和已有的一样！", Toast.LENGTH_SHORT).show();

                            } else {
                                boolean add = server.add(newNote);
                                if (add) {
                                    allNotes = server.findAll();
                                    adapter.notifyDataSetInvalidated();
                                    Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }


                    }
                });
        customizeDialog.show();
    }


    class MyAdapter extends BaseAdapter {
        private String id2;
        private String theme2;
        private String content2;
        private String date2;
        private View view;


        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;//设置静态类使其初始化
            if (convertView == null) {

                holder = new ViewHolder();//创建holder对象
                view = View.inflate(MainActivity.this, R.layout.item, null);

                holder.idTv = (TextView) view.findViewById(R.id.list_idTV);
                holder.themeTv = (TextView) view.findViewById(R.id.list_themeTV);
                holder.contentTv = (TextView) view.findViewById(R.id.list_contentTV);
                holder.dateTv = (TextView) view.findViewById(R.id.list_dateTV);

                view.setTag(holder);//用来保存一些数据结构。
            } else {
                view = convertView;//复用历史缓存
                holder = (ViewHolder) view.getTag();

            }
            //这里注意
            id2 = allNotes.get(position).getId();
            theme2 = allNotes.get(position).getTheme();
            content2 = allNotes.get(position).getContent();
            date2 = allNotes.get(position).getDate();

            holder.idTv.setText(id2);
            holder.themeTv.setText(theme2);
            holder.contentTv.setText(content2);
            holder.dateTv.setText(date2);
            return view;
        }

        @Override
        public int getCount() {
            return allNotes.size();    //返回list集合中的数据个数
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
    static class ViewHolder {
        TextView idTv;
        TextView themeTv;
        TextView contentTv;
        TextView dateTv;
    }

}







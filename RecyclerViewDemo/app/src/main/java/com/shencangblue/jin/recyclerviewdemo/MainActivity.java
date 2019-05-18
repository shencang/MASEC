package com.shencangblue.jin.recyclerviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<Fruit>fruits = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager setLayoutManager=
                new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL) ;
        recyclerView.setLayoutManager(setLayoutManager);
        //recyclerView.setLayoutManager(linearLayoutManager);
        FruitAdapter fruitAdapter = new FruitAdapter(fruits);
        recyclerView.setAdapter(fruitAdapter);
        //initFruit();
        initData();

    }
    private void initData(){
        String []name = new String[]{"apple","banana","cherry"
                                        ,"grape","mango","orange",
                "pear","pineapple","strawberry","watermelon"};
        int []img= {R.drawable.apple_pic,R.drawable.banana_pic,R.drawable.cherry_pic,R.drawable.grape_pic,
                R.drawable.mango_pic,R.drawable.orange_pic,R.drawable.pear_pic,R.drawable.pineapple_pic,
                R.drawable.strawberry_pic,R.drawable.watermelon_pic};
        Random random =new Random();

        int count = 0;
        while (count<200){
            int length =random.nextInt(10);
            fruits.add(new Fruit(getRandomLengthName(name[length]),img[length]));
            count++;
        }


    }

    private  void initFruit(){
        for (int i=0;i<10;i++){
            fruits.add(new Fruit(getRandomLengthName("apple"),R.drawable.apple_pic));
            fruits.add(new Fruit(getRandomLengthName("banana"),R.drawable.banana_pic));
            fruits.add(new Fruit(getRandomLengthName("cherry"),R.drawable.cherry_pic));
            fruits.add(new Fruit(getRandomLengthName("grape"),R.drawable.grape_pic));
            fruits.add(new Fruit(getRandomLengthName("mango"),R.drawable.mango_pic));
            fruits.add(new Fruit(getRandomLengthName("orange"),R.drawable.orange_pic));
            fruits.add(new Fruit(getRandomLengthName("pear"),R.drawable.pear_pic));
            fruits.add(new Fruit(getRandomLengthName("pineapple"),R.drawable.pineapple_pic));
            fruits.add(new Fruit(getRandomLengthName("strawberry"),R.drawable.strawberry_pic));
            fruits.add(new Fruit(getRandomLengthName("watermelon"),R.drawable.watermelon_pic));

        }



    }
    private String getRandomLengthName(String name){
        Random random =new Random();
        int length =random.nextInt(20)+1;
        StringBuilder builder  =new StringBuilder();
        for (int i =0;i<length;i++){
            builder.append(name);
        }
        return builder.toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:{
                Toast.makeText(this,"你想注销.",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.exit:{Toast.makeText(this,"你想退出.",Toast.LENGTH_LONG).show();
                break;}
            default:{break;}
        }

        return  super.onOptionsItemSelected(item);
    }
}

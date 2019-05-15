package com.shencangblue.jin.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fruit>fruits = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        FruitAdapter fruitAdapter = new FruitAdapter(fruits);
        recyclerView.setAdapter(fruitAdapter);
        initFruit();

    }

    private  void initFruit(){
        for (int i=0;i<2;i++){
            fruits.add(new Fruit("apple",R.drawable.apple_pic));
            fruits.add(new Fruit("banana",R.drawable.banana_pic));
            fruits.add(new Fruit("cherry",R.drawable.cherry_pic));
            fruits.add(new Fruit("grape",R.drawable.grape_pic));
            fruits.add(new Fruit("mango",R.drawable.mango_pic));
            fruits.add(new Fruit("orange",R.drawable.orange_pic));
            fruits.add(new Fruit("pear",R.drawable.pear_pic));
            fruits.add(new Fruit("pineapple",R.drawable.pineapple_pic));
            fruits.add(new Fruit("strawberry",R.drawable.strawberry_pic));
            fruits.add(new Fruit("watermelon",R.drawable.watermelon_pic));

        }

    }

}

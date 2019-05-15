package com.shencangblue.jin.recyclerviewdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.MyViewHolder> {
    List<Fruit> fruits = null;
    static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView fruit_image;
        private TextView  fruit_namre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fruit_image  = (ImageView)itemView.findViewById(R.id.fruit_Image_view);
            fruit_namre  = (TextView)itemView.findViewById(R.id.fruit_name);
        }

    }

    public FruitAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = (View) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.fruit_item,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Fruit fruit  = fruits.get(i);
        myViewHolder.fruit_image.setImageResource(fruit.getImageId());
        myViewHolder.fruit_namre.setText(fruit.getName());


    }


    @Override
    public int getItemCount() {
        return fruits.size();
    }
}

package com.example.familyangel.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by FamilyAngel on 10/9/2016.
 */

public class CustomGrid extends BaseAdapter {
    private Context context;
    private movie[] movieArray;
    private static LayoutInflater inflater=null;

    public CustomGrid(Context c, movie[] movieArray ) {
        context = c;
        this.movieArray = movieArray;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return movieArray.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public class Holder
    {
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.gridlayout, null);
        holder.img=(ImageView) rowView.findViewById(R.id.gridImage);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movieArray[position].posterLink).into(holder.img);

        return rowView;
    }
}

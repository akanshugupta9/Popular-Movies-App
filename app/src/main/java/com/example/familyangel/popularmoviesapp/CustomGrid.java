package com.example.familyangel.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by FamilyAngel on 10/9/2016.
 */

public class CustomGrid extends BaseAdapter {
    private Context context;
    private final int[] Imageid;
    private static LayoutInflater inflater=null;

    public CustomGrid(Context c, int[] Imageid ) {
        context = c;
        this.Imageid = Imageid;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Imageid.length;
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
        holder.img.setImageResource(Imageid[position]);

        return rowView;
    }
}

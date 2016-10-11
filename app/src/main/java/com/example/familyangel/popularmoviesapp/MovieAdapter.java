package com.example.familyangel.popularmoviesapp;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

/**
 * Created by FamilyAngel on 10/11/2016.
 */

public class MovieAdapter extends BaseAdapter {
    FragmentActivity context;
    Movie[] movies;
    private static LayoutInflater inflater=null;
    public MovieAdapter(FragmentActivity mainActivity, Movie[] movies) {
        // TODO Auto-generated constructor stub
        this.movies = movies;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return movies.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.gridlayout, null);
        holder.img=(ImageView) rowView.findViewById(R.id.gridImage);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movies[position].posterLink).into(holder.img);

        return rowView;
    }
}

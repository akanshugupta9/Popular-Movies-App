package com.example.familyangel.popularmoviesapp;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by FamilyAngel on 10/11/2016.
 */

public class MovieAdapter extends BaseAdapter {
    FragmentActivity context;
    ArrayList<Movie> movies;
    private static LayoutInflater inflater=null;
    public MovieAdapter(FragmentActivity mainActivity, ArrayList<Movie> movies) {
        // TODO Auto-generated constructor stub
        this.movies = movies;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void add(Movie movie){
        movies.add(movie);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
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

    public void clear(){
        movies.clear();
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

        rowView = inflater.inflate(R.layout.griditem, null);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movies.get(position).posterLink)
                .into((ImageView) rowView.findViewById(R.id.gridImage));

        return rowView;
    }
}

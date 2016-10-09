package com.example.familyangel.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    GridView grid;

    int[] imageId = {
            R.drawable.fifty,
            R.drawable.harry,
            R.drawable.inception,
            R.drawable.inferno,
            R.drawable.inside,
            R.drawable.interstellar,
            R.drawable.pele,
            R.drawable.pi,
            R.drawable.step,
            R.drawable.tarzan,
            R.drawable.transporter
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomGrid adapter = new CustomGrid(MainActivity.this, imageId);
        grid=(GridView)findViewById(R.id.gridItem);
        grid.setAdapter(adapter);

    }
}

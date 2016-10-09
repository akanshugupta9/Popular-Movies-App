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

    String[] imageId = {
            "/z6BP8yLwck8mN9dtdYKkZ4XGa3D.jpg",
            "/uSHjeRVuObwdpbECaXJnvyDoeJK.jpg",
            "/5N20rQURev5CNDcMjHVUZhpoCNC.jpg",
            "/7D6hM7IR0TbQmNvSZVtEiPM3H5h.jpg",
            "/1ZQVHkvOegv5wVzxD2fphcxl1Ba.jpg"
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

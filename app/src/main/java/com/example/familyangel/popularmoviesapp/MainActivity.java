package com.example.familyangel.popularmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import static com.example.familyangel.popularmoviesapp.R.id.container;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainActivityFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    /*GridView grid;

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

    }*/
}

package com.example.familyangel.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by FamilyAngel on 10/11/2016.
 */

public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;

    Movie[] data = {
            new Movie("The Magnificent Seven", "/z6BP8yLwck8mN9dtdYKkZ4XGa3D.jpg"),
            new Movie("Captain America: Civil War", "/5N20rQURev5CNDcMjHVUZhpoCNC.jpg"),
            new Movie("Miss Peregrine's Home for Peculiar Children", "/uSHjeRVuObwdpbECaXJnvyDoeJK.jpg\""),
            new Movie("Suicide Squad", "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg"),
            new Movie("Independence Day: Resurgence", "/9KQX22BeFzuNM66pBA6JbiaJ7Mi.jpg")
    };

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gridlayout, container, false);

        movieAdapter = new MovieAdapter(getActivity(), data);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridItem);
        gridView.setAdapter(movieAdapter);

        return rootView;
    }
}

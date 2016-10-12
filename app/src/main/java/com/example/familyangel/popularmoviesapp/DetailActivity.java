package com.example.familyangel.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by FamilyAngel on 10/12/2016.
 */

public class DetailActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /*private String mForecastStr;
        private static final String FORECAST_SHARE_HASHTAG = "#SunshineAppMadeByAkanshuGupta";
        private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

        public PlaceholderFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detailfragment, menu);

            MenuItem menuItem = menu.findItem(R.id.action_share);
            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForcastIntent());
            } else {
                Log.d(LOG_TAG, "Share Action Provider is null.");
            }
        }*/

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if (intent != null && intent.hasExtra("value")) {
                //Bundle bundle = intent.getExtras();

                Movie movie=
                        (Movie)intent.getSerializableExtra("value");
                //Movie movie = (Movie) intent.getSerializableExtra("movieDetails");


                //final String LOG_TAG = MainActivityFragment.FetchMovieTask.class.getSimpleName();
                //Log.v(LOG_TAG, movie.originalName);
                //Toast.makeText(getActivity(), movie.originalName, Toast.LENGTH_SHORT).show();

                Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+movie.posterLink)
                        .into((ImageView) rootView.findViewById(R.id.detail_poster));
                ((TextView) rootView.findViewById(R.id.detail_title)).setText(movie.originalName);
                ((TextView) rootView.findViewById(R.id.detail_overview)).setText(movie.movieOverview);
                ((TextView) rootView.findViewById(R.id.detail_release_date)).setText(movie.releaseDate);
                ((TextView) rootView.findViewById(R.id.detail_user_rating)).setText(Double.toString(movie.userRating));

                //((TextView) rootView.findViewById(R.id.detail_text)).setText(mForecastStr);
            }
            return rootView;
        }

        /*private Intent createShareForcastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }*/
    }
}
package com.example.familyangel.popularmoviesapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchMovieTask movieTask = new FetchMovieTask();
            movieTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public class FetchMovieTask extends AsyncTask<Void, Void, Void> {

        private Movie[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MOV_RESULTS = "results";
            final String MOV_TITLE = "original_title";
            final String MOV_DATE = "release_date";
            final String MOV_OVERVIEW = "overview";
            final String MOV_POSTER = "poster_path";
            final String MOV_VOTE = "vote_average";



            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MOV_RESULTS);

            Movie[] results = new Movie[movieArray.length()];

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            for(int i = 0; i < movieArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"

                // Get the JSON object representing the day
                JSONObject movieDetails = movieArray.getJSONObject(i);

                Log.v(LOG_TAG, "Forecast entry: " + movieDetails.getString(MOV_TITLE) + movieDetails.getString(MOV_DATE)
                + movieDetails.getString(MOV_OVERVIEW) + movieDetails.getString(MOV_POSTER) + movieDetails.getDouble(MOV_VOTE));


                results[i].originalName = movieDetails.getString(MOV_TITLE);
                results[i].releaseDate = movieDetails.getString(MOV_DATE);
                results[i].movieOverview = movieDetails.getString(MOV_OVERVIEW);
                results[i].posterLink = movieDetails.getString(MOV_POSTER);
                results[i].userRating = movieDetails.getDouble(MOV_VOTE);

                Log.v(LOG_TAG, "Forecast entry: " + results[i].originalName + results[i].releaseDate + results[i].movieOverview
                                + results[i].posterLink + results[i].userRating);
            }


            return results;

        }

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                String baseUrl = "http://api.themoviedb.org/3/movie/popular?";
                String apiKey = "api_key=" + "ENTER_YOUR_KEY_HERE";
                URL url = new URL(baseUrl.concat(apiKey));

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG, "movieJsonStr data: " + movieJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }
    }
}

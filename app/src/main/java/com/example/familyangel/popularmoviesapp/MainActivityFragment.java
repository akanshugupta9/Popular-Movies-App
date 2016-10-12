package com.example.familyangel.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;

/**
 * Created by FamilyAngel on 10/11/2016.
 */

public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;



    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    private void updateMovie(){
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = pref.getString(getString(R.string.pref_sort_by_key),
                getString(R.string.pref_sort_by_popular));
        movieTask.execute(sortBy);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        View rootView = inflater.inflate(R.layout.gridlayout, container, false);

        ArrayList<Movie> data = new ArrayList<Movie>();

        movieAdapter = new MovieAdapter(getActivity(), data);
        for(int i = 0; i < movieAdapter.getCount(); i++){
            Log.v(LOG_TAG, "data : " + movieAdapter.movies.get(i).originalName);
        }

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridItem);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Movie movie = (Movie) movieAdapter.getItem(position);
                //Toast.makeText(getActivity(), movie.originalName, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", movie);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

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
                Movie movie = new Movie();

                //Log.v(LOG_TAG, "Forecast entry: " + movieDetails.getString(MOV_TITLE) + movieDetails.getString(MOV_DATE)
                //+ movieDetails.getString(MOV_OVERVIEW) + movieDetails.getString(MOV_POSTER) + movieDetails.getDouble(MOV_VOTE));


                movie.originalName = movieDetails.getString(MOV_TITLE);
                movie.releaseDate = movieDetails.getString(MOV_DATE);
                movie.movieOverview = movieDetails.getString(MOV_OVERVIEW);
                movie.posterLink = movieDetails.getString(MOV_POSTER);
                movie.userRating = movieDetails.getDouble(MOV_VOTE);

                results[i] = movie;

                //Log.v(LOG_TAG, "Forecast entry: " + results[i].originalName + results[i].releaseDate + results[i].movieOverview
                  //              + results[i].posterLink + results[i].userRating);
            }


            return results;

        }

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected Movie[] doInBackground(String... params) {
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
                String baseUrl = "http://api.themoviedb.org/3/movie/"+params[0]+"?";
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
                //Log.v(LOG_TAG, "movieJsonStr data: " + movieJsonStr);
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
                return  getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            movieAdapter.clear();
            for(int i = 0; i < result.length; i++){
                movieAdapter.add(result[i]);
            }

        }
    }
}

package edu.uci.ics.fabflixmobile.ui.movielist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.data.model.Movie;
import edu.uci.ics.fabflixmobile.ui.login.LoginActivity;
import edu.uci.ics.fabflixmobile.ui.singlemovie.MovieDetailsActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {
    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "ROOT_WAR";
    private final String baseURL = "https://" + host + ":" + port + "/" + domain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        // TODO: this should be retrieved from the backend server
        //final ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Movie> movies = getMovieList();
        //movies.add(new Movie("The Terminal", (short) 2004));
        //movies.add(new Movie("The Final Season", (short) 2007));
        MovieListViewAdapter adapter = new MovieListViewAdapter(this, movies);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = movies.get(position);
            @SuppressLint("DefaultLocale") String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Intent MovieDetailsPage = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
            //go to movie page
            MovieDetailsPage.putExtra("movie", movie);
            startActivity(MovieDetailsPage);
        });
    }
    protected ArrayList<Movie> getMovieList() {
        ArrayList<Movie> movieList = new ArrayList<>();
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest movieListRequest = new StringRequest(Request.Method.GET,
                //actually use search. we need to get query from a search page
                baseURL + "/api/movies",
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        ArrayList<Movie> movies = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            int year = jsonObject.getInt("year");
                            String director = jsonObject.getString("director");
                            JSONArray starsArray = jsonObject.getJSONArray("star");
                            JSONArray genresArray = jsonObject.getJSONArray("genre");
                            ArrayList<String> stars = new ArrayList<>();
                            for (int j = 0; j < starsArray.length(); j++) {
                                String star = starsArray.getString(j);
                                stars.add(star);
                            }
                            ArrayList<String> genres = new ArrayList<>();
                            for (int j = 0; j < genresArray.length(); j++) {
                                String genre = genresArray.getString(j);
                                genres.add(genre);
                            }
                            movieList.add(new Movie(title, year, director, genres, stars));
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    System.out.println("Error");
                });
        queue.add(movieListRequest);
        return movieList;
    }

}
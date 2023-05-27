package edu.uci.ics.fabflixmobile.ui.singlemovie;

import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.model.Movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieDetailsAdapter extends ArrayAdapter<Movie> {
    private final Movie movie;
    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView year;
        TextView director;
        TextView genres;
        TextView stars;
    }

    public MovieDetailsAdapter(Context context, Movie movie) {
        super(context, R.layout.movieDetailsPage, new ArrayList<>());
        this.movie = movie;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movieDetailsPage, parent, false);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.year = convertView.findViewById(R.id.year);
            viewHolder.director = convertView.findViewById(R.id.director);
            viewHolder.genres = convertView.findViewById(R.id.genres);
            viewHolder.stars = convertView.findViewById(R.id.stars);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the movie object via the viewHolder object
        // into the template view.
        viewHolder.title.setText(movie.getName());
        viewHolder.year.setText(String.valueOf(movie.getYear()));
        viewHolder.director.setText(movie.getDirector());
        StringBuilder allGenres = new StringBuilder();
        for (String i: movie.getGenres()) {
            allGenres.append(i).append(" ");
        }
        StringBuilder allStars = new StringBuilder();
        for (String i: movie.getStars()) {
            allStars.append(i).append(" ");
        }
        viewHolder.genres.setText(String.valueOf(allGenres));
        viewHolder.stars.setText(String.valueOf(allStars));
        // Return the completed view to render on screen
        return convertView;
    }
}
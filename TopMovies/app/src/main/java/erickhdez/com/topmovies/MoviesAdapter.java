package erickhdez.com.topmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by erickhdez on 18/3/18.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {
    private LayoutInflater mInflater;

    public MoviesAdapter(@NonNull Context context, ArrayList<Movie> objects) {
        super(context, R.layout.movie_info, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View movieLayout = convertView;
        Movie movie = getItem(position);

        if(movieLayout == null) {
            movieLayout = mInflater.inflate(R.layout.movie_info, null);
        }

        TextView title = movieLayout.findViewById(R.id.txtMovieTitle);
        title.setText(movie.getName());

        TextView starRating = movieLayout.findViewById(R.id.txtStarRating);
        starRating.setText(movie.getStarRating());

        TextView metacriticRating = movieLayout.findViewById(R.id.txtMetascore);
        metacriticRating.setText(movie.getMetascore());

        ImageView poster = movieLayout.findViewById(R.id.imgMoviePoster);
        poster.setImageBitmap(movie.getPoster());

        return movieLayout;
    }
}

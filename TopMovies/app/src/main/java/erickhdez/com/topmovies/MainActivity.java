package erickhdez.com.topmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IReceiveAsyncTaskResponse {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout overLayout = findViewById(R.id.OverLayout);
        overLayout.bringToFront();
        overLayout.setVisibility(View.VISIBLE);

        requestsMovies();
    }

    private void requestsMovies() {
        String url = "http://www.imdb.com/list/ls064079588/";
        String wantedElements = "20";

        new RetriveMovies(this).execute(url, wantedElements);
    }

    @Override
    public void receiveResponse(Object response) {
        ArrayList<Movie> movies = (ArrayList<Movie>) response;
        MoviesAdapter adapter = new MoviesAdapter(this, movies);

        GridView moviesGridView = findViewById(R.id.moviesGridView);
        moviesGridView.setAdapter(adapter);

        RelativeLayout overLayout = findViewById(R.id.OverLayout);
        overLayout.setVisibility(View.INVISIBLE);
    }
}

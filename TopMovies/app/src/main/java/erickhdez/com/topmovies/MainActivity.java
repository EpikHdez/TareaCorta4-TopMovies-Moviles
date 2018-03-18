package erickhdez.com.topmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpMovies();
    }

    private void setUpMovies() {
        String url = "http://www.imdb.com/list/ls064079588/";
        String wantedElements = "20";

        try {
            RetriveMovies retriveMovies = new RetriveMovies();
            JSONArray movies = retriveMovies.execute(url, wantedElements).get();
        } catch(Exception ex) {
            Log.d("ErrorGettingMovies", "setUpMovies: " + ex.getMessage());
        }
    }
}

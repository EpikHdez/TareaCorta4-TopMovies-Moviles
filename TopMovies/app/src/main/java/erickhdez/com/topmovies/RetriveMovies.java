package erickhdez.com.topmovies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by erickhdez on 18/3/18.
 */

public class RetriveMovies extends AsyncTask<String, Void, JSONArray> {
    @Override
    protected JSONArray doInBackground(String... strings) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        String url = strings[0];
        int wantedElements = Integer.parseInt(strings[1]);

        try {
            Document html = Jsoup.connect(url).get();
            Elements top100Movies = html.select("div.lister-item");
            List<Element> top20Movies = top100Movies.subList(0, wantedElements);
            JSONArray parsedMovies = new JSONArray();

            for(Element movie : top20Movies) {
                Element image = movie.selectFirst("img");
                String imageUrl = image.attr("loadlate");

                Element header = movie.selectFirst("h3");
                String movieName = header.selectFirst("a[href]").html();
                String releaseYear = header.selectFirst("span.lister-item-year").html();

                Element ratings = movie.selectFirst("div.ratings-bar");
                String imdbRating = ratings.selectFirst("strong").html();
                String metascore = ratings.selectFirst("span.metascore").html();

                JSONObject movieInfo = new JSONObject();
                movieInfo.put("name", movieName);
                movieInfo.put("year", releaseYear);
                movieInfo.put("star_rating", imdbRating);
                movieInfo.put("metascore", metascore);
                movieInfo.put("image", imageUrl);

                parsedMovies.put(movieInfo);
            }

            return parsedMovies;
        } catch(Exception ex) {
            Log.d("Error", "setUpMovies: " + ex.getMessage());

            return null;
        }
    }
}

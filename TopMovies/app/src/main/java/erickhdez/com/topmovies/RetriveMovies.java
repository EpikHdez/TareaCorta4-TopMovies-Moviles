package erickhdez.com.topmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erickhdez on 18/3/18.
 */

public class RetriveMovies extends AsyncTask<String, Void, ArrayList<Movie>> {
    private IReceiveAsyncTaskResponse taskRequester;

    public RetriveMovies(IReceiveAsyncTaskResponse taskRequester) {
        this.taskRequester = taskRequester;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {
        String pageURL = strings[0];
        int wantedElements = Integer.parseInt(strings[1]);

        try {
            Document html = Jsoup.connect(pageURL).get();
            Elements top100Movies = html.select("div.lister-item");
            List<Element> top20Movies = top100Movies.subList(0, wantedElements);
            ArrayList<Movie> parsedMovies = new ArrayList<>();

            for(Element movie : top20Movies) {
                Element image = movie.selectFirst("img");
                String imageUrl = image.attr("loadlate");

                Element header = movie.selectFirst("h3");
                String movieName = header.selectFirst("a[href]").html();
                String releaseYear = header.selectFirst("span.lister-item-year").html();
                String movieFullName = String.format("%s - %s", movieName, releaseYear);

                Element ratings = movie.selectFirst("div.ratings-bar");
                String imdbRating = ratings.selectFirst("strong").html();
                String metascore = ratings.selectFirst("span.metascore").html();

                URL url = new URL(imageUrl);
                URLConnection connection = url.openConnection();
                Bitmap movieImage = BitmapFactory.decodeStream(connection.getInputStream());

                Movie movieInfo = new Movie(movieName, movieImage, imdbRating, metascore);
                parsedMovies.add(movieInfo);
            }

            return parsedMovies;
        } catch(Exception ex) {
            Log.d("Error", "setUpMovies: " + ex.getMessage());

            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        taskRequester.receiveResponse(movies);
    }
}

package erickhdez.com.topmovies;

import android.app.Activity;
import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements IReceiveAsyncTaskResponse {
    private TextView txtLoading;
    private Timer loadingTimer;
    private IReceiveAsyncTaskResponse taskRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webViewContainer);
        webView.loadUrl("file:///android_asset/index.html");

        RelativeLayout overLayout = findViewById(R.id.OverLayout);
        overLayout.bringToFront();
        overLayout.setVisibility(View.VISIBLE);

        txtLoading = findViewById(R.id.txtLoading);
        loadingTimer = new Timer();
        taskRequester = this;

        requestsMovies();
    }

    private void requestsMovies() {
        final String url = "http://www.imdb.com/list/ls064079588/";
        final String wantedElements = "20";

        loadingTimer.scheduleAtFixedRate(new TimerTask() {
            int[] loadingTexts = {R.string.loading_1, R.string.loading_2, R.string.loading_3};
            int textsCount = loadingTexts.length;
            boolean retrieving = false;
            int index = 0, attemps = 0;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtLoading.setText(loadingTexts[index++]);

                        if(attemps == 60) {
                            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                index %= textsCount;
                attemps++;

                if(isOnline() && !retrieving) {
                    retrieving = true;
                    new RetriveMovies(taskRequester).execute(url, wantedElements);
                }
            }
        }, 0, 1000);


    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void receiveResponse(Object response) {
        ArrayList<Movie> movies = (ArrayList<Movie>) response;
        MoviesAdapter adapter = new MoviesAdapter(this, movies);

        GridView moviesGridView = findViewById(R.id.moviesGridView);
        moviesGridView.setAdapter(adapter);

        RelativeLayout overLayout = findViewById(R.id.OverLayout);
        overLayout.setVisibility(View.INVISIBLE);
        loadingTimer.cancel();
    }
}

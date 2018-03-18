package erickhdez.com.topmovies;

import android.graphics.Bitmap;

/**
 * Created by erickhdez on 18/3/18.
 */

public class Movie {
    private String mName;
    private Bitmap mPoster;
    private String mStarRating;
    private String mMetascore;

    public Movie(String mName, Bitmap mPoster, String mStarRating, String mMetascore) {
        this.mName = mName;
        this.mPoster = mPoster;
        this.mStarRating = mStarRating;
        this.mMetascore = mMetascore;
    }

    public String getName() {
        return mName;
    }

    public Bitmap getPoster() {
        return mPoster;
    }

    public String getStarRating() {
        return mStarRating;
    }

    public String getMetascore() {
        return mMetascore;
    }
}

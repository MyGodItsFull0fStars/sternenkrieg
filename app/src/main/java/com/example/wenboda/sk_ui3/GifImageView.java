package com.example.wenboda.sk_ui3;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.view.View;
import android.webkit.WebView;

import java.io.InputStream;

/**
 * Created by anja on 19.04.17.
 */

public class GifImageView extends WebView {
    public GifImageView(Context context, String path) {
        super(context);
        loadUrl(path);
    }


    /* private Movie mMovie;
    private long mMoviestart;


    public GifImageView(Context context, InputStream stream) {
        super(context);

        mMovie = Movie.decodeStream(stream);
        if (mMovie == null) {
            System.out.println("movie is null! :-(");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        final long now = SystemClock.uptimeMillis();

        if (mMoviestart == 0) {
            mMoviestart = now;
            System.out.println("asdffff" + now);
        }

        final int relTime = (int)((now - mMoviestart) % mMovie.duration());
        mMovie.setTime(relTime);
        mMovie.draw(canvas, 10, 10);
        this.invalidate();
    } */

}
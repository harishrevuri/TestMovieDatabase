package com.harish.test.project.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.harish.test.project.R;
import com.harish.test.project.entities.Movie;
import com.harish.test.project.fragments.MovieDetailFragment;
import com.harish.test.project.fragments.MovieListFragment;
import com.harish.test.project.fragments.MovieListPagerFragment;
import com.harish.test.project.fragments.MoviePreviewFragment;
import com.harish.test.project.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        MovieListFragment.OnFragmentInteractionListener,
        MovieDetailFragment.OnFragmentInteractionListener,
        MoviePreviewFragment.OnFragmentInteractionListener {

    private static final long SPLASH_PRESENTATION_DURATION = 1500L;
    private static boolean splashPresented = false;

    MoviePreviewFragment previewFragment;

// This is a test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                updateFragmentMenu();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        showHomeScreen();
    }

    private void showHomeScreen() {
        if (splashPresented) {
            showMovieList();
            getSupportActionBar().show();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    splashPresented = true;
                    showHomeScreen();
                }
            }, SPLASH_PRESENTATION_DURATION);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {
        openMovieDetail(movie);
    }

    @Override
    public void onMovieSelected(List<Movie> movies, int selectedIndex) {
        openMovieDetail(movies, selectedIndex);
    }

    @Override
    public void onMoviePreviewRequested(Movie movie) {
        openMovieDetailPreview(movie);
    }

    private void showMovieList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MovieListFragment.newInstance())
                .commit();
    }

    private void openMovieDetail(Movie movie) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MovieDetailFragment.newInstance(movie))
                .addToBackStack(MovieDetailFragment.class.getName())
                .commit();
    }

    private void openMovieDetail(List<Movie> articles, int selectedIndex) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MovieListPagerFragment.newInstance((ArrayList<Movie>) articles, selectedIndex))
                .addToBackStack(MovieListPagerFragment.class.getName())
                .commit();
    }

    private void openMovieDetailPreview(Movie movie) {
        safeClosePreviewBox();
        previewFragment = MoviePreviewFragment.newInstance(movie);
        previewFragment.show(
                getSupportFragmentManager(),
                MoviePreviewFragment.class.getName()
        );
    }

    private void safeClosePreviewBox() {
        if (previewFragment != null) {
            previewFragment.dismiss();
            previewFragment = null;
        }
    }

    private void updateFragmentMenu() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
//        if(fragment != null) {
//            fragment.invalidateOptionMenu();
//        }
    }

    @Override
    public void onDismissPreviewRequested() {
        safeClosePreviewBox();
    }

    @Override
    public void onArticleShareRequested(Movie movie) {
        //ToDo - fix this
        Utils.shareText(this, movie.getBackdropPath());
    }

    @Override
    public void onArticleOpenRequested(Movie movie) {
        openMovieDetail(movie);
    }
}

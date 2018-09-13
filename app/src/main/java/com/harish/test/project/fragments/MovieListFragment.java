package com.harish.test.project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.harish.test.project.R;
import com.harish.test.project.adapters.MovieListAdapter;
import com.harish.test.project.entities.Movie;
import com.harish.test.project.entities.MovieSearchResult;
import com.harish.test.project.utils.APIHelper;
import com.harish.test.project.utils.EndlessOnScrollListener;
import com.harish.test.project.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListFragment extends Fragment {

    @BindView(R.id.empty)
    View empty;
    @BindView(R.id.progress)
    SwipeRefreshLayout progress;
    @BindView(R.id.list_movies)
    RecyclerView listMovies;

    private MovieListAdapter adapter;

    private int currentPage = 1;
    private String searchKeyword = "";

    private OnFragmentInteractionListener mListener;

    private int maxResultPage;

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MovieListAdapter(new ArrayList<Movie>(), new MovieListAdapter.MovieItemHandler() {
            @Override
            public void onMovieSelected(Movie movie) {
                //mListener.onMovieSelected(article);
                List<Movie> articles = new ArrayList<>(adapter.getItems());
                int index = articles.indexOf(movie);
                mListener.onMovieSelected(articles, index);
            }

            @Override
            public void onMoviePreviewRequested(Movie movie) {
                mListener.onMoviePreviewRequested(movie);
            }

            @Override
            public void onDismissPreviewRequested() {
                mListener.onDismissPreviewRequested();
            }
        });
        listMovies.setAdapter(adapter);

        listMovies.addOnScrollListener(new EndlessOnScrollListener() {
            @Override
            public void onScrolledToEnd() {
                if (currentPage < maxResultPage) {
                    loadMovies(currentPage + 1);
                }
            }
        });

        progress.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadMovies(currentPage);
            }
        });
        loadMovies(currentPage);
    }

    private void loadMovies(int page) {
        showProgress();
        APIHelper.searchMoviesInBackground(getContext(), searchKeyword, page, new APIHelper.TaskListener() {
            @Override
            public void onTaskDone(Object object) {
                hideProgress();
                MovieSearchResult movieSearchResult = (MovieSearchResult) object;
                maxResultPage = movieSearchResult.getTotalPages();
                currentPage = movieSearchResult.getPage();
                adapter.addItems(movieSearchResult.getMovies());
            }

            @Override
            public void onTaskFailed(String problem) {
                hideProgress();
            }

            @Override
            public void onConnectionError() {
                hideProgress();
            }

            @Override
            public void onTaskFailed() {
                hideProgress();
            }
        });
    }

    private void showProgress() {
        progress.setRefreshing(true);
    }

    private void hideProgress() {
        progress.setRefreshing(false);
    }

    private void handleSearchText(String text) {
        if (Utils.isValid(text)) {
            searchKeyword = text;
            adapter.clearItems();
            currentPage = 1;
            loadMovies(currentPage);
        } else {
            searchKeyword = "";
            adapter.clearItems();
            currentPage = 1;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearchText(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        System.out.println("");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onMovieSelected(Movie article);

        void onMoviePreviewRequested(Movie article);

        void onDismissPreviewRequested();

        void onMovieSelected(List<Movie> movies, int selectedIndex);
    }
}

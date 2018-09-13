package com.harish.test.project.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harish.test.project.R;
import com.harish.test.project.entities.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListPagerFragment extends Fragment {

    public static final String ARG_MOVIES = "ARG_MOVIES";
    public static final String ARG_INDEX = "ARG_INDEX";

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.nav_left)
    View navLeft;
    @BindView(R.id.nav_right)
    View navRight;

    private ArrayList<Movie> movies;
    private int selectedIndex;

    public MovieListPagerFragment() {
        // Required empty public constructor
    }

    public static MovieListPagerFragment newInstance(ArrayList<Movie> movies, int selectedIndex) {
        MovieListPagerFragment fragment = new MovieListPagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIES, movies);
        args.putInt(ARG_INDEX, selectedIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        if (getArguments() != null) {
            movies = (ArrayList<Movie>) getArguments().getSerializable(ARG_MOVIES);
            selectedIndex = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list_pager, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager.setAdapter(new MovieListPagerAdapter(getChildFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshNavButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            }
        });

        navRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });
        pager.setCurrentItem(selectedIndex);
        refreshNavButtons(selectedIndex);
    }

    private void refreshNavButtons(int position) {
        if (position == 0) {
            navLeft.setVisibility(View.GONE);
        } else {
            navLeft.setVisibility(View.VISIBLE);
        }

        if (position >= pager.getAdapter().getCount() - 1) {
            navRight.setVisibility(View.GONE);
        } else {
            navRight.setVisibility(View.VISIBLE);
        }
    }

    private class MovieListPagerAdapter extends FragmentPagerAdapter {

        public MovieListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MovieDetailFragment.newInstance(movies.get(position));
        }

        @Override
        public int getCount() {
            return movies.size();
        }
    }
}

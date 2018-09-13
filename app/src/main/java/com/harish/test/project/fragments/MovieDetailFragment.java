package com.harish.test.project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harish.test.project.R;
import com.harish.test.project.entities.Movie;
import com.harish.test.project.utils.APIHelper;
import com.harish.test.project.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_MOVIE = "arg_movie";

    private Movie movie;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_overview)
    TextView txtOverview;
    @BindView(R.id.btn_share)
    View btnShare;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(Movie article) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_movie_detail, container, false
        );
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtTitle.setText(movie.getTitle());
        txtDate.setText(Utils.formatDateString(movie.getReleaseDate()));
        txtOverview.setText(movie.getOverview());
        Picasso.get().
                load(APIHelper.getImageUrl(movie.getPosterPath()))
                .into(imgBanner);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onArticleShareRequested(movie);
            }
        });
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

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onArticleShareRequested(Movie article);
    }
}

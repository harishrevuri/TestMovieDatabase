package com.harish.test.project.adapters;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harish.test.project.R;
import com.harish.test.project.entities.Movie;
import com.harish.test.project.utils.APIHelper;
import com.harish.test.project.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final List<Movie> mValues;
    private MovieItemHandler mHandler;

    public MovieListAdapter(List<Movie> items, MovieItemHandler handler) {
        mValues = items;
        mHandler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItems(List<Movie> items) {
        mValues.addAll(items);
        this.notifyDataSetChanged();
    }

    public void clearItems() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public void replaceItems(List<Movie> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    public List<Movie> getItems() {
        return mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_overview)
        TextView txtOverview;
        @BindView(R.id.txt_rating_count)
        TextView txtRatingCount;
        @BindView(R.id.img_thumb)
        ImageView imgThumb;

        @BindView(R.id.img_video)
        ImageView imgVideo;
        @BindView(R.id.img_adult)
        ImageView imgAdult;
        @BindView(R.id.rating)
        AppCompatRatingBar rating;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            final Movie movie = mValues.get(position);
            txtTitle.setText(movie.getTitle());
            txtOverview.setText(movie.getOverview());
            txtRatingCount.setText(movie.getVoteCount() + "");
            rating.setRating(movie.getVoteAverage());

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHandler.onMovieSelected(movie);
                }
            });
            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mHandler.onMoviePreviewRequested(movie);
                    return true;
                }
            });
            mView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
                        mHandler.onDismissPreviewRequested();
                    }
                    return false;
                }
            });
            String imageUrl = APIHelper.getImageUrl(movie.getPosterPath());
            //String imageFallbackUrl = APIHelper.getImageUrl(movie.getBackdropPath());
            if (Utils.isValid(imageUrl)) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.default_thumbnail)
                        .error(R.drawable.default_thumbnail)
                        .into(imgThumb);
            }

            imgVideo.setVisibility(movie.isVideo() ? View.VISIBLE : View.GONE);
            imgAdult.setVisibility(movie.isAdult() ? View.VISIBLE : View.GONE);
        }
    }

    public interface MovieItemHandler {
        void onMovieSelected(Movie movie);

        void onMoviePreviewRequested(Movie movie);

        void onDismissPreviewRequested();
    }
}

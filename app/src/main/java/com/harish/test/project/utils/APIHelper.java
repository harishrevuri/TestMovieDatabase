package com.harish.test.project.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.harish.test.project.BuildConfig;
import com.harish.test.project.entities.Movie;
import com.harish.test.project.entities.MovieSearchResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.security.KeyStore;

import cz.msebera.android.httpclient.Header;

public class APIHelper {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String BASE_URL_IMAGES = "https://image.tmdb.org/t/p/w185_and_h278_bestv2";
    //private static final String API_KEY = "5b9825410e0a262854005f3b";
    private static final String API_KEY = "0e1cd066a75e787893f420cb43e4aba4";

    private static final String URL_MOVIE_SEARCH = BASE_URL + "/search/movie";
    private static final String URL_MOVIE_DETAIL = BASE_URL + "/movie/";
    private static final String URL_MOVIE_DISCOVER = BASE_URL + "/discover/movie";

    public static String SERVER_DATE_FORMAT = "yyyy-MM-dd"; //1999-10-12
    public static GsonBuilder gsonBuilder = new GsonBuilder();
    public static Gson gson;

    public static void init() {
        gsonBuilder.setDateFormat(SERVER_DATE_FORMAT);
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gson = gsonBuilder.create();
    }

    public static void searchMoviesInBackground(Context context, String keyword, int page, final TaskListener listener) {
        if (!isNetworkAvailable(context)) {
            listener.onConnectionError();
            return;
        }
        try {
            String requestUrl = "";
            RequestParams params = new RequestParams();
            params.add("api_key", API_KEY);
            params.add("language", "en-US");
            if (Utils.isValid(keyword)) {
                requestUrl = URL_MOVIE_SEARCH;
                params.add("query", keyword);
            } else {
                params.add("sort_by", "popularity.desc");
                requestUrl = URL_MOVIE_DISCOVER;
            }
            params.add("page", page + "");
            params.add("include_adult", true + "");
            getHTTPClient().cancelAllRequests(true);
            getHTTPClient().get(requestUrl, params, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    try {
                        MovieSearchResult result = gson.fromJson(response, MovieSearchResult.class);
                        listener.onTaskDone(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onTaskFailed();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                    handleCommonFailure(response, throwable, listener);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            listener.onTaskFailed(e.getMessage());
        }
    }


    public static void getMovieDataInBackground(Context context, int movieData, final TaskListener listener) {
        if (!isNetworkAvailable(context)) {
            listener.onConnectionError();
            return;
        }
        try {
            RequestParams params = new RequestParams();
            params.add("api_key", API_KEY);
            params.add("language", "en-US");
            getHTTPClient().get(URL_MOVIE_DETAIL + movieData, params, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    try {
                        Movie result = gson.fromJson(response, Movie.class);
                        listener.onTaskDone(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onTaskFailed();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                    handleCommonFailure(response, throwable, listener);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            listener.onTaskFailed(e.getMessage());
        }
    }

    private static void handleCommonFailure(String response, Throwable throwable, TaskListener listener) {
        if (listener == null)
            return;
        try {
            JSONObject responseJson = new JSONObject(response);
            String message = responseJson.getString("status_message");
            listener.onTaskFailed(message);
        } catch (Exception e) {
            listener.onTaskFailed();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            if (context == null)
                return true;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return true;
        }
    }

    public static String getImageUrl(String path) {
        if (Utils.isValid(path)) {
            return BASE_URL_IMAGES + path + "?api_key=" + API_KEY;
        }
        return "";
    }

    ////////////////////////////////////////////

    private static AsyncHttpClient client;

    private static AsyncHttpClient getHTTPClient() {
        if (client == null) {
            client = new AsyncHttpClient();
            client.setLoggingEnabled(BuildConfig.DEBUG);
            //client.setMaxConnections(10);
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                client.setSSLSocketFactory(sf);
            } catch (Exception e) {
                e.printStackTrace();
            }
            client.setTimeout(5000);
        }
        return client;
    }

    ////////////////////////////////////////////

    public interface TaskListener {
        void onTaskDone(Object object);

        void onTaskFailed();

        void onTaskFailed(String problem);

        void onConnectionError();
    }
}

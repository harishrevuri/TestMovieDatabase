package com.harish.test.project.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Movie implements Serializable {
    //Short Params

    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("adult")
    boolean adult;
    @SerializedName("overview")
    String overview;
    @SerializedName("release_date")
    Date releaseDate;
    @SerializedName("genre_ids")
    int[] genreIds;
    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("original_language")
    String originalLanguage;
    @SerializedName("backdrop_path")
    String backdropPath;
    @SerializedName("popularity")
    double popularity;
    @SerializedName("vote_count")
    int voteCount;
    @SerializedName("video")
    boolean video;
    @SerializedName("vote_average")
    float voteAverage;

    //Full Params
    @SerializedName("belongs_to_collection")
    Object belongsToCollection;
    @SerializedName("budget")
    int budget;
    @SerializedName("genres")
    Genre[] genres;
    @SerializedName("homepage")
    String homepage;
    @SerializedName("imdb_id")
    int imdbId;
    @SerializedName("production_companies")
    ProductionCompany[] productionCompanies;
    @SerializedName("production_countries")
    ProductionCountry[] productionCountries;
    @SerializedName("revenue")
    int revenue;
    @SerializedName("runtime")
    int runtime;
    @SerializedName("spoken_languages")
    SpokenLanguage[] spokenLanguages;
    @SerializedName("status")
    String status;
    @SerializedName("tagline")
    String tagline;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(Object belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public void setGenres(Genre[] genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getImdbId() {
        return imdbId;
    }

    public void setImdbId(int imdbId) {
        this.imdbId = imdbId;
    }

    public ProductionCompany[] getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(ProductionCompany[] productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public ProductionCountry[] getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(ProductionCountry[] productionCountries) {
        this.productionCountries = productionCountries;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public SpokenLanguage[] getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(SpokenLanguage[] spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

//    public boolean isFullDataAvailable() {
//        return fullDataAvailable;
//    }
//
//    public void setFullDataAvailable(boolean fullDataAvailable) {
//        this.fullDataAvailable = fullDataAvailable;
//    }


    @Override
    public boolean equals(Object other) {
        try {
            return ((Movie) other).id == this.id;
        } catch (Exception e) {
            return super.equals(other);
        }
    }

    @Override
    public String toString() {
        return this.title;
    }
}

package com.example.android.movieapp.main;

import android.os.Parcel;
import android.os.Parcelable;

//parcelable is faster than serializable
public class MovieItemModel implements Parcelable {

    private String posterUrl;
    private String movieOriginalTitle;
    private String movieReleaseDate;
    private String movieVoteAverage;
    private String movieOverview;
    private String movieId;

    //constructor
    public MovieItemModel() {
    }


    public MovieItemModel(String posterUrl, String movieOriginalTitle,
                          String movieReleaseDate, String movieVoteAverage,
                          String movieOverview, String movieId) {
        this.posterUrl = posterUrl;
        this.movieOriginalTitle = movieOriginalTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieVoteAverage = movieVoteAverage;
        this.movieOverview = movieOverview;
        this.movieId = movieId;
    }

    protected MovieItemModel(Parcel in) {
        posterUrl = in.readString();
        movieOriginalTitle = in.readString();
        movieReleaseDate = in.readString();
        movieVoteAverage = in.readString();
        movieOverview = in.readString();
        movieId = in.readString();
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public void setMovieOriginalTitle(String movieOriginalTitle) {
        this.movieOriginalTitle = movieOriginalTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieRating() {
        return movieVoteAverage;
    }

    public void setMovieRating(String movieVoteRating) {
        this.movieVoteAverage = movieVoteRating;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterUrl);
        dest.writeString(movieOriginalTitle);
        dest.writeString(movieReleaseDate);
        dest.writeString(movieVoteAverage);
        dest.writeString(movieOverview);
        dest.writeString(movieId);
    }

    public static final Creator<MovieItemModel> CREATOR = new Creator<MovieItemModel>() {
        @Override
        public MovieItemModel createFromParcel(Parcel in) {
            return new MovieItemModel(in);
        }

        @Override
        public MovieItemModel[] newArray(int size) {
            return new MovieItemModel[size];
        }
    };

}

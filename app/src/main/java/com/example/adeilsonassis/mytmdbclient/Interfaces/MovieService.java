package com.example.adeilsonassis.mytmdbclient.Interfaces;

import com.example.adeilsonassis.mytmdbclient.Model.Movie;
import com.example.adeilsonassis.mytmdbclient.Model.MovieListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adeilson.assis on 20/10/2017.
 */

public interface MovieService
{
    @GET("discover/movie")
    Call<MovieListResponse> listaMovies(@Query("api_key") String api_key, @Query("vote_average.gte") String voteAverage);

    @GET("http://api.themoviedb.org/3/search/movie")
    Call<MovieListResponse> listaMoviesBusca(@Query("api_key") String api_key, @Query("query") String criterioBusca);
}

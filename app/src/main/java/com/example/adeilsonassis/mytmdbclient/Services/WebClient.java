package com.example.adeilsonassis.mytmdbclient.Services;

import android.content.Context;

import com.example.adeilsonassis.mytmdbclient.Events.MovieEvent;
import com.example.adeilsonassis.mytmdbclient.Interfaces.MovieService;
import com.example.adeilsonassis.mytmdbclient.Model.Movie;
import com.example.adeilsonassis.mytmdbclient.Model.MovieListResponse;
import com.example.adeilsonassis.mytmdbclient.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adeilson.assis on 20/10/2017.
 */

public class WebClient
{
    private static final String SERVER_URL = "http://api.themoviedb.org/3/";

    public void getMovies(Context context)
    {
        final Context contexto;

        contexto = context;

        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = client.create(MovieService.class);

        Call<MovieListResponse> call = movieService.listaMovies(contexto.getString(R.string.api_key),contexto.getString(R.string.vote_average));

        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response)
            {
                EventBus.getDefault().post(new MovieEvent(response, contexto, true));
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t)
            {
                EventBus.getDefault().post(t);
            }
        });


    }


}

package com.example.adeilsonassis.mytmdbclient.Events;

import android.content.Context;

import com.example.adeilsonassis.mytmdbclient.Model.Movie;
import com.example.adeilsonassis.mytmdbclient.Model.MovieListResponse;
import com.example.adeilsonassis.mytmdbclient.Utils.MyJSONHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Created by adeilson.assis on 20/10/2017.
 */

public class MovieEvent
{
    private final MovieListResponse movieListResponse;
    private final Context context;
    private final Boolean saveJson;


    public MovieEvent(Response<MovieListResponse> response, Context context, Boolean saveJson)
    {
        this.movieListResponse = response.body();
        this.context = context;
        this.saveJson = saveJson;
    }

    public List<Movie> getMovies()
    {
        if(saveJson)
        {
            saveJson();
        }
        return movieListResponse.getResults();
    }

    public void saveJson()
    {
        MyJSONHelper jsonHelper = new MyJSONHelper();
        Gson gson = null;

        gson = new GsonBuilder().create();

        try {
            jsonHelper.objectToFile(gson.toJson(movieListResponse),context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

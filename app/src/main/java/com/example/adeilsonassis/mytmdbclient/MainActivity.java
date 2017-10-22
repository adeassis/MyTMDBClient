package com.example.adeilsonassis.mytmdbclient;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.adeilsonassis.mytmdbclient.Events.MovieEvent;
import com.example.adeilsonassis.mytmdbclient.Fragments.DetalhesMovieFragment;
import com.example.adeilsonassis.mytmdbclient.Fragments.ListaMoviesFragment;
import com.example.adeilsonassis.mytmdbclient.Interfaces.MovieDelegate;
import com.example.adeilsonassis.mytmdbclient.Model.Movie;
import com.example.adeilsonassis.mytmdbclient.Services.WebClient;
import com.example.adeilsonassis.mytmdbclient.Utils.MyJSONHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieDelegate
{
    ListaMoviesFragment listaMoviesFragment;
    List<Movie> filmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            loadData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void loadData() throws JSONException
    {
        String jsonMovieLocal = null;
        try {
            jsonMovieLocal = (String) MyJSONHelper.objectFromFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(jsonMovieLocal == null)
        {
            Bundle args = new Bundle();
            args.putString("jsonMovieLocal", null);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            listaMoviesFragment = new ListaMoviesFragment();
            listaMoviesFragment.setArguments(args);
            transaction.replace(R.id.frame_principal, listaMoviesFragment);
            transaction.commit();
            new WebClient().getMovies(this);

        }
        else
        {
            Bundle args = new Bundle();
            args.putString("jsonMovieLocal", jsonMovieLocal);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            listaMoviesFragment = new ListaMoviesFragment();
            listaMoviesFragment.setArguments(args);
            transaction.replace(R.id.frame_principal, listaMoviesFragment);
            transaction.commit();
        }
    }



    @Override
    public void lidaComItemSelecionado(Movie movie)
    {
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DetalhesMovieFragment detalhesMovieFragment = new DetalhesMovieFragment();
        detalhesMovieFragment.setArguments(args);
        transaction.replace(R.id.frame_principal, detalhesMovieFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Subscribe
    public void lidaComSucesso(MovieEvent event)
    {
        listaMoviesFragment.populaListaCom(event.getMovies());
    }

    @Subscribe
    public void lidaComErro(Throwable t)
    {
        Toast.makeText(this, "Não foi possível carregar os filmes... "+t.toString() ,Toast.LENGTH_LONG).show();
        Log.e("main", t.getMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}

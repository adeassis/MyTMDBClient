package com.example.adeilsonassis.mytmdbclient.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adeilsonassis.mytmdbclient.Adapter.MovieAdapter;
import com.example.adeilsonassis.mytmdbclient.Interfaces.MovieService;
import com.example.adeilsonassis.mytmdbclient.MainActivity;
import com.example.adeilsonassis.mytmdbclient.Model.Movie;
import com.example.adeilsonassis.mytmdbclient.Model.MovieListResponse;
import com.example.adeilsonassis.mytmdbclient.R;
import com.example.adeilsonassis.mytmdbclient.Services.WebClient;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaMoviesFragment extends Fragment
{
    @BindView(R.id.lista_movies)
    RecyclerView recyclerView;

    @BindView(R.id.edtCriterioBusca)
    TextView criterioBusca;

    @BindView(R.id.imgBuscarFilme)
    ImageView imgBuscarFilme;


    private List<Movie> movies = new ArrayList<>();
    private List<Movie> filmes = new ArrayList<>();
    private List<Movie> filmesBusca = new ArrayList<>();

    String jsonMovieLocal = null;
    private static final String SERVER_URL = "http://api.themoviedb.org/3/";

    public ListaMoviesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_lista_movies, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(container.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MovieAdapter(getActivity(),movies));

        Bundle args = getArguments();
        jsonMovieLocal = (String) args.getSerializable("jsonMovieLocal");

        if(jsonMovieLocal != null)
        {
            loadData();
        }

        criterioBusca.requestFocus();
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }

    @OnClick(R.id.imgBuscarFilme)
    public void buscarFilmes()
    {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getMoviesBusca(getContext(),criterioBusca.getText().toString());

    }

    public void populaListaCom(List<Movie> movies)
    {
        this.movies.clear();
        this.movies.addAll(movies);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void loadData()
    {
        filmes.clear();

        try
        {
            JSONObject jsonObject = new JSONObject(jsonMovieLocal);
            JSONArray array = null;

            array = jsonObject.getJSONArray("results");


            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Movie filme = new Movie();

                filme.setAdult(obj.getBoolean("adult"));
                filme.setPosterPath(obj.getString("poster_path"));
                filme.setOverview(obj.getString("overview"));
                filme.setReleaseDate(obj.getString("release_date"));
                filme.setId(obj.getInt("id"));
                filme.setOriginalTitle(obj.getString("original_title"));
                filme.setOriginalLanguage(obj.getString("original_language"));
                filme.setTitle(obj.getString("title"));
                filme.setBackdropPath(obj.getString("backdrop_path"));
                filme.setPopularity(obj.getDouble("popularity"));
                filme.setVoteCount(obj.getInt("vote_count"));
                filme.setVoteAverage(obj.getDouble("vote_average"));
                filme.setVideo(obj.getBoolean("video"));

                filmes.add(i, filme);
            }

            populaListaCom(filmes);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getMoviesBusca(Context context, String criterioBusca)
    {
        final Context contexto;
        final MovieListResponse[] responseMovieList = new MovieListResponse[1];

        contexto = context;

        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = client.create(MovieService.class);

        Call<MovieListResponse> call = movieService.listaMoviesBusca(contexto.getString(R.string.api_key),criterioBusca);

        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response)
            {
                responseMovieList[0] = response.body();
                populaListaCom(responseMovieList[0].getResults());
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t)
            {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).cancel();
            }
        });


    }


}

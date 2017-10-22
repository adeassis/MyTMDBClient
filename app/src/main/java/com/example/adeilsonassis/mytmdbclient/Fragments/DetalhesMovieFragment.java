package com.example.adeilsonassis.mytmdbclient.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adeilsonassis.mytmdbclient.Model.Movie;
import com.example.adeilsonassis.mytmdbclient.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetalhesMovieFragment extends Fragment
{


    @BindView(R.id.detalhe_movie_title)
    TextView detalheMovieTitle;

    @BindView(R.id.detalhe_movie_overview)
    TextView detalheMovieOverview;

    @BindView(R.id.detalhe_movie_count_average)
    TextView detalheMovieCountAverage;

    @BindView(R.id.detalhe_movie_count_vote)
    TextView detalheMovieCountVote;

    @BindView(R.id.detalhe_movie_releasedate)
    TextView detalheMovieReleaseDate;

    @BindView(R.id.detalhe_movie_image)
    ImageView detalheMovieImage;

    private Movie movie;
    private static final String SERVER_IMG_URL = "https://image.tmdb.org/t/p/w500";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_movie, container, false);

        ButterKnife.bind(this,view);

        Bundle args = getArguments();
        movie = (Movie) args.getSerializable("movie");

        populaCamposCom(movie);

        return view;
    }

    public void populaCamposCom(Movie movie)
    {
        Picasso.with(getActivity()).load(SERVER_IMG_URL + movie.getPosterPath()).error(R.mipmap.ic_photo_error).into(detalheMovieImage);
        detalheMovieTitle.setText(movie.getTitle());
        detalheMovieOverview.setText(movie.getOverview());
        detalheMovieCountAverage.setText("Média dos votos: "+String.valueOf(movie.getVoteAverage()));
        detalheMovieCountVote.setText("Total de votos: "+String.valueOf(movie.getVoteCount()));
        detalheMovieReleaseDate.setText("Ano de lancamento: "+movie.getReleaseDate().substring(0, 4));
    }


}

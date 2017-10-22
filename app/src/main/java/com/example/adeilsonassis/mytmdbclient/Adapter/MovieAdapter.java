package com.example.adeilsonassis.mytmdbclient.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.adeilsonassis.mytmdbclient.Interfaces.MovieDelegate;
import com.example.adeilsonassis.mytmdbclient.Model.Movie;
import com.example.adeilsonassis.mytmdbclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.adeilsonassis.mytmdbclient.R.mipmap.ic_photo_error;

/**
 * Created by adeilson.assis on 19/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter
{

    Context context;
    List<Movie> movies;
    private static final String SERVER_IMG_URL = "https://image.tmdb.org/t/p/w150";

    public MovieAdapter(Context context, List<Movie> movies)
    {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ViewHolder viewHolder = (ViewHolder) holder;
        Movie movie = movies.get(position);
        viewHolder.releaseDate.setText(movie.getReleaseDate().substring(0, 4));
        viewHolder.tituloMovie.setText(movie.getTitle());
        viewHolder.voteAverage.setText("Média dos votos: "+String.valueOf(movie.getVoteAverage()));
        viewHolder.voteCount.setText("Total de votos: "+String.valueOf(movie.getVoteCount()));
        Picasso.with(context).load(SERVER_IMG_URL + movie.getPosterPath()).error(R.mipmap.ic_photo_error).into(viewHolder.posterMovie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);

        }
        @BindView(R.id.item_movie_title)
        TextView tituloMovie;

        @BindView(R.id.item_movie_voteaverage)
        TextView voteAverage;

        @BindView(R.id.item_movie_votecount)
        TextView voteCount;

        @BindView(R.id.item_movie_releasedate)
        TextView releaseDate;

        @BindView(R.id.item_movie_poster)
        ImageView posterMovie;

        @OnClick(R.id.card_item_movie)
        public void clickItem()
        {
            Movie filme = movies.get(getAdapterPosition());
            MovieDelegate delegate = (MovieDelegate) itemView.getContext();
            delegate.lidaComItemSelecionado(filme);
        }

    }

}

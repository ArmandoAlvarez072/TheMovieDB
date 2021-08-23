package com.example.themoviedb.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.entities.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.themoviedb.app.Constants
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ItemMovieBinding

class MovieAdapter(private var movieList : List<Movie>,
                   private val listener : OnMovieListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    private lateinit var context : Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemMovieBinding.bind(view)

        fun setListener(movie: Movie){
            binding.root.setOnClickListener{
                listener.onClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.setListener(movie)
        holder.binding.tvTitle.text = movie.title
        holder.binding.rbPopularity.rating = movie.vote_average / 2
        Glide.with(context)
            .load(Constants.IMAGE_BASE_URL + movie.poster_path)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .centerCrop()
            .into(holder.binding.imgMovie)
    }

    fun setData(movies : List<Movie>?){
        movieList = movies!!
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = movieList.size
}
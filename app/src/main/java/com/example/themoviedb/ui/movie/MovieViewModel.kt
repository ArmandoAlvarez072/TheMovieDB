package com.example.themoviedb.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.themoviedb.entities.Movie
import com.example.themoviedb.repository.TheMovieDBRepository


class MovieViewModel: ViewModel() {
    private var theMovieDBRepository: TheMovieDBRepository
    private var popularMovies: LiveData<List<Movie>>
    private var nowPlaying: LiveData<List<Movie>>

    init {
        theMovieDBRepository = TheMovieDBRepository()
        popularMovies = theMovieDBRepository.popularMovies()!!
        nowPlaying = theMovieDBRepository.nowPlaying()!!
    }

    fun getPopularMovies(): LiveData<List<Movie>> {
        return popularMovies
    }

    fun getNowPlaying() : LiveData<List<Movie>>{
        return nowPlaying
    }
}
package com.example.themoviedb.ui.movie

import com.example.themoviedb.entities.Movie

interface OnMovieListener {
    fun onClick(movie : Movie)
}
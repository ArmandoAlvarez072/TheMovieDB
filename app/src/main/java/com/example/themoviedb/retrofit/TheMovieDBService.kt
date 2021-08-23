package com.example.themoviedb.retrofit

import com.example.themoviedb.entities.NowPlayingResponse
import com.example.themoviedb.entities.PopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET

interface TheMovieDBService {

    @GET("movie/popular")
    fun getPopularMovies(): Call<PopularMoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlaying() : Call<NowPlayingResponse>
}
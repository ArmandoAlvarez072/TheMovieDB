package com.example.themoviedb.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.themoviedb.app.MyApp
import com.example.themoviedb.entities.Movie
import com.example.themoviedb.entities.NowPlayingResponse
import com.example.themoviedb.entities.PopularMoviesResponse
import com.example.themoviedb.retrofit.TheMovieDBClient
import com.example.themoviedb.retrofit.TheMovieDBService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheMovieDBRepository {
    private var theMovieDBService: TheMovieDBService? = null
    private var theMovieDBClient: TheMovieDBClient? = null

    var popularMovies: MutableLiveData<List<Movie>>? = null
    var nowPlaying: MutableLiveData<List<Movie>>? = null

    init {
        theMovieDBClient = TheMovieDBClient.instance
        theMovieDBService = theMovieDBClient?.getTheMovieDBService()
        popularMovies = popularMovies()
    }

    fun popularMovies(): MutableLiveData<List<Movie>>? {
        if(popularMovies == null) {
            popularMovies = MutableLiveData<List<Movie>>()
        }

        val call: Call<PopularMoviesResponse>? = theMovieDBService?.getPopularMovies()
        call?.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en la llamada", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PopularMoviesResponse>, response: Response<PopularMoviesResponse>) {
                if(response.isSuccessful) {
                    popularMovies?.value = response.body()?.results
                }
            }
        })

        return popularMovies
    }

    fun nowPlaying() : MutableLiveData<List<Movie>>? {
        if (nowPlaying == null){
            nowPlaying = MutableLiveData<List<Movie>>()
        }

        val call: Call<NowPlayingResponse>? = theMovieDBService?.getNowPlaying()
        call?.enqueue(object : Callback<NowPlayingResponse>{
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                if (response.isSuccessful){
                    nowPlaying?.value = response.body()?.results
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error en la llamada", Toast.LENGTH_LONG).show()
            }
        })

        return nowPlaying
    }

}
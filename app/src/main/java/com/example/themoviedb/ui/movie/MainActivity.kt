package com.example.themoviedb.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ActivityMainBinding
import com.example.themoviedb.entities.Movie
import com.example.themoviedb.ui.detail.DetailFragment

class MainActivity : AppCompatActivity()  ,OnMovieListener ,MainAux {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private var movieList : List<Movie> = ArrayList()
    private var movieSelected : Movie? = null


    private val arrayValues : Array<String> by lazy {
        this.resources.getStringArray(R.array.status_value)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecyclerView()
        setArray()
        getViewModel()
        getPopular()
        itemChanged()


    }

    private fun setArray(){
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayValues)
        binding.actvType.setAdapter(statusAdapter)
    }

    private fun getViewModel() {
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private fun getPopular() {
        movieViewModel.getPopularMovies().observe(this, Observer {
            movieList = it
            adapter.setData(movieList)
        })
    }

    private fun typeChanged(){

        if (binding.actvType.text.toString()
                .equals(this.getString(R.string.popular_movies))){
            getPopular()
        }else{
            getNowPlaying()
        }
    }

    private fun itemChanged(){
        binding.actvType.setOnItemClickListener { adapterView, view, i, l ->
            typeChanged()
        }
    }

    private fun configRecyclerView() {
        adapter = MovieAdapter(arrayListOf(), this)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                context, 2,
                GridLayoutManager.VERTICAL, false
            )
            adapter = this@MainActivity.adapter
        }

    }

    private fun getNowPlaying(){
        movieViewModel.getNowPlaying().observe(this, Observer {
            movieList = it
            adapter.setData(movieList)
        })
    }

    override fun onClick(movie: Movie) {
        movieSelected = movie
        val fragment = DetailFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.containerMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun getMovieSelected(): Movie? = movieSelected

}
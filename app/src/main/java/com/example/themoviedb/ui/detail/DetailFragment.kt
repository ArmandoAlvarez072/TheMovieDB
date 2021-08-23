package com.example.themoviedb.ui.detail

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.themoviedb.app.Constants
import com.example.themoviedb.databinding.FragmentDetailBinding
import com.example.themoviedb.entities.Movie
import com.example.themoviedb.ui.movie.MainAux

class DetailFragment : Fragment() {
    private var binding : FragmentDetailBinding? = null
    private var movie : Movie? = null
    private var url : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding?.let{
            return it.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()
        getMovie()
        configButton()

    }

    private fun setUpActionBar() {
        (activity as? AppCompatActivity)?.let{
            it.supportActionBar?.hide()
            setHasOptionsMenu(true)
        }
    }

    private fun getMovie() {
        movie = (activity as? MainAux)?.getMovieSelected()
        movie?.let{ movie ->
            url = Constants.IMAGE_BASE_URL + movie.poster_path
            binding?.let{
                it.tvTitle.text = movie.title
                it.tvDescription.text = movie.overview
                Glide.with(this)
                    .load(Constants.IMAGE_BASE_URL + movie.poster_path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(it.imgMovie)

            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


    fun configButton(){
        binding?.let{
            it.efab.setOnClickListener {
              val intent : Intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                  putExtra(SearchManager.QUERY, url)
              }
                startActivity(intent)
            }
        }
    }

}
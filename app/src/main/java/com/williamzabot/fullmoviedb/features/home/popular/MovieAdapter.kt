package com.williamzabot.fullmoviedb.features.home.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.williamzabot.fullmoviedb.R
import com.williamzabot.fullmoviedb.data.remote.model.Movie

class MovieAdapter : PagedListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        return getItem(position).let { holder.bind(it!!) }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fieldTitle = itemView.findViewById<TextView>(R.id.movie_title)
        private val fieldData = itemView.findViewById<TextView>(R.id.movie_data)
        private val imgView = itemView.findViewById<ImageView>(R.id.movie_img)

        fun bind(movie: Movie) {
            fieldTitle.text = movie.title
            fieldData.text = movie.release_date

            val url = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
            Glide.with(imgView)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView)
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.original_title == newItem.original_title
                    && oldItem.poster_path == newItem.poster_path &&
                    oldItem.release_date == newItem.release_date
        }

    }
}
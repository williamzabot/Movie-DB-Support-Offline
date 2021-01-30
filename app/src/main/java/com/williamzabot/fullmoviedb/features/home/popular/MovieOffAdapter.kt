package com.williamzabot.fullmoviedb.features.home.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.williamzabot.fullmoviedb.R
import com.williamzabot.fullmoviedb.data.db.local.entity.PopularMovie

class MovieOffAdapter : RecyclerView.Adapter<MovieOffAdapter.MovieOffViewHolder>() {

    var movies = listOf<PopularMovie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MovieOffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fieldTitle = itemView.findViewById<TextView>(R.id.movie_title)
        private val fieldData = itemView.findViewById<TextView>(R.id.movie_data)
        private val imgView = itemView.findViewById<ImageView>(R.id.movie_img)

        fun bind(movie: PopularMovie) {
            fieldTitle.text = movie.title
            fieldData.text = movie.data

            Glide.with(imgView)
                .load(movie.url)
                .onlyRetrieveFromCache(true)
                .into(imgView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieOffViewHolder {
        return MovieOffViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieOffViewHolder, position: Int) {
        return holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
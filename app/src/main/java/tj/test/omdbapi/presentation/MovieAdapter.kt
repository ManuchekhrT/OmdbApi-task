package tj.test.omdbapi.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.test.omdbapi.domain.model.Movie

class MovieAdapter(private val itemClickListener: (Movie) -> Unit) : ListAdapter<Movie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var onLikeClickListener: ((comment: Movie, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MovieItemView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView as MovieItemView).apply {
            setItem(item)
            rootView.setOnClickListener {
                itemClickListener.invoke(item)
            }
            setOnLikeClickListener {
                onLikeClickListener?.invoke(item, position)
            }
        }
    }

    fun updateItem(item: Movie) {
        val position = currentList.indexOf(item)
        if (position != -1) {
            // Modify the item in your data source or list
            // For example, if using a MutableList:
            val mutableList = currentList.toMutableList()
            mutableList[position] = item

            // Update the list in the adapter
            submitList(mutableList)

            // Notify the adapter about the specific item change
            notifyItemChanged(position)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.imdbId == newItem.imdbId
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
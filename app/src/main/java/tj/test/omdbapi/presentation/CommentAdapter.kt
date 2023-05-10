package tj.test.omdbapi.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.test.omdbapi.domain.model.Comment

class CommentAdapter : ListAdapter<Comment, CommentAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var onDeleteClickListener: ((comment: Comment, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommentItemView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView as CommentItemView).apply {
            setItem(item)
            setOnDeleteClickListener {
                onDeleteClickListener?.invoke(item, position)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean {
                return oldItem.comment == newItem.comment
            }

            override fun areContentsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
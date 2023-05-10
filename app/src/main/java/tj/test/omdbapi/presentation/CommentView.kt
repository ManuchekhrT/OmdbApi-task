package tj.test.omdbapi.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import tj.test.omdbapi.R
import tj.test.omdbapi.databinding.ViewCommentBinding
import tj.test.omdbapi.domain.model.Movie
import tj.test.omdbapi.extensions.hide
import tj.test.omdbapi.extensions.show

class CommentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ViewCommentBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun showImdb(movie: Movie?) {
        with(binding) {
            textComment.show()
            textComment.text = resources.getString(R.string.imdb)
            cvCommentLayout.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("${MovieFragment.IMDB_URL}/${movie?.imdbId}"))
                context.startActivity(browserIntent)
            }
        }
    }

    fun showCounter(movie: Movie?) {
        with(binding) {
            if (movie?.commentsCount == 0) {
                textComment.hide()
            } else {
                textComment.text = movie?.commentsCount.toString()
                textComment.show()
            }
        }
    }
}
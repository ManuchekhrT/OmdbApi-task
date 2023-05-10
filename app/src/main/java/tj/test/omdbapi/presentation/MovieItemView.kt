package tj.test.omdbapi.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import tj.test.omdbapi.R
import tj.test.omdbapi.databinding.ItemMovieBinding
import tj.test.omdbapi.domain.model.Movie

class MovieItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ItemMovieBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setOnLikeClickListener(listener: OnClickListener) {
        binding.buttonLike.setOnClickListener(listener)
    }

    fun setItem(item: Movie) {
        with(binding) {
            textTitle.text = item.title
            textYear.text = item.year
            textType.text = item.type.replaceFirstChar { it.uppercaseChar() }
            if (item.isLiked) {
                buttonLike.setImageResource(R.drawable.ic_like)
            } else {
                buttonLike.setImageResource(R.drawable.ic_dislike)
            }
            viewComment.showCounter(item)
            Glide.with(context)
                .load(item.poster)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imagePoster)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.root.setOnClickListener(l)
    }
}
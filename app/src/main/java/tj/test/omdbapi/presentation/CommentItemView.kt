package tj.test.omdbapi.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import tj.test.omdbapi.databinding.ItemCommentBinding
import tj.test.omdbapi.domain.model.Comment

class CommentItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ItemCommentBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setItem(item: Comment) {
        with(binding) {
            textComment.text = item.comment
        }
    }

    fun setOnDeleteClickListener(listener: OnClickListener) {
        binding.imageDeleteComment.setOnClickListener(listener)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.root.setOnClickListener(l)
    }
}
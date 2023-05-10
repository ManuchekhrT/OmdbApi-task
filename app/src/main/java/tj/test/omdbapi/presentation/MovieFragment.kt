package tj.test.omdbapi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import tj.test.omdbapi.R
import tj.test.omdbapi.databinding.FragmentMovieBinding
import tj.test.omdbapi.domain.model.Comment
import tj.test.omdbapi.domain.model.Movie
import tj.test.omdbapi.extensions.serializable
import tj.test.omdbapi.extensions.showSnackBar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = checkNotNull(_binding)
    private var movie: Movie? = null

    private val commentAdapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.serializable(ARG_MOVIE) as? Movie
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMovieItem()
        initView()
    }

    private fun setupMovieItem() = with(binding) {
        textTitle.text = movie?.title
        textYear.text = movie?.year
        textType.text = movie?.type?.replaceFirstChar { it.uppercaseChar() }
        movie?.isLiked?.let {
            if (it) {
                imageLike.setImageResource(R.drawable.ic_like)
            } else {
                imageLike.setImageResource(R.drawable.ic_dislike)
            }
        }
        Glide.with(requireContext())
            .load(movie?.poster)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imagePoster)
    }

    private fun initView() = with(binding) {
        rvComments.adapter = commentAdapter
        commentAdapter.onDeleteClickListener = { aComment, _ ->
            val commentsList = commentAdapter.currentList.toMutableList()
            commentsList.remove(aComment)
            commentAdapter.submitList(commentsList)

            requireView().showSnackBar(getString(R.string.comment_deleted))
        }

        appToolbar.topAppBar.setNavigationIcon(R.drawable.ic_arrow)
        appToolbar.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewComment.showImdb(movie)
        imageLike.setOnClickListener {
            if (movie?.isLiked != null && !movie!!.isLiked) {
                movie?.isLiked = true
                imageLike.setImageResource(R.drawable.ic_like)
            } else {
                movie?.isLiked = false
                imageLike.setImageResource(R.drawable.ic_dislike)
            }
        }

        buttonLeaveComment.setOnClickListener {
            val comment = textInputComment.text.toString()
            val commentsList = commentAdapter.currentList.toMutableList()
            commentsList.add(Comment(movie?.imdbId, comment))
            commentAdapter.submitList(commentsList.toMutableList())

            textInputComment.setText("")
            requireView().showSnackBar(getString(R.string.comment_added))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IMDB_URL = "https://www.imdb.com/title"
        const val ARG_MOVIE = "arg_movie"
    }
}
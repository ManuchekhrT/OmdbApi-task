package tj.test.omdbapi.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import tj.test.omdbapi.R
import tj.test.omdbapi.databinding.DialogSearchMovieBinding
import tj.test.omdbapi.extensions.showToast

@AndroidEntryPoint
class SearchMovieDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSearchMovieBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                BottomSheetBehavior.from(parent).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    skipCollapsed = true
                }
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        buttonApply.setOnClickListener {
            val searchTitle = textInputTitle.text.toString()
            val quantity = textInputQuantity.text.toString()
            if (searchTitle.isBlank()) {
                requireContext().showToast(resources.getString(R.string.error_fill_search_field))
            } else {
                setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(
                        ARG_SEARCH to searchTitle,
                        ARG_QUANTITY to quantity.toIntOrNull()
                    )
                )
                dismiss()
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "searchParamKey"
        const val ARG_SEARCH = "arg_search"
        const val ARG_QUANTITY = "arg_quantity"
        const val TAG_SEARCH_MOVIE_DIALOG = "TAG_SearchMovieDialog"
    }
}
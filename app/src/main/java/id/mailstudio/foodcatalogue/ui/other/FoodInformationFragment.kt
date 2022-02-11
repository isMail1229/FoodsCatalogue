package id.mailstudio.foodcatalogue.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.mailstudio.foodcatalogue.databinding.FoodInfoLayoutBinding

class FoodInformationFragment : BottomSheetDialogFragment() {

    private var _viewBinding: FoodInfoLayoutBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FoodInfoLayoutBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnUnderstand.setOnClickListener {
            findNavController().popBackStack()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
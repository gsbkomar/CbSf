package gsbkomar.cocktailbar.extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

infix fun Fragment.setBackPressed(customNavId: Int?) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (customNavId != null) {
                    findNavController().navigate(customNavId)
                }
            }
        }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}

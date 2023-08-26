package gsbkomar.cocktailbar.fragments.main_cocktails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.databinding.FragmentMainBinding
import gsbkomar.cocktailbar.factory.MainFragmentViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: MainFragmentViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        with(binding.lottieLoading) {
            setAnimation(R.raw.lottie_loading_2)
            playAnimation()
        }

        lifecycleScope.launch {
            if (viewModel.isContainsCocktailsInDataBase()) {
                findNavController().navigate(R.id.action_mainFragment_to_tapeCocktailsFragment)
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_myCocktailsFragment)
            }
        }
    }
}
package gsbkomar.cocktailbar.fragments.tape_cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.State
import gsbkomar.cocktailbar.databinding.FragmentTapeCocktailsBinding
import gsbkomar.cocktailbar.extensions.setBackPressed
import gsbkomar.cocktailbar.factory.TapeCocktailsViewModelFactory
import gsbkomar.cocktailbar.fragments.tape_cocktails.adapter.CocktailsListAdapter
import gsbkomar.domain.models.Cocktail
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TapeCocktailsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentTapeCocktailsBinding? = null
    private val binding get() = _binding!!

    private var cocktailsListAdapter = CocktailsListAdapter(this) {
        findNavController().navigate(
            R.id.action_tapeCocktailsFragment_to_cocktailDetailsFragment,
            Bundle().apply { putInt("position", it) })
    }

    private var cocktails: List<Cocktail> = listOf()

    @Inject
    lateinit var viewModelFactory: TapeCocktailsViewModelFactory
    private val viewModel: TapeCocktailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTapeCocktailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackPressed(null)

        checkStateInfo()

        lifecycleScope.launch {
            cocktails = viewModel.getAllCocktails()
        }

        binding.btnAdd.setOnClickListener { findNavController().navigate(R.id.action_tapeCocktailsFragment_to_createCocktailFragment) }

        setTapeInfo()
    }

    private fun checkStateInfo() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is State.Error -> TODO()
                    State.Loading -> {
                        lottieLoading.setAnimation(R.raw.lottie_loading_2)
                        lottieLoading.isVisible = true
                        lottieLoading.playAnimation()
                        rcCocktails.isVisible = false
                        btnAdd.isVisible = false
                    }

                    State.Success -> {
                        lottieLoading.isVisible = false
                        lottieLoading.pauseAnimation()
                        rcCocktails.isVisible = true
                        btnAdd.isVisible = true
                    }
                }
            }
        }
    }

    private fun setTapeInfo() {
        binding.rcCocktails.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcCocktails.adapter = cocktailsListAdapter
        lifecycleScope.launch {
            cocktails = viewModel.getAllCocktails()
            cocktailsListAdapter.submitList(cocktails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package gsbkomar.cocktailbar.fragments.details_cocktail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.State
import gsbkomar.cocktailbar.databinding.FragmentCocktailDetailsBinding
import gsbkomar.cocktailbar.factory.CocktailDetailsViewModelFactory
import gsbkomar.cocktailbar.fragments.details_cocktail.adapter.IngredientDetailsListAdapter
import gsbkomar.domain.models.Cocktail
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PARAM = "position"

@AndroidEntryPoint
class CocktailDetailsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentCocktailDetailsBinding? = null
    private val binding get() = _binding!!

    private var position = 1

    private lateinit var cocktail: List<Cocktail>
    private lateinit var ingredients: List<String>

    @Inject
    lateinit var viewModelFactory: CocktailDetailsViewModelFactory
    private val viewModel: CocktailDetailsViewModel by viewModels { viewModelFactory }

    private val ingredientListAdapter = IngredientDetailsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_cocktailDetailsFragment_to_tapeCocktailsFragment)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        checkStateInfo()

        lifecycleScope.launch {
            cocktail = viewModel.getAllCocktails()
            ingredients = cocktail[position].ingredients
            val uri = cocktail[position].photo.toUri()

            with(binding) {
                Glide.with(requireContext())
                    .load(uri)
                    .into(photo)
                description.text = cocktail[position].description
                cocktailName.text = cocktail[position].name
                recipe.text = "   Recipe:\n ${cocktail[position].recipe}"
                btnEdit.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_cocktailDetailsFragment_to_createCocktailFragment,
                        Bundle().apply { putInt("position", position) })
                }
            }
            setIngredientsList()
        }
    }

    private fun setIngredientsList() {
        with(binding.rcIngredients) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ingredientListAdapter
        }
        lifecycleScope.launch {
            ingredientListAdapter.submitList(ingredients)
        }
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
                        photo.isVisible = false
                        banner.isVisible = false
                        btnEdit.isVisible = false
                    }

                    State.Success -> {
                        lottieLoading.isVisible = false
                        lottieLoading.pauseAnimation()
                        photo.isVisible = true
                        banner.isVisible = true
                        btnEdit.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param: Int) =
            CocktailDetailsFragment().apply {
                arguments = Bundle().apply {
                    getInt(ARG_PARAM, param)
                }
            }
    }
}
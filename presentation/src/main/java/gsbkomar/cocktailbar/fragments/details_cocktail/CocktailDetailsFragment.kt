package gsbkomar.cocktailbar.fragments.details_cocktail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.State
import gsbkomar.cocktailbar.databinding.FragmentCocktailDetailsBinding
import gsbkomar.cocktailbar.extensions.setBackPressed
import gsbkomar.cocktailbar.factory.CocktailDetailsViewModelFactory
import gsbkomar.cocktailbar.fragments.details_cocktail.adapter.IngredientDetailsListAdapter
import gsbkomar.domain.models.Cocktail
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CocktailDetailsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentCocktailDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cocktail: List<Cocktail>
    private lateinit var ingredients: List<String>

    @Inject
    lateinit var viewModelFactory: CocktailDetailsViewModelFactory
    private val viewModel: CocktailDetailsViewModel by viewModels { viewModelFactory }

    private val ingredientListAdapter = IngredientDetailsListAdapter()
    private var position = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(KEY_POSITION)
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

        setBackPressed(R.id.action_cocktailDetailsFragment_to_tapeCocktailsFragment)

        checkStateInfo()

        lifecycleScope.launch {
            cocktail = viewModel.getAllCocktails()
            ingredients = cocktail[position].ingredients
            initListeners(cocktail[position].photo.toUri())
            setIngredientsList()
        }
    }

    private fun initListeners(uri: Uri) = with(binding) {
        Glide.with(requireContext())
            .load(uri)
            .into(photo)
        val currentCocktail = cocktail[position]
        description.text = currentCocktail.description
        cocktailName.text = currentCocktail.name
        recipe.text = currentCocktail.recipe
        btnEdit.setOnClickListener {
            findNavController().navigate(
                R.id.action_cocktailDetailsFragment_to_createCocktailFragment,
                Bundle().apply { putInt(KEY_POSITION, position) })
        }

        cvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = MIME_TYPE_TEXT
                putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.share_name, currentCocktail.name) +
                            getString(R.string.share_description, currentCocktail.description) +
                            getString(R.string.share_recipe, currentCocktail.recipe))
            }
            if (context?.let { context -> intent.resolveActivity(context.packageManager) } != null) {
                startActivity(intent)
            }
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
        const val MIME_TYPE_TEXT = "text/plain"
        const val KEY_POSITION = "position"
    }
}
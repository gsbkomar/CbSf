package gsbkomar.cocktailbar.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.adapters.recipe_adapters.RecipeListAdapter
import gsbkomar.cocktailbar.databinding.FragmentCreateCocktailBinding
import gsbkomar.cocktailbar.factory.CreateCocktailViewModelFactory
import gsbkomar.cocktailbar.fragments.viewmodels.CreateCocktailFragmentViewModel
import gsbkomar.data.models.Cocktails
import gsbkomar.data.models.Recipe
import kotlinx.coroutines.launch
import android.app.AlertDialog
import android.util.Log
import gsbkomar.cocktailbar.databinding.DialogLayoutBinding
import javax.inject.Inject

private var dataUri: Uri? = null

@AndroidEntryPoint
class CreateCocktailFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentCreateCocktailBinding? = null
    private val binding get() = _binding!!

    private val recipeListAdapter = RecipeListAdapter { }
    private val recipeList: MutableList<Recipe> = mutableListOf()
    private lateinit var newCocktailData: Cocktails

    @Inject
    lateinit var createCocktailFragmentFactory: CreateCocktailViewModelFactory
    private val viewModel: CreateCocktailFragmentViewModel by viewModels { createCocktailFragmentFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCocktailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rectangle1.setOnClickListener { openGallery() }

            btnSave.setOnClickListener {
                if (checkIsBlanck()) {
                    newCocktailData = Cocktails(
                        uri = dataUri,
                        name = title.text.toString(),
                        description = descriptionEditText.text.toString(),
                        ingredients = recipeList,
                        recipe = recipeEditText.text.toString()
                    )
                    findNavController().navigate(R.id.action_createCocktailFragment_to_tapeCocktailsFragment)
                    viewModel.saveCocktail(newCocktailData)
                } else Toast.makeText(requireContext(), "Cocktail form empty", Toast.LENGTH_SHORT)
                    .show()
            }

            btnAddRecipe.setOnClickListener {
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("")
                    .setView(R.layout.dialog_layout)
                    .setOnCancelListener { dialog ->
                        with(DialogLayoutBinding.inflate(layoutInflater).root) {
                            btnSave.setOnClickListener {
                                dialog.cancel()
                            }
                        }
                    }
                     alertDialog.show()
            }

            // recipeList.add(Recipe(recipeEditText.te))
        }

        with(binding.rcRecipe) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recipeListAdapter
        }

        lifecycleScope.launch {
            recipeListAdapter.submitList(recipeList)
        }
    }


    private fun checkIsBlanck(): Boolean = with(binding) {
        if (title.text?.isNotEmpty() == true && descriptionEditText.text?.isNotEmpty() == true && recipeEditText.text?.isNotEmpty() == true) return true else false
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        dataUri = uri!!

        Picasso.with(requireContext())
            .load(uri)
            .rotate(270F)
            .into(binding.rectangle1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val GALLERY_CODE = 1
    }
}
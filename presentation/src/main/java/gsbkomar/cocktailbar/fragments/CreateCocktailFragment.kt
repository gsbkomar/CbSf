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
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.databinding.FragmentCreateCocktailBinding
import gsbkomar.cocktailbar.factory.CreateCocktailViewModelFactory
import gsbkomar.cocktailbar.viewmodels.ui.CreateCocktailFragmentViewModel
import gsbkomar.data.models.CocktailDto
import kotlinx.coroutines.launch
import android.graphics.Color
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import gsbkomar.cocktailbar.adapters.recipe_adapters.RecipeListAdapter
import gsbkomar.data.db.CocktailsDataBase
import gsbkomar.data.models.IngredientDto
import javax.inject.Inject

private var dataUri: Uri? = null

@AndroidEntryPoint
class CreateCocktailFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentCreateCocktailBinding? = null
    private val binding get() = _binding!!

    private val cocktailDb by lazy {
        Room.databaseBuilder(
            requireContext(),
            CocktailsDataBase::class.java,
            "cocktails.db"
        ).build()
    }

    private lateinit var newIngredientData: IngredientDto
    private val recipeListAdapter = RecipeListAdapter {
        ingredientDtoList.removeAt(it)
        setIngredientsList()
    }

    private var ingredientDtoList: MutableList<IngredientDto> = mutableListOf()
    private lateinit var newCocktailData: CocktailDto

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
        initListeners()


        with(binding) {
            lifecycleScope.launch {
                setErrorEnabled(textField, title)
                setErrorEnabled(description, descriptionEditText)
                setErrorEnabled(recipe, recipeEditText)
            }
            title.doOnTextChanged { text, start, before, count ->
                setErrorEnabled(textField, title)
            }
            descriptionEditText.doOnTextChanged { text, start, before, count ->
                setErrorEnabled(description, descriptionEditText)
            }
            recipeEditText.doOnTextChanged { text, start, before, count ->
                setErrorEnabled(recipe, recipeEditText)
            }
            setIngredientsList()
        }
    }
    fun saveInfo(ingredientDto: IngredientDto) {
        newIngredientData = ingredientDto
    }

    private fun setIngredientsList() {
        with(binding.rcRecipe) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recipeListAdapter
        }
        lifecycleScope.launch {
           // val id = cocktailDb.cocktailDao.getAll().get(cocktailDb.cocktailDao.getAll().size-1)
            //ingredientDtoList = cocktailDb.ingredientDao.getIngredientsByCocktailId(id.id.toLong())
            //cocktailDb.ingredientDao.getIngredientsByCocktailId(id.id.toLong())
            recipeListAdapter.submitList(ingredientDtoList)
        }
    }

    fun testing(string: String) {
        ingredientDtoList.add(IngredientDto(string))
        setIngredientsList()
    }

    private fun setErrorEnabled(layout: TextInputLayout, editText: TextInputEditText) {
        if (editText.text?.isBlank() == true) {
            layout.isErrorEnabled = true
            editText.setHintTextColor(Color.RED)
        } else {
            layout.isErrorEnabled = false
            editText.setHintTextColor(Color.GRAY)
        }
    }
    private fun initListeners() = with(binding) {

        cvPhoto.setOnClickListener {
            openGallery()
        }

        btnSave.setOnClickListener {
            if (checkIsBlank()) {
                lifecycleScope.launch {
                    newCocktailData = CocktailDto(
                        name = title.text.toString(),
                        description = descriptionEditText.text.toString(),
                        recipe = recipeEditText.text.toString(),
                        photo = dataUri.toString(),
                    )
                    cocktailDb.cocktailDao.upsert(newCocktailData)
                }
                findNavController().navigate(R.id.action_createCocktailFragment_to_tapeCocktailsFragment)
               // viewModel.saveCocktail(newCocktailData)
            } else Toast.makeText(requireContext(), "Cocktail form empty", Toast.LENGTH_SHORT)
                .show()
            /*lifecycleScope.launch {
               // TODO db.dao.
            }*/
            btnCancel.setOnClickListener {
                findNavController().navigate(R.id.action_createCocktailFragment_to_myCocktailsFragment)
            }
        }

        btnAddRecipe.setOnClickListener {
            showAddIngredientDialog()
        }
    }

    private fun showAddIngredientDialog() {
        val dialogFragment = AddIngredientDialogFragment(this)
        dialogFragment.show(parentFragmentManager, null)
    }

    private fun checkIsBlank(): Boolean = with(binding) {
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
        Toast.makeText(requireContext(), dataUri.toString(), Toast.LENGTH_SHORT).show()
        Glide.with(requireContext())
            .load(uri)
            .into(binding.photo)

        binding.iconCamera.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val GALLERY_CODE = 1
    }
}
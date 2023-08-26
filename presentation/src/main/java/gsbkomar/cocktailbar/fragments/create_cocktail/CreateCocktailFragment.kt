package gsbkomar.cocktailbar.fragments.create_cocktail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.State
import gsbkomar.cocktailbar.databinding.FragmentCreateCocktailBinding
import gsbkomar.cocktailbar.factory.CreateCocktailViewModelFactory
import gsbkomar.cocktailbar.fragments.create_cocktail.adapter.RecipeListAdapter
import gsbkomar.cocktailbar.fragments.create_cocktail.dialog.AddIngredientDialogFragment
import gsbkomar.domain.models.Cocktail
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PARAM = "position"

@AndroidEntryPoint
class CreateCocktailFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentCreateCocktailBinding? = null
    private val binding get() = _binding!!

    private var dataUri: Uri? = null

    private var ingredientList: MutableList<String> = mutableListOf()

    private lateinit var cocktail: List<Cocktail>

    private var position: Int? = null

    private val recipeListAdapter = RecipeListAdapter {
        ingredientList.removeAt(it)
        setIngredientsList()
    }
    private lateinit var newCocktailData: Cocktail

    @Inject
    lateinit var viewModelFactory: CreateCocktailViewModelFactory
    private val viewModel: CreateCocktailViewModel by viewModels { viewModelFactory }

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
        _binding = FragmentCreateCocktailBinding.inflate(layoutInflater, container, false)
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

        if (position != null) {
            initEditListeners()
        } else {
            initListeners()
        }
    }

    private fun initEditListeners() {
        checkStateInfo()
        lifecycleScope.launch {
            cocktail = viewModel.getAllCocktails()
            cocktail[position!!].ingredients.map {
                ingredientList.add(it)
            }
            setIngredientsList()
            val uri = cocktail[position!!].photo.toUri()

            with(binding) {
                iconCamera.visibility = View.INVISIBLE
                Glide.with(requireContext())
                    .load(uri)
                    .into(photo)
                descriptionEditText.setText(cocktail[position!!].description)
                title.setText(cocktail[position!!].name)
                recipeEditText.setText(cocktail[position!!].recipe)
                btnCancel.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_createCocktailFragment_to_cocktailDetailsFragment,
                        Bundle().apply { putInt("position", position!!) })
                }

                cvPhoto.setOnClickListener {
                    openGallery()
                }

                btnSave.setOnClickListener {
                    saveCocktail(true)
                }
                btnAddRecipe.setOnClickListener {
                    showAddIngredientDialog()
                }
            }
            setEditTextOnTextChanged()
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
                        cvPhoto.isVisible = false
                        description.isVisible = false
                        recipe.isVisible = false
                        btnCancel.isVisible = false
                        btnSave.isVisible = false
                        btnAddRecipe.isVisible = false
                    }

                    State.Success -> {
                        lottieLoading.isVisible = false
                        lottieLoading.pauseAnimation()
                        cvPhoto.isVisible = true
                        description.isVisible = true
                        recipe.isVisible = true
                        btnCancel.isVisible = true
                        btnSave.isVisible = true
                        btnAddRecipe.isVisible = true
                    }
                }
            }
        }
    }

    private fun setIngredientsList() {
        with(binding.rcRecipe) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recipeListAdapter
        }
        lifecycleScope.launch {
            recipeListAdapter.submitList(ingredientList)
        }
    }

    fun refreshIngredientsList(string: String) {
        lifecycleScope.launch {
            ingredientList.add(string)
            setIngredientsList()
        }
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
            saveCocktail(false)
        }

        btnCancel.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.getAllCocktails().isNotEmpty()) {
                    findNavController().navigate(R.id.action_createCocktailFragment_to_tapeCocktailsFragment)
                } else {
                    findNavController().navigate(R.id.action_createCocktailFragment_to_myCocktailsFragment)
                }
            }
        }

        btnAddRecipe.setOnClickListener {
            showAddIngredientDialog()
        }

        setEditTextOnTextChanged()
    }

    private fun newCocktailDataSet(isEdit: Boolean) = with(binding) {
        if (isEdit) {

            if (dataUri == null) {
                newCocktailData = Cocktail(
                    id = position!!.toLong() + 1,
                    name = title.text.toString(),
                    description = descriptionEditText.text.toString(),
                    recipe = recipeEditText.text.toString(),
                    ingredients = ingredientList.toList(),
                    photo = cocktail[position!!].photo,
                )
            } else {
                newCocktailData = Cocktail(
                    id = position!!.toLong() + 1,
                    name = title.text.toString(),
                    description = descriptionEditText.text.toString(),
                    recipe = recipeEditText.text.toString(),
                    ingredients = ingredientList.toList(),
                    photo = dataUri.toString(),
                )
            }

        } else {
            newCocktailData = Cocktail(
                name = title.text.toString(),
                description = descriptionEditText.text.toString(),
                recipe = recipeEditText.text.toString(),
                ingredients = ingredientList.toList(),
                photo = dataUri.toString(),
            )
        }
    }

    private fun saveCocktail(isEdit: Boolean) {
        if (checkIsBlank()) {
            lifecycleScope.launch {
                newCocktailDataSet(isEdit)
                if (isEdit) {
                    viewModel.updateCocktail(newCocktailData, position!!.toLong())
                    findNavController().navigate(R.id.action_createCocktailFragment_to_cocktailDetailsFragment,
                        Bundle().apply { putInt("position", position!!) })
                } else {
                    viewModel.upsertCocktailData(newCocktailData)
                    findNavController().navigate(R.id.action_createCocktailFragment_to_tapeCocktailsFragment)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Cocktail form empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setEditTextOnTextChanged() = with(binding) {
        lifecycleScope.launch {
            setErrorEnabled(textField, title)
            setErrorEnabled(description, descriptionEditText)
            setErrorEnabled(recipe, recipeEditText)
        }
        title.doOnTextChanged { _, _, _, _ ->
            setErrorEnabled(textField, title)
        }
        descriptionEditText.doOnTextChanged { _, _, _, _ ->
            setErrorEnabled(description, descriptionEditText)
        }
        recipeEditText.doOnTextChanged { _, _, _, _ ->
            setErrorEnabled(recipe, recipeEditText)
        }
        setIngredientsList()
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
        private const val GALLERY_CODE = 1
    }
}
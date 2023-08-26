package gsbkomar.cocktailbar.fragments.create_cocktail.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import gsbkomar.cocktailbar.databinding.FragmentAddIngredientDialogBinding
import gsbkomar.cocktailbar.fragments.create_cocktail.CreateCocktailFragment
import java.lang.IllegalStateException
import javax.inject.Inject

class AddIngredientDialogFragment @Inject constructor(private val createCocktailFragment: CreateCocktailFragment) :
    DialogFragment() {

    private var _binding: FragmentAddIngredientDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            _binding = FragmentAddIngredientDialogBinding.inflate(layoutInflater)
            AlertDialog.Builder(it)
                .setView(binding.root)
                .create()
        } ?: throw IllegalStateException("Alert dialog don't create")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() = with(binding) {
        btnSave.setOnClickListener {
            if (ingredientEditText.text.isNullOrEmpty()) {
                ingredientsLayout.error = "Add ingredient"
                ingredientEditText.setHintTextColor(Color.RED)
            } else {
                ingredientsLayout.isErrorEnabled = false
                ingredientEditText.setHintTextColor(Color.GRAY)
                createCocktailFragment.refreshIngredientsList(ingredientEditText.text.toString())
                dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package gsbkomar.cocktailbar.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import gsbkomar.cocktailbar.databinding.FragmentAddIngredientDialogBinding
import gsbkomar.data.db.CocktailsDataBase
import gsbkomar.data.models.IngredientDto
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

class AddIngredientDialogFragment(private val createCocktailFragment: CreateCocktailFragment) : DialogFragment() {

    private var _binding: FragmentAddIngredientDialogBinding? = null
    private val binding get() = _binding!!

    private val db by lazy {
        Room.databaseBuilder(
            requireContext(),
            CocktailsDataBase::class.java,
            "cocktails.db"
        ).build()
    }

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
                createCocktailFragment.testing(ingredientEditText.text.toString())
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
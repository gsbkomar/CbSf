package gsbkomar.cocktailbar.fragments.welcome_cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.databinding.FragmentGreetingsBinding
import gsbkomar.cocktailbar.extensions.setBackPressed
import javax.inject.Inject

class GreetingsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentGreetingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackPressed(null)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnAddCocktail.setOnClickListener {
            findNavController().navigate(R.id.action_myCocktailsFragment_to_createCocktailFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package gsbkomar.cocktailbar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import gsbkomar.cocktailbar.R
import gsbkomar.cocktailbar.databinding.FragmentMyCocktailsBinding
import javax.inject.Inject

class MyCocktailsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentMyCocktailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCocktailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.group1.setOnClickListener {
           findNavController().navigate(R.id.action_myCocktailsFragment_to_createCocktailFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
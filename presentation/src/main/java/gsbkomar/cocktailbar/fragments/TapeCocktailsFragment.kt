package gsbkomar.cocktailbar.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.adapters.cocktails_adapters.CocktailsListAdapter
import gsbkomar.cocktailbar.databinding.FragmentTapeCocktailsBinding
import gsbkomar.cocktailbar.factory.TapeCocktailsViewModelFactory
import gsbkomar.cocktailbar.fragments.viewmodels.TapeCocktailsViewModel
import gsbkomar.data.models.Cocktails
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files
import javax.inject.Inject


private var newCocktails: Cocktails? = null

@AndroidEntryPoint
class TapeCocktailsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentTapeCocktailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cocktailsListAdapter: CocktailsListAdapter
    private var cocktails: MutableList<Cocktails> = mutableListOf()

    private lateinit var shared: SharedPreferences

    private val builder = GsonBuilder()
    private val gson = builder.create()

    @Inject
    lateinit var tapeCocktailsViewModelFactory: TapeCocktailsViewModelFactory
    private val viewModel: TapeCocktailsViewModel by viewModels { tapeCocktailsViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTapeCocktailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = requireActivity().getSharedPreferences("Tape", Context.MODE_PRIVATE)
        cocktailsListAdapter = CocktailsListAdapter(this) {}

        if (newCocktails == null) {
            setTapeInfo()
        } else {
            refreshCocktailsInfo()
        }


        binding.rcCocktails.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcCocktails.adapter = cocktailsListAdapter

        lifecycleScope.launch {
            cocktailsListAdapter.submitList(cocktails)
        }

    }

    /*fun saveUserProfile(userProfile: Cocktails?) {
        val serializedUser = gson.toJson(userProfile)
        shared.edit().putString("tape", serializedUser).apply()
    }


    fun readUserProfile(): Cocktails? {
        val serializedUser = shared.getString("tape", null)
        return gson.fromJson(serializedUser, Cocktails::class.java)
    }

    inline fun <reified T> SharedPreferences.addItemToList(spListKey: String, item: T) {
        val savedList = getList<T>(spListKey).toMutableList()
        savedList.add(item)
        val listJson = Gson().toJson(savedList)
        edit { putString(spListKey, listJson) }
    }

    inline fun <reified T> SharedPreferences.removeItemFromList(spListKey: String, item: T) {
        val savedList = getList<T>(spListKey).toMutableList()
        savedList.remove(item)
        val listJson = Gson().toJson(savedList)
        edit {
            putString(spListKey, listJson)
        }
    }

    fun <T> SharedPreferences.putList(spListKey: String, list: List<T>) {
        val listJson = Gson().toJson(list)
        edit {
            putString(spListKey, listJson)
        }
    }

    inline fun <reified T> SharedPreferences.getList(spListKey: String): List<T> {
        val listJson = getString(spListKey, "")
        if (!listJson.isNullOrBlank()) {
            val type = object : TypeToken<List<T>>() {}.type
            return Gson().fromJson(listJson, type)
        }
        return listOf()
    }*/

    private fun refreshCocktailsInfo() {
        cocktails.add(
            Cocktails(
                uri = newCocktails?.uri,
                name = newCocktails?.name ?: "",
                description = newCocktails?.description ?: "",
                ingredients = newCocktails?.ingredients ?: mutableListOf(),
                recipe = newCocktails?.recipe ?: ""
            )
        )

        setTapeInfo()
    }

    fun saveInfo(info: Cocktails) {
        newCocktails = info
    }

    private fun setTapeInfo() {
        lifecycleScope.launch {
            cocktailsListAdapter.submitList(cocktails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
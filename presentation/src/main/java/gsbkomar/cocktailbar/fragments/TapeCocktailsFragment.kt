package gsbkomar.cocktailbar.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.cocktailbar.adapters.cocktails_adapters.CocktailsListAdapter
import gsbkomar.cocktailbar.databinding.FragmentTapeCocktailsBinding
import gsbkomar.cocktailbar.factory.TapeCocktailsViewModelFactory
import gsbkomar.cocktailbar.viewmodels.ui.TapeCocktailsViewModel
import gsbkomar.data.db.CocktailsDataBase
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.IngredientDto
import kotlinx.coroutines.launch
import java.text.CollationKey
import javax.inject.Inject


private var newCocktailDto: CocktailDto? = null

@AndroidEntryPoint
class TapeCocktailsFragment @Inject constructor() : Fragment() {

    private val db by lazy {
        Room.databaseBuilder(
            requireContext(),
            CocktailsDataBase::class.java,
            "cocktails.db"
        ).build()
    }

    private var _binding: FragmentTapeCocktailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cocktailsListAdapter: CocktailsListAdapter
    private var cocktails: List<CocktailDto> = listOf()

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

        lifecycleScope.launch {
            cocktails = db.cocktailDao.getAll()
        }

        if (newCocktailDto == null) {
            setTapeInfo()
        } else {
            lifecycleScope.launch {
                refreshCocktailsInfo()
            }
        }


        binding.rcCocktails.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcCocktails.adapter = cocktailsListAdapter

        setTapeInfo()

    }

    private suspend fun refreshCocktailsInfo() {
        db.cocktailDao.upsert(
            CocktailDto(
                name = newCocktailDto?.name ?: "",
                description = newCocktailDto?.description ?: "",
                // ingredients = newCocktailDto?.ingredients,
                photo = newCocktailDto?.photo.toString(),
                recipe = newCocktailDto?.recipe ?: ""
            )
        )
        setTapeInfo()
        newCocktailDto = null
    }

    fun saveInfo(info: CocktailDto) {
        newCocktailDto = info
    }

    fun deleteInfo(info: IngredientDto) {
        lifecycleScope.launch {
            db.ingredientDao.delete(info)
        }
    }

    fun getCocktailById() : CocktailDto? {
        var cocktailDto: CocktailDto? = null
        lifecycleScope.launch {
            cocktailDto = db.cocktailDao.getById(db.cocktailDao.getAll().last().id)
        }
        return cocktailDto
    }

    fun getInfo(info: CocktailDto) : List<IngredientDto> {
        var ingredients: List<IngredientDto> = listOf()
        lifecycleScope.launch {
          ingredients = db.ingredientDao.getIngredientsByCocktailId(info.id.toLong())
        }
        return ingredients
    }

    private fun setTapeInfo() {
        lifecycleScope.launch {
            cocktails = db.cocktailDao.getAll()
            cocktailsListAdapter.submitList(cocktails)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
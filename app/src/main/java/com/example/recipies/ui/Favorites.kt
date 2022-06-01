package com.example.recipies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipies.R
import com.example.recipies.databinding.FavoritesBinding
import com.example.recipies.extra.AllRecipesViewModel
import com.example.recipies.extra.Recipe
import com.example.recipies.extra.RecipeAdapter
import com.example.recipies.extra.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Favorites : Fragment(), RecipeAdapter.RecipeItemListener {

    private var _binding: FavoritesBinding? = null
    private val binding get() = _binding!!
    private val allViewModel : AllRecipesViewModel by viewModels()
    private val viewModel : RecipeViewModel by viewModels()
    private  lateinit var  adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesBinding.inflate(inflater, container,false);


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipeAdapter(this)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.adapter = adapter

        allViewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            adapter.setRecipes(it)
        }
    }

    override fun onRecipeClick(recipeId: Int) {
        findNavController().navigate(R.id.action_favorites_to_singleRecipe,
            bundleOf("id" to recipeId))
    }

    override fun onRecipeLongClick(recipe: Recipe) {
        viewModel.recipe.observe(viewLifecycleOwner) {
        }
        viewModel.setId(recipe.id)
        recipe.favorite = !recipe.favorite
        viewModel.update(recipe)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.getRecipes()?.observe(viewLifecycleOwner) {
            //filters recipes by favorite (true)
            val liked = it.filter { it.favorite }
            binding.recycler.adapter = RecipeAdapter(liked, object : RecipeAdapter.RecipeListener {

                override fun onRecipeClicked(index: Int) {
                    //pass index
                    val num = it.indexOf(liked[index])
                    val bundle = bundleOf("index" to num)
                    findNavController().navigate(R.id.action_favorites_to_singleRecipe, bundle)
                }

                override fun onRecipeLongClicked(index: Int) {
                    liked[index].favorite = !liked[index].favorite
                    viewModel.update(liked[index])

                    binding.recycler.adapter!!.notifyItemChanged(index)
                }
            })
        }




                    when(it.status) {
                is Loading -> binding.progressBar.visibility = View.VISIBLE

                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.setRecipes(it.status.data!!)
                }

                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),it.status.message, Toast.LENGTH_LONG).show()
                }
            }
 */
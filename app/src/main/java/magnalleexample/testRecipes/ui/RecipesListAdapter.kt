package magnalleexample.testRecipes.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import magnalleexample.testRecipes.databinding.RecipeItemBinding
import magnalleexample.testRecipes.domain.Recipe
import com.bumptech.glide.Glide
import java.net.URL
import java.text.SimpleDateFormat

//класс для адаптера списка рецептов
class RecipesListAdapter : ListAdapter<Recipe, RecyclerView.ViewHolder>(DiffCallback)  {
    companion object DiffCallback : DiffUtil.ItemCallback<Recipe>(){
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeDataViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeDataViewHolder -> {
                val item: RecipeDataViewHolder = holder
                item.bind(getItem(position))
            }
        }
    }
    class RecipeDataViewHolder private constructor (private val binding: RecipeItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): RecipeDataViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeItemBinding.inflate(layoutInflater, parent, false)
                return RecipeDataViewHolder(binding)
            }
        }
        //функция для заполнения формы элемента списка по данным рецеепта
        fun bind(recipe: Recipe){
            binding.recipeItemName.text = recipe.name
            binding.recipeItemHeadline.text = recipe.headline
            binding.recipeItemDifficulty.text =
                when(recipe.difficulty){
                    0->"easy"
                    1->"medium"
                    2->"hard"
                    else->""
                }
            binding.recipeItemCalories.text = recipe.calories
            binding.recipeItemDescription.text = recipe.description
            Glide
                .with(binding.root)
                .load(recipe.image)
                //.fitCenter()
                .placeholder(CircularProgressDrawable(binding.root.context).let {
                    it.start()
                    it})
                .into(binding.recipeItemImage)
            binding.recipeItemTime.text = recipe.time.removePrefix("PT")
        }
    }
}
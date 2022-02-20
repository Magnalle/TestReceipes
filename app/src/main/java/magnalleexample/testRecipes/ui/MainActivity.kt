package magnalleexample.testRecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import magnalleexample.testRecipes.App
import magnalleexample.testRecipes.R
import magnalleexample.testRecipes.app
import magnalleexample.testRecipes.databinding.ActivityMainBinding
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

class MainActivity : AppCompatActivity() {
    val VISIBILITY_GONE = 4
    val VISIBILITY_VISIBLE = 0
    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //В вью модель прописываем репозиторий, так же можно было сделать это в конструкторе, но тогда нужно было бы создавать фабрику для модели
        //Репозиторий это интерфейс, в принципе можно получать данные не только из интернет репозитория
        viewModel.repo = app.repository

        // Создаем объект биндинга, чтобы задать обработчики для формы
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        //Создаем адаптер для списка рецептов
        val recipesAdapter = RecipesListAdapter()
        binding.recipesRecycleView.adapter = recipesAdapter
        binding.recipesRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //snapHelper нужен, чтобы список нормально фиксировался на странице
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recipesRecycleView)

        //Обработчик события для получения списка рецептов по нажатию кнопки
        binding.refreshButton.setOnClickListener {
            viewModel.getRecipes()
        }

        //задается картинка для отображения загрузки
        binding.loadingImageView.setImageDrawable(CircularProgressDrawable(binding.root.context).let {
            it.start()
            it})
        //событие при изменении статуса загрузки
        viewModel.isLoading.observe(this, Observer{
            setVisibility(binding, it, viewModel.errorText.value?:"")
        })
        //событие при изменении сообщения об ошибке
        viewModel.errorText.observe(this, Observer{
            it?.let{
                binding.errorTextView.text = it
            }
            setVisibility(binding, viewModel.isLoading.value?:false, it)
        })
        //событие при получении списка рецептов из репозитория
        viewModel.recipesList.observe(this, Observer {
            it?.let{
                try{
                    recipesAdapter.submitList(it)
                    setVisibility(binding, false, "")
                }
                catch (e: Exception){
                    viewModel.errorText.postValue(e.localizedMessage?:"")
                }
            }
        })
    }
    //задается видимость в зависимости от статуса загрузки и ошибки
    private fun setVisibility(binding : ActivityMainBinding, isLoading : Boolean, errorText : String){
        when {
            isLoading -> {
                binding.recipesRecycleView.visibility = VISIBILITY_GONE
                binding.errorTextView.visibility = VISIBILITY_GONE
                binding.loadingImageView.visibility = VISIBILITY_VISIBLE
            }
            errorText != "" -> {
                binding.recipesRecycleView.visibility = VISIBILITY_GONE
                binding.errorTextView.visibility = VISIBILITY_VISIBLE
                binding.loadingImageView.visibility = VISIBILITY_GONE
            }
            else -> {
                binding.recipesRecycleView.visibility = VISIBILITY_VISIBLE
                binding.errorTextView.visibility = VISIBILITY_GONE
                binding.loadingImageView.visibility = VISIBILITY_GONE
            }
        }
    }
}
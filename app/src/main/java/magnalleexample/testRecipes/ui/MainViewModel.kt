package magnalleexample.testRecipes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import magnalleexample.testRecipes.Repo.Repo
import magnalleexample.testRecipes.domain.Recipe

class MainViewModel : ViewModel() {
    lateinit var repo: Repo
    val errorText : MutableLiveData<String> = MutableLiveData("")
    val recipesList : MutableLiveData<List<Recipe>> = MutableLiveData(listOf())
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)

    //функция получения рецептов из репозитория
    //выполняется в потоке IO
    fun getRecipes() {
        isLoading.postValue(true)
        GlobalScope.launch(
            Dispatchers.IO){
            repo.getRecipesAsync({ list ->
                isLoading.postValue(false)
                recipesList.postValue(list)
                                 }, { str ->
                isLoading.postValue(false)
                errorText.postValue(str)
                                 })
        }
    }
}
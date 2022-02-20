package magnalleexample.testRecipes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import magnalleexample.testRecipes.Repo.Repo
import magnalleexample.testRecipes.domain.Recipe


class MainViewModel : ViewModel() {
    lateinit var repo: Repo
    val errorText : MutableLiveData<String> = MutableLiveData("")
    val recipesList : MutableLiveData<List<Recipe>> = MutableLiveData(listOf())
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    //функция получения рецептов из репозитория
    //выполняется в потоке IO
    fun getRecipes(){
        isLoading.postValue(true)
        scope.launch(Dispatchers.IO) {
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
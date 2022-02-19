package magnalleexample.testRecipes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import magnalleexample.testRecipes.Repo.Repo

class MainViewModel : ViewModel() {
    lateinit var repo: Repo
    val JSONText : MutableLiveData<String> = MutableLiveData()

    fun getRecipes() {
        Thread{
            repo.getRecipesAsync({ str -> JSONText.postValue(str) }, { str -> JSONText.postValue(str) })
        }.start()
    }
}
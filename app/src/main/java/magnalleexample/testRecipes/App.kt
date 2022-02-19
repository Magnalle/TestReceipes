package magnalleexample.testRecipes

import android.app.Application
import android.content.Context
import magnalleexample.testRecipes.Repo.Repo
import magnalleexample.testRecipes.Repo.RepoInternet

class App : Application() {
    val repository : Repo by lazy { RepoInternet() }
}

val Context.app
    get() = applicationContext as App
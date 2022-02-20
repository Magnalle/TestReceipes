package magnalleexample.testRecipes.Repo

import magnalleexample.testRecipes.domain.Recipe

interface Repo {
    fun getRecipesAsync(onSuccess : (List<Recipe>) -> Unit, onFailure : (String) -> Unit)
}
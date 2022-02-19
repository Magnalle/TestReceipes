package magnalleexample.testRecipes.Repo

interface Repo {
    fun getRecipesAsync(onSuccess : (String) -> Unit, onFailure : (String) -> Unit)
}
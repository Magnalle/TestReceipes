package magnalleexample.testRecipes.Repo

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RepoInternet : Repo {
    val URLString = "https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/recipes.json"
    override fun getRecipesAsync(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        var connection : HttpURLConnection? = null
        try{
            connection = URL(URLString).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5_000

            val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))

            onSuccess.invoke(bufferedReader.readText())
        }
        catch (e: Exception){
            onFailure.invoke(e.localizedMessage?:"")
        }
        finally {
            connection?.disconnect()
        }
    }
}
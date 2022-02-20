package magnalleexample.testRecipes.Repo

import magnalleexample.testRecipes.domain.Recipe
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RepoInternet : Repo {
    val URLString = "https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/recipes.json"
    //функция для получения рецептов из интернета
    //в нее передаются обработчики событий при успехе и при неудаче
    override fun getRecipesAsync(onSuccess: (List<Recipe>) -> Unit, onFailure: (String) -> Unit) {
        var connection : HttpURLConnection? = null
        try{
            connection = URL(URLString).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5_000

            val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))

            val jsonArray  = JSONTokener(bufferedReader.readText()).nextValue() as JSONArray
            val result = mutableListOf<Recipe>()

            for (i in 0 until jsonArray.length()) {

                val jsonObject = jsonArray.getJSONObject(i)
                val element = Recipe(
                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("description"),
                    jsonObject.getInt("difficulty"),
                    jsonObject.getString("time"),
                    jsonObject.getString("headline"),
                    jsonObject.getString("calories"),
                    jsonObject.getString("proteins"),
                    jsonObject.getString("fats"),
                    jsonObject.getString("carbos"),
                    jsonObject.getString("image"),
                    jsonObject.getString("thumb"),
                )
                result.add(element)
            }
            onSuccess.invoke(result)
        }
        catch (e: Exception){
            onFailure.invoke(e.toString()?:"")
        }
        finally {
            connection?.disconnect()
        }
    }


}
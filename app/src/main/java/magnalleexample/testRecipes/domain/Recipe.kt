package magnalleexample.testRecipes.domain

data class Recipe(
    val id : String,
    val name : String,
    val description : String,
    val difficulty : Int,
    val time : String,
    val headline : String,
    val calories : String,
    val proteins : String,
    val fats : String,
    val carbos : String,
    val image : String,
    val thumb : String
)
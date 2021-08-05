import com.google.gson.annotations.SerializedName




data class Question (

	@SerializedName("id") val id : Int,
	@SerializedName("trueword") val trueword : String,
	@SerializedName("falseword") val falseword : String


)
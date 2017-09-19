import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiClient {
    @GET("/w/api.php")
    fun searchNearby(
            @Query("gscoord") location: String,
            @Query("gslimit") limit: Int,
            @Query("gsradius") radius: Int): Call<JsonObject>

    companion object Client {
        private var BASE_URL: String = "https://en.wikipedia.org"
        fun create(): WikiApiClient {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(WikiApiClient::class.java);
        }
    }
}


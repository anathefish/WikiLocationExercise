import com.spacebanana.wikilocationexercise.models.Geosearch
import com.spacebanana.wikilocationexercise.models.Pages
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiClient {
    @GET("/w/api.php?action=query")
    fun getPagesNearby(
            @Query("gscoord") location: String,
            @Query("gslimit") limit: Int,
            @Query("gsradius") radius: Int,
            @Query("format") format: String): Observable<Pages.Result>

    @GET("/w/api.php?action=query")
    fun getPageProperty(
            @Query("prop") property: String,
            @Query("pageids") pageids: String,
            @Query("format") format: String): Observable<Geosearch.Result>

    companion object Client {
        private var BASE_URL: String = "https://en.wikipedia.org"
        fun create(): WikiApiClient {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(WikiApiClient::class.java)
        }
    }
}


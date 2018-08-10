package tw.holidaybear.trends

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET

interface TrendsAPI {

    @GET("/api/terms/")
    fun getTrends(): Single<List<Region>>

    companion object {
        fun create(): TrendsAPI {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://hawttrends.appspot.com/api/terms/")
                    .addConverterFactory(RegionConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(TrendsAPI::class.java)
        }
    }
}
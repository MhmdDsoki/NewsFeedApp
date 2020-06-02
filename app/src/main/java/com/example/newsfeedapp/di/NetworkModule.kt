package  com.example.newsfeedapp.di


import com.example.newsfeedapp.BuildConfig
import com.example.newsfeedapp.data.sources.remoteApi.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.API_KEY)
                        .build()
                )
                .build()
            return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    single {
        GsonConverterFactory.create()
    }
    single {
        CoroutineCallAdapterFactory()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
            .client(get<OkHttpClient>())
            .build()
    }
}

val apiServiceModule = module {

    factory {
        get<Retrofit>().create(ApiService::class.java)
    }

}
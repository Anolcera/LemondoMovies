package anolcera.lemondomovies.network.di

import android.content.Context
import anolcera.lemondomovies.core.network.BuildConfig
import anolcera.lemondomovies.core.network.R
import anolcera.lemondomovies.network.ApiVersions
import anolcera.lemondomovies.network.common.NetworkCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkingModule {

    companion object {

        @Provides
        @Singleton
        fun providesNetworkJson() = Json {
            ignoreUnknownKeys = true
        }

        @Provides
        @Singleton
        fun okHttpCallFactory(
            @ApplicationContext context: Context
        ): Call.Factory = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url
                val url = originalHttpUrl.newBuilder().addQueryParameter(
                    name = context.getString(R.string.themoviedb_api_key_param_name),
                    value = BuildConfig.THE_MOVIE_DB_API_KEY
                ).build()
                request.url(url)
                val response = chain.proceed(request.build())
                return@addInterceptor response
            }
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    },
            )
            .build()

        @Provides
        @Singleton
        fun retrofitBuilder(
            json: Json,
            okHTTpCallFactory: Call.Factory
        ): Retrofit.Builder =
            Retrofit.Builder().callFactory(okHTTpCallFactory)
                .addConverterFactory(
                    json.asConverterFactory("application/json".toMediaType()),
                )
                .addCallAdapterFactory(NetworkCallAdapterFactory(json))

        @Provides
        fun retrofitInstanceBaseUrl(
            retrofitBuilder: Retrofit.Builder
        ): Retrofit =
            retrofitBuilder.baseUrl("https://api.themoviedb.org/${ApiVersions.VERSION_3}/").build()
    }
}
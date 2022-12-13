package com.prosody.gudgames.data.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.prosody.gudgames.data.local.database.GameEntity
import com.prosody.gudgames.data.network.GudNetworkDataSource
import com.prosody.gudgames.data.network.util.ApiKeyHeaderInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for Gud Network API
 */
private interface RetrofitGudNetworkApi {

    @GET(value = "/v4/games")
    suspend fun getGames(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<GameEntity>>
}

// ToDo: Extract base URL to switch by product flavor?
private const val GudBaseUrl = "https://vplgoe6bmg.execute-api.us-west-2.amazonaws.com/production/v4/"

/**
 * Wrapper for data provided from the [GudBaseUrl]
 */
@Serializable
private data class NetworkResponse<T>(
    val data: T
)

/**
 * [Retrofit] backed [GudNetworkDataSource]
 */
@Singleton
class RetrofitGudNetwork @Inject constructor(
    networkJson: Json
) : GudNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(GudBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .addInterceptor(ApiKeyHeaderInterceptor())
                .build()
        )
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitGudNetworkApi::class.java)
}
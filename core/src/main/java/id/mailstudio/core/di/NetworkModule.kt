package id.mailstudio.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.mailstudio.core.data.datasource.remote.network.FoodAPIService
import id.mailstudio.core.utils.Constants
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val hostname = Constants.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, Constants.CERTIFICATE_SHA)
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(50L, TimeUnit.SECONDS)
            .readTimeout(100L, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideFoodApiService(okHttpClient: OkHttpClient): FoodAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_MEAL_URL + Constants.API_KEY)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(FoodAPIService::class.java)
    }
}
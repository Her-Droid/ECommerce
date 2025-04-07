package id.herdroid.ecommercemandiri.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.herdroid.ecommercemandiri.data.api.ApiService
import id.herdroid.ecommercemandiri.data.repository.ProductRepositoryImpl
import id.herdroid.ecommercemandiri.domain.repository.ProductRepository
import id.herdroid.ecommercemandiri.domain.usecase.ProductUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://fakestoreapi.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        baseUrl: String,
        client: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ApiService
    ): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProductUseCase(
        repository: ProductRepository
    ): ProductUseCase {
        return ProductUseCase(repository)
    }
}

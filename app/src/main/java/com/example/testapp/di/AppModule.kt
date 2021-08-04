package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.BuildConfig
import com.example.testapp.Constants
import com.example.testapp.db.AppDataBase
import com.example.testapp.db.TodoDataBaseRepository
import com.example.testapp.network.ApiService
import com.example.testapp.ui.list.TodoListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL


    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideListRepository(apiService: ApiService): TodoListRepository =
        TodoListRepository(apiService)


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "todo_db"
        ).build()

    @Singleton
    @Provides
    fun provideTodoBaseRepository(appDataBase: AppDataBase): TodoDataBaseRepository =
        TodoDataBaseRepository(appDataBase)
}
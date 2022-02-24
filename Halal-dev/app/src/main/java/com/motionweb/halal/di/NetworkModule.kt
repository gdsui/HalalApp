package com.motionweb.halal.di

import com.motionweb.halal.BuildConfig
import com.motionweb.halal.data.network.banner.BannerApi
import com.motionweb.halal.data.network.barcode_scanner.BarcodeScannerApi
import com.motionweb.halal.data.network.catalog.CatalogApi
import com.motionweb.halal.data.network.certificates.CertificatesApi
import com.motionweb.halal.data.network.ecode.ECodeApi
import com.motionweb.halal.data.network.favorite.FavoriteApi
import com.motionweb.halal.data.network.prayertime.PrayerTimeApi
import com.motionweb.halal.data.network.profile.AuthApi
import com.motionweb.halal.data.network.profile.ProfileApi
import com.motionweb.halal.data.network.questions.QuestionApi
import com.motionweb.halal.data.network.request.RequestApi
import com.motionweb.halal.utils.NetworkInterceptor
import com.motionweb.halal.utils.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }
    }

    @Provides
    @Singleton
    @Named("intercepted")
    fun provideInterceptedOkHttpClint(
        networkInterceptor: NetworkInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .authenticator(tokenInterceptor)
                .addInterceptor(networkInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        } else {
            OkHttpClient.Builder()
                .authenticator(tokenInterceptor)
                .addInterceptor(networkInterceptor)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("intercepted")
    fun provideInterceptedRetrofit(@Named("intercepted") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBannerApi(retrofit: Retrofit): BannerApi = retrofit.create(BannerApi::class.java)

    @Provides
    @Singleton
    fun provideECodeApi(retrofit: Retrofit): ECodeApi = retrofit.create(ECodeApi::class.java)

    @Provides
    @Singleton
    fun providePrayerTimeApi(retrofit: Retrofit): PrayerTimeApi =
        retrofit.create(PrayerTimeApi::class.java)

    @Provides
    @Singleton
    fun provideCatalogApi(retrofit: Retrofit): CatalogApi =
        retrofit.create(CatalogApi::class.java)

    @Provides
    @Singleton
    fun provideQuestionApi(retrofit: Retrofit): QuestionApi =
        retrofit.create(QuestionApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideFavoriteApi(@Named("intercepted") retrofit: Retrofit): FavoriteApi =
        retrofit.create(FavoriteApi::class.java)

    @Provides
    @Singleton
    fun provideRequestApi(retrofit: Retrofit): RequestApi =
        retrofit.create(RequestApi::class.java)

    @Provides
    @Singleton
    fun provideProfileApi(@Named("intercepted") retrofit: Retrofit): ProfileApi =
        retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideCertificatesApi(retrofit: Retrofit): CertificatesApi =
        retrofit.create(CertificatesApi::class.java)

    @Provides
    @Singleton
    fun provideBarcodeScannerApi(retrofit: Retrofit): BarcodeScannerApi =
        retrofit.create(BarcodeScannerApi::class.java)

}
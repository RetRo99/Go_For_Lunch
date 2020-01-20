package com.retar.go4lunch.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object ApiClient {

    var BASE_URL:String="https://maps.googleapis.com/maps/api/place/"


    val getGoogleMapsRestaurants: RestaurantApi
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", "AIzaSyB6JuG-GiQgQG2RixaTKyhqBlhT9Uklr6Y")
                    .addQueryParameter("type", "restaurant")
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)


                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()


            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(RestaurantApi::class.java)

        }


}
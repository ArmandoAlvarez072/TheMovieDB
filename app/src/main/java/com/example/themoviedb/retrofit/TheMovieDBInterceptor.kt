package com.example.themoviedb.retrofit

import com.example.themoviedb.app.Constants
import okhttp3.Interceptor
import okhttp3.Response

class TheMovieDBInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // Añadimos parámetros a la URL de la cadena que recibimos (chain)
        val urlWithParams = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(Constants.URL_PARAM_API_KEY, Constants.API_KEY)
            .addQueryParameter(Constants.URL_PARAM_LANGUAGE, "es-ES")
            .build()

        var request = chain.request()

        request = request?.newBuilder()
            .url(urlWithParams)
            .build()

        return chain.proceed(request)

    }
}
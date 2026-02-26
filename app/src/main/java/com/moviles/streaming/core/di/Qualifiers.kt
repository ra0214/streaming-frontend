package com.moviles.streaming.core.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StreamingRESTRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChatOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WebSocketBaseUrl
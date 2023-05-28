package com.vytautasdev.photogallery

import com.vytautasdev.photogallery.api.FlickrApi
import com.vytautasdev.photogallery.api.GalleryItem
import com.vytautasdev.photogallery.api.PhotoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(PhotoInterceptor()).build()
        val retrofit = Retrofit.Builder().baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create()).client(okHttpClient).build()

        flickrApi = retrofit.create()
    }

    suspend fun fetchPhotos(): List<GalleryItem> = flickrApi.fetchPhotos().photos.galleryItems

    suspend fun searchPhotos(query: String): List<GalleryItem> =
        flickrApi.searchPhotos(query).photos.galleryItems

}
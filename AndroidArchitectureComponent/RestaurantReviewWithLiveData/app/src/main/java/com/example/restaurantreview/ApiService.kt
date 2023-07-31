package com.example.restaurantreview

import retrofit2.Call
import retrofit2.http.*

/**
 * API Service merupakan interface yang berisi kumpulan endpoint yang digunakan pada sebuah aplikasi.
 */
interface ApiService {

    /**
     * Pada aplikasi ini terdapat 2 endpoint, yakni fungsi getRestaurant dengan annotation @GET
     * untuk mengambil data. Anda dapat mengganti variable {id} pada endpoint dengan menggunakan @Path
     * sehingga dapat mengakses detail suatu restoran dengan URL https://restaurant-api.dicoding.dev/detail/uewq1zg2zlskfw1e867.
     */
    @GET("detail/{id}")
    fun getRestaurantDetailt(
        @Path("id") id: String
    ): Call<RestaurantDetailResponse>

    /**
     * fungsi postReview dengan annotation @POST untuk mengirim data.
     * Selain itu, Anda juga dapat menambahkan @Header untuk menyematkan token jika API tersebut
     * membutuhkan otorisasi. Kemudian, Anda harus memakai anotasi @FormUrlEncoded untuk mengirimkan
     * data dengan menggunakan @Field. Pastikan key yang dimasukkan pada @Field harus sama dengan field
     * yang ada pada API. Jika tidak sama, data tidak akan terkirim.
     */
    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ): Call<PostReviewResponse>
}
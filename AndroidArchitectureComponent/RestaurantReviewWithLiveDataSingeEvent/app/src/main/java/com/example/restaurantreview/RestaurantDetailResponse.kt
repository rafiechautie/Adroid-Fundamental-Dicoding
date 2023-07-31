package com.example.restaurantreview

import com.google.gson.annotations.SerializedName

/**
 * class RestaurantDetailResponse digunakan untuk mengambil data respon dari server seperti error dan message
 *
 * ntuk membedakan antara JSONObject dan JSONArray, cukup gunakan List<CustomerReviewsItem> untuk JSONArray;
 * dan Restaurant tanpa List untuk JSONObject.
 */
data class RestaurantDetailResponse(

	/**
	 * Kemudian untuk menandai sebuah variabel terhubung dengan data JSON, gunakan annotation @SerializedName
	 */
	@field:SerializedName("restaurant")
	val restaurant: Restaurant,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

/**
 * class restaurant digunakan untuk mengambil data json object restaurant
 */

data class Restaurant(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,


	@field:SerializedName("pictureId")
	val pictureId: String,


	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,


)

/**
 * class CustomerReviewsItem digunakan untuk mengambil data customer reviews
 */
data class CustomerReviewsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("name")
	val name: String
)

/**
 * class CustomerReviewsItem digunakan untuk mengambil data PostReviewResponse
 */
data class PostReviewResponse(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)




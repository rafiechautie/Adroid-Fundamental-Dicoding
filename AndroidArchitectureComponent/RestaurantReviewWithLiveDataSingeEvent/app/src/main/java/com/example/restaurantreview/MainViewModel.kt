package com.example.restaurantreview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {

    /**
     * erdapat LiveData dan MutableLiveData. Lalu apa bedanya? Keduanya sebenarnya mirip,
     * namun bedanya MutableLiveData bisa kita ubah value-nya, sedangkan LiveData bersifat read-only (tidak dapat diubah).
     */
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    /**
     * membuat 2 variable dan memisahkan variable live data dan mutableLiveData berfungsi membuat
     * data yang bertipe MutableLiveData menjadi private (_listReview) dan yang bertipe LiveData
     * menjadi public (listReview). Cara ini disebut dengan backing property. Dengan begitu Anda
     * dapat mencegah variabel yang bertipe MutableLiveData diubah dari luar class. Karena memang
     * seharusnya hanya ViewModel-lah yang dapat mengubah data
     *
     */
    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * code dibawah adalah cara menggunakan EventWrapper
     * Anda hanya perlu membungkus (wrap) data yang ingin dijadikan single event
     * Anda dapat membungkusnya dengan format Event<TipeData> seperti contoh di bawah
     *
     * Kemudian untuk memasukkan nilai ke dalam variabel tersebut, Anda harus menginisialisasi Event
     * dengan constructor pesan yang ingin dijadikan sebagai content seperti ini untuk menyesuaikan tipenya.
     */
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        /**
         * Yang dimaksud mengubah value-nya adalah pada bagian ini:
         */
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurantDetailt(RESTAURANT_ID)
        client.enqueue(object : retrofit2.Callback<RestaurantDetailResponse>{
            override fun onResponse(
                call: Call<RestaurantDetailResponse>,
                response: Response<RestaurantDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        /**
                         * Yang dimaksud mengubah value-nya adalah pada bagian ini:
                         */
                        _restaurant.value = response.body()?.restaurant
                        /**
                         * Yang dimaksud mengubah value-nya adalah pada bagian ini:
                         */
                        _listReview.value = response.body()?.restaurant?.customerReviews
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

     fun postReview(review: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
        client.enqueue(object : retrofit2.Callback<PostReviewResponse>{
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listReview.value = response.body()?.customerReviews
                    /**
                     * untuk memasukkan nilai ke dalam variabel _snackbarText, Anda harus menginisialisasi class Event
                     * dengan constructor pesan yang ingin dijadikan sebagai content seperti ini untuk menyesuaikan tipenya.
                     */
                    _snackbarText.value = Event(response.body()?.message.toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }


}
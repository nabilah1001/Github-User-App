package com.dicoding.picodiploma.githubuserapp3.detailuser


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp3.api.ApiConfig
import com.dicoding.picodiploma.githubuserapp3.api.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.e("Failure", t.message!!)
            }

        })
    }
    fun getListFollowers(): LiveData<ArrayList<ItemsItem>> {
        return listFollowers
    }
}
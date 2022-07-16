package com.dicoding.picodiploma.githubuserapp3.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp3.api.ApiConfig
import com.dicoding.picodiploma.githubuserapp3.api.ItemsItem
import com.dicoding.picodiploma.githubuserapp3.api.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()

    fun setSearchUsers(query: String){
        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful){
                    listUsers.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("Failure", t.message!!)
            }
        })
    }

    fun getSearchUsers(): LiveData<ArrayList<ItemsItem>> {
        return listUsers
    }
}
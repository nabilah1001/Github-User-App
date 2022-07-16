package com.dicoding.picodiploma.githubuserapp3.detailuser


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.githubuserapp3.api.ApiConfig
import com.dicoding.picodiploma.githubuserapp3.api.DetailResponse
import com.dicoding.picodiploma.githubuserapp3.favorite.data.DatabaseFavorite
import com.dicoding.picodiploma.githubuserapp3.favorite.data.FavoriteUser
import com.dicoding.picodiploma.githubuserapp3.favorite.data.FavoriteUserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailResponse>()

    private var userDao: FavoriteUserDao?
    private var userDb: DatabaseFavorite?

    init {
        userDb = DatabaseFavorite.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e("Failure", t.message!!)
            }
        })
    }

    fun getUserDetail(): LiveData<DetailResponse> {
        return user
    }

    fun addToFavorite(login: String, avatarUrl: String, htmlUrl: String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                login,
                avatarUrl,
                htmlUrl,
                id
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}
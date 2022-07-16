package com.dicoding.picodiploma.githubuserapp3.favorite.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.githubuserapp3.favorite.data.DatabaseFavorite
import com.dicoding.picodiploma.githubuserapp3.favorite.data.FavoriteUser
import com.dicoding.picodiploma.githubuserapp3.favorite.data.FavoriteUserDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDb: DatabaseFavorite?

    init {
        userDb = DatabaseFavorite.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}
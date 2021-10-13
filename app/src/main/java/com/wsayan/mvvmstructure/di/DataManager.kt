package com.wsayan.mvvmstructure.di

import android.content.Context
import com.wsayan.mvvmstructure.db.RoomHelper
import com.wsayan.mvvmstructure.network.IApiService
import com.wsayan.mvvmstructure.preference.PreferencesHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataManager @Inject constructor(
    val preferencesHelper: PreferencesHelper,
    val roomHelper: RoomHelper,
    val apiService: IApiService,
    @ApplicationContext context: Context
)
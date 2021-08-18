package com.wsayan.mvvmstructure

import com.wsayan.mvvmstructure.local.db.RoomHelper
import com.wsayan.mvvmstructure.network.ApiHelper
import com.wsayan.mvvmstructure.local.preference.PreferencesHelper
import javax.inject.Inject

class DataManager @Inject constructor(
    val preferencesHelper: PreferencesHelper, val roomHelper: RoomHelper,val apiHelper: ApiHelper
)
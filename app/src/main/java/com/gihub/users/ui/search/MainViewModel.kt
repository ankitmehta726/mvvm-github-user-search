package com.gihub.users.ui.search

import androidx.lifecycle.ViewModel
import com.gihub.users.data.AppDataManager
import javax.inject.Inject

class MainViewModel
    @Inject
    constructor(var appDataManager: AppDataManager): ViewModel(){
}
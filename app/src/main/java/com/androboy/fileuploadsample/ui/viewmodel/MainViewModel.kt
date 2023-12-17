package com.androboy.fileuploadsample.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androboy.fileuploadsample.repository.ImageUploadRepository
import com.androboy.fileuploadsample.ui.model.ImageUploadResponse
import com.androboy.fileuploadsample.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val imageUploadRepository: ImageUploadRepository
) : ViewModel() {


    private val _uploadImageLiveData = MutableLiveData<NetworkResult<ImageUploadResponse>>()
    val uploadImageLiveData: LiveData<NetworkResult<ImageUploadResponse>>
        get() = _uploadImageLiveData


    fun uploadImage(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            imageUploadRepository.uploadImage(page).let {
                when (it) {
                    is NetworkResult.Success -> {
                        Log.d("MMMMMM", "setObserver Success:  ${it.data.toString()}")
                        _uploadImageLiveData.postValue(it)

                    }

                    is NetworkResult.Error -> {
                        Log.d("MMMMMM", "setObserver Error: ")
                    }

                    is NetworkResult.Loading -> {
                        Log.d("MMMMMM", "setObserver Loading: ")
                    }
                }
            }
        }


    }

}
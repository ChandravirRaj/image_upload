package com.androboy.fileuploadsample.ui.viewmodel

import android.content.Context
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val imageUploadRepository: ImageUploadRepository
) : ViewModel() {


    private val _uploadImageLiveData = MutableLiveData<NetworkResult<ImageUploadResponse>>()
    val uploadImageLiveData: LiveData<NetworkResult<ImageUploadResponse>>
        get() = _uploadImageLiveData


    fun uploadImage(file: File){

        viewModelScope.launch(Dispatchers.IO) {
            imageUploadRepository.uploadImage(file).let {
                _uploadImageLiveData.postValue(it)
            }
        }


    }

}
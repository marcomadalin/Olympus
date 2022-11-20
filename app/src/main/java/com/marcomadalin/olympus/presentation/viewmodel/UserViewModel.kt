package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteUsersUseCase
import com.marcomadalin.olympus.domain.cases.GetUserUseCase
import com.marcomadalin.olympus.domain.cases.SaveUserUseCase
import com.marcomadalin.olympus.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteUsersUseCase: DeleteUsersUseCase
) : ViewModel() {

    val user = MutableLiveData<User>()

    fun saveUser(user: User) {
        viewModelScope.launch { saveUserUseCase.invoke(user) }
    }

    fun getUser() {
        viewModelScope.launch {
            val result = getUsersUseCase()
            user.postValue(result)
        }
    }

    fun deleteUsers() {
        viewModelScope.launch {
            deleteUsersUseCase()
        }
    }
}
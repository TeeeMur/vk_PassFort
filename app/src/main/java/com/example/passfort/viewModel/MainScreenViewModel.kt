package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.PasswordsListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repo: PasswordsListRepo): ViewModel() {
    private val _recentPasswords: MutableStateFlow<List<PasswordRecordEntity>> = MutableStateFlow(emptyList())
    private val _pinnedPasswords: MutableStateFlow<List<PasswordRecordEntity>> = MutableStateFlow(emptyList())
    private val _thirdPasswords: MutableStateFlow<List<PasswordRecordEntity>> = MutableStateFlow(emptyList())
    val recentPasswords = _recentPasswords.asStateFlow()
    val pinnedPasswords = _pinnedPasswords.asStateFlow()
    val thirdPasswords = _thirdPasswords.asStateFlow()

    init {
        update()
    }

    fun update() {
        viewModelScope.launch{
            _recentPasswords.update { repo.getAllPasswords() }
            _pinnedPasswords.update { repo.getPinnedPasswords() }
            _thirdPasswords.update { repo.getPinnedPasswords() }
        }
    }

}
package com.example.passfort.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.MainScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repo: MainScreenRepo): ViewModel() {
    private var _recentPasswords: MutableStateFlow<ImmutableList<PasswordRecordEntity>> = MutableStateFlow(persistentListOf())
    private var _pinnedPasswords: MutableStateFlow<ImmutableList<PasswordRecordEntity>> = MutableStateFlow(persistentListOf())
    val recentPasswords = _recentPasswords.asStateFlow()
    val pinnedPasswords = _pinnedPasswords.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getRecentPasswords(5).collect { newIt ->
                _recentPasswords.update { oldIt -> newIt.toImmutableList() }
            }
            repo.getPinnedPasswords().collect { newIt ->
                _pinnedPasswords.update { oldIt -> newIt.toImmutableList() }
            }
        }
    }

}
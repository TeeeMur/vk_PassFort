package com.example.passfort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passfort.model.PreferencesManager
import com.example.passfort.model.dbentity.PasswordRecordEntity
import com.example.passfort.repository.MainScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repo: MainScreenRepo,
    private val prefs: PreferencesManager) : ViewModel() {
    private var _recentPasswords: MutableStateFlow<ImmutableList<PasswordRecordEntity>> =
        MutableStateFlow(persistentListOf())
    private var _pinnedPasswords: MutableStateFlow<ImmutableList<PasswordRecordEntity>> =
        MutableStateFlow(persistentListOf())
    val userName: MutableStateFlow<String> = MutableStateFlow(prefs.getName())
    val recentPasswords = _recentPasswords.asStateFlow()
    val pinnedPasswords = _pinnedPasswords.asStateFlow()

    init {
        prefs.prefs.registerOnSharedPreferenceChangeListener {itPref, name ->
            userName.update { itPref.getString(name, "").toString() }
        }
        repo.getRecentPasswords(5).onEach { newIt ->
            _recentPasswords.update { oldIt -> newIt.toImmutableList() }
        }.launchIn(viewModelScope)
        repo.getPinnedPasswords(5).onEach { newIt ->
            _pinnedPasswords.update { oldIt -> newIt.toImmutableList() }
        }.launchIn(viewModelScope)
    }
    fun pinPassword(passwordId: Long) {
        viewModelScope.launch {
            var password = repo.getByIdPassword(passwordId)
            password.pinned = !password.pinned
            repo.pinPassword(password)
        }
    }

    fun deletePassword(passwordId: Long) {
        viewModelScope.launch {
            repo.deletePassword(passwordId)
        }
    }
}
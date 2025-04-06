package com.example.passfort.ui

import com.example.passfort.dbentity.PasswordRecordEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PasswordsScreenListState(
    val passwordsPinnedList: ImmutableList<PasswordRecordEntity> = persistentListOf(),
    val passwordsNotPinnedList: ImmutableList<PasswordRecordEntity> = persistentListOf(),
    val screenState: ScreenState = ScreenState.LOADING
)

enum class ScreenState {
    SUCCESS,
    LOADING,
    ERROR
}

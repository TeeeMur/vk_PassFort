package com.example.passfort.ui

import com.example.passfort.dbentity.PasswordRecordEntity

data class PasswordsScreenListState(
    val passwordsPinnedList: List<PasswordRecordEntity> = mutableListOf(),
    val passwordsNotPinnedList: List<PasswordRecordEntity> = mutableListOf(),
    val screenState: ScreenState = ScreenState.LOADING
)

enum class ScreenState {
    SUCCESS,
    LOADING,
    ERROR
}

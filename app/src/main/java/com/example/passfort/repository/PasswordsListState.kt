package com.example.passfort.repository

import com.example.passfort.dbentity.PasswordRecordEntity

data class ScreenListState(
    var passwordsPinnedList: List<PasswordRecordEntity> = mutableListOf(),
    var passwordsNotPinnedList: List<PasswordRecordEntity> = mutableListOf(),
    var screenState: ScreenState = ScreenState.Loading
)

enum class ScreenState {
    Success,
    Loading,
    Error
}

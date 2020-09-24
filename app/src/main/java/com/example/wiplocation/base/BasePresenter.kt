package com.example.wiplocation.base

open class BasePresenter(baseView: BaseView) {
    val realmDbService = baseView.getWipApplication().realmDbService
}
package com.interview.fashiondays.base

import com.interview.fashiondays.data.restmanager.ResponseCallback

abstract class BasePresenter<T : BaseMvp.View> : BaseMvp.Presenter, ResponseCallback {

    protected var view: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun attachView(view: BaseMvp.View) {
        this.view = view as? T
    }

}
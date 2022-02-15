package com.interview.fashiondays.base

interface BaseMvp {

    interface View {
        fun showLoader()
        fun hideLoader()
    }

    interface Presenter {
        fun attachView(view: View)
        fun onDestroy()
        fun cancelDataCall()
    }
}
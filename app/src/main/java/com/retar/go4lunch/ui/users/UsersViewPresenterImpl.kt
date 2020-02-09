package com.retar.go4lunch.ui.users

import android.util.Log
import com.retar.go4lunch.repository.users.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersViewPresenterImpl @Inject constructor(
    private val repo: UsersRepository,
    private val view: UsersView
) : UsersViewPresenter {

    private var disposable: Disposable? = null


    override fun onActivityCreated() {
        disposable =
            repo.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view.setData(it)
                    },
                    onError = {
                        Log.d("čič", "onError")

                    },
                    onComplete = {
                        Log.d("čič", "onComplete")
                    }


                )

    }
}

package com.retar.go4lunch.ui.users

import android.util.Log
import com.retar.go4lunch.repository.users.UsersRepository
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class UsersViewPresenterImpl @Inject constructor(
    private val repo: UsersRepository,
    private val view: UsersView
) : UsersViewPresenter {

    private var disposable: Disposable? = null


    override fun onActivityCreated() {
        disposable =
            repo.getUsers()
                .subscribeBy(
                    onNext = {
                        view.setData(it)
                        Log.d("čič", "onNext")

                    },
                    onError = {
                        //todo handle error
                    }
                )

    }

    override fun onDestroy() {
        disposable?.dispose()
    }
}

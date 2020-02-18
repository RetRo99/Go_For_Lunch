package com.retar.go4lunch.ui.users

import com.retar.go4lunch.R
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
                    },
                    onError = {
                        view.showToast(R.string.error_no_user_data)
                    }
                )

    }

    override fun onDestroy() {
        disposable?.dispose()
    }
}

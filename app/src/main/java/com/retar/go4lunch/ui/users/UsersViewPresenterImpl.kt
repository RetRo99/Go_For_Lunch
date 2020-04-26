package com.retar.go4lunch.ui.users

import com.retar.go4lunch.R
import com.retar.go4lunch.repository.users.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UsersViewPresenterImpl @Inject constructor(
    private val repo: UsersRepository,
    private val view: UsersView
) : UsersViewPresenter {

    private var disposable: Disposable? = null

    private var searchDisposable: Disposable? = null

    private val searchText: BehaviorSubject<String> = BehaviorSubject.create()

    override fun onActivityCreated() {
        disposable =
            repo.getUsers()
                .subscribeBy(
                    onNext = {
                        view.setData(it)
                    },
                    onError = {
                        it.printStackTrace()
                        view.showToast(R.string.error_no_user_data)
                    }
                )

        searchDisposable = searchText
            .debounce(750, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeBy(
                onNext = {
                    repo.getFilteredUsers(it)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onSuccess = {
                                view.setData(it)
                            }
                        )
                },
                onError = {
                    view.setData(listOf())
                }
            )

    }

    override fun onDestroy() {
        disposable?.dispose()
        searchDisposable?.dispose()

    }

    override fun onSearchChanged(text: CharSequence?) {
        searchText.onNext(text.toString())
    }
}

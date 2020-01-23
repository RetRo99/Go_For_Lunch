package com.retar.go4lunch.ui.mainfragment

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module


@Module
internal interface MainModule {

    @Binds
    fun bindView(fragment: MainFragment): MainView

    @Binds
    fun bindPresenter(presenterImpl: MainViewPresenterImpl): MainViewPresenter

    @Binds
    fun bindFragment(fragment: MainFragment): Fragment


}




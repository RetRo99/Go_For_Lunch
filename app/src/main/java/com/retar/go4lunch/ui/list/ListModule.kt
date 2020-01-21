package com.retar.go4lunch.ui.list

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module

@Module
internal interface ListModule {

    @Binds
    fun bindView(fragment: ListFragment): ListView

    @Binds
    fun bindPresenter(presenterImpl: ListViewPresenterImpl): ListViewPresenter

    @Binds
    fun bindFragment(fragment: ListFragment): Fragment

}
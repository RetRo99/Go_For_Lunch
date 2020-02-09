package com.retar.go4lunch.ui.users

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module

@Module
interface UsersModule {

    @Binds
    fun bindView(fragment: UsersFragment): UsersView

    @Binds
    fun bindPresenter(presenterImpl: UsersViewPresenterImpl): UsersViewPresenter

    @Binds
    fun bindFragment(fragment: UsersFragment): Fragment
}
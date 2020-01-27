package com.retar.go4lunch.ui.holderfragment

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module


@Module
internal interface HolderModule {

    @Binds
    fun bindView(fragment: HolderFragment): HolderView

    @Binds
    fun bindPresenter(presenterImpl: HolderViewPresenterImpl): HolderViewPresenter

    @Binds
    fun bindFragment(fragment: HolderFragment): Fragment


}




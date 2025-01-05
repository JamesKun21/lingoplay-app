package com.alexius.core.di

import com.alexius.core.data.manager.AuthenticationManagerImplementation
import com.alexius.core.data.manager.LocalUserManagerImplementation
import com.alexius.core.domain.manager.AuthenticationManager
import com.alexius.core.domain.manager.LocalUserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindLocalUserManger(localUserMangerImpl: LocalUserManagerImplementation) : LocalUserManager

    @Binds
    @Singleton
    abstract fun bindAuthenticationManager(authenticationManagerImpl: AuthenticationManagerImplementation) : AuthenticationManager
}
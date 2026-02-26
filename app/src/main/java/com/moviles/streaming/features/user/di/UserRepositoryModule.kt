package com.moviles.streaming.features.user.di



import com.moviles.streaming.features.user.data.repositories.UserRepositoryImp
import com.moviles.streaming.features.user.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImp: UserRepositoryImp
    ): UserRepository
}
package com.teacher.english.data.di
import com.teacher.english.data.repository.AuthRepository
import com.teacher.english.data.repository.AuthRepositoryImpl
import com.teacher.english.data.repository.EnglishRepository;
import com.teacher.english.data.repository.EnglishRepositoryImpl;
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindSingletonRepository {

    @Binds
    @Singleton
    fun bindEnglishRespository(englishRepository: EnglishRepositoryImpl): EnglishRepository

    @Binds
    @Singleton
    fun bindAuthRespository(authRepository: AuthRepositoryImpl): AuthRepository
}
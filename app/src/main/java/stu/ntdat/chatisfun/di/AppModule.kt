package stu.ntdat.chatisfun.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import stu.ntdat.chatisfun.model.database.AppDatabase
import stu.ntdat.chatisfun.model.repository.AccountRepository
import stu.ntdat.chatisfun.model.repository.MainRepository
import stu.ntdat.chatisfun.util.FireStoreAction
import stu.ntdat.chatisfun.util.FireStoreHelper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideFireStoreAction(): FireStoreAction = FireStoreHelper.getInstance()

    @Singleton
    @Provides
    fun provideFireStoreHelper(): FireStoreHelper = FireStoreHelper.getInstance()

    @Singleton
    @Provides
    fun provideAccountRepository(
        fireStoreHelper: FireStoreHelper,
    ): AccountRepository = AccountRepository.getInstance(fireStoreHelper)

    @Singleton
    @Provides
    fun provideMainRepository(
        fireStoreHelper: FireStoreHelper,
        database: AppDatabase,
    ): MainRepository = MainRepository.getInstance(fireStoreHelper, database)

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}
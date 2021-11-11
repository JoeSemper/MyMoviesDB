package com.joesemper.mymoviesdb.di

import org.koin.androidx.viewmodel.dsl.viewModel
import com.joesemper.mymoviesdb.data.datasource.MoviesRepositoryRetrofitImpl
import com.joesemper.mymoviesdb.data.repository.MoviesRepository
import com.joesemper.mymoviesdb.ui.viewmodel.HomeViewModel
import org.koin.dsl.module

val appModule = module {
    single<MoviesRepository> { MoviesRepositoryRetrofitImpl() }
}

val mainActivity = module {
    viewModel { HomeViewModel(get()) }
}
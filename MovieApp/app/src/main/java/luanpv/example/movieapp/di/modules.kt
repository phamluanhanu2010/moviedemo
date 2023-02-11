package com.pgbank.personal.di


import com.pgbank.personal.data.impl.SearchRepo
import luanpv.example.movieapp.ui.activities.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val models = module {
    viewModel {
        SearchViewModel(get())
    }
}

val impls = module {

    single {
        SearchRepo(get())
    }
}

val daos = module {

}

val utilities = module {
    single<ResourceProvider> { AndroidResourceProvider(get()) }
}

val modules = listOf(utilities, networks, models, dialogs, impls, daos)
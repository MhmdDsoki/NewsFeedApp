package  com.example.newsfeedapp.di


import com.example.newsfeedapp.data.sources.remoteApi.ApiHelperImpl
import com.example.newsfeedapp.common.INetworkAwareHandler
import com.example.newsfeedapp.common.NetworkHandler
import com.example.newsfeedapp.data.*
import com.example.newsfeedapp.data.sources.homeCahedData.IOfflineDataSource
import com.example.newsfeedapp.data.sources.homeCahedData.OfflineSourcesRoomBased
import com.example.newsfeedapp.data.sources.remoteApi.IApiHelper
import com.example.newsfeedapp.data.sources.remoteApi.IOnlineDataSource
import com.example.newsfeedapp.data.sources.remoteApi.OnlineSourcesBasedRetroFit
import org.koin.dsl.module

val repoModule = module {

    // Provide NewsRepository
    single { NewsRepository(get() , get() , get() ) }

    factory  <IOfflineDataSource>{ OfflineSourcesRoomBased(get()) }

    factory <IOnlineDataSource> { OnlineSourcesBasedRetroFit(get())  }

    single <INetworkAwareHandler> { NetworkHandler(get())  }

    single { FavRepo(get()) }


    factory <IApiHelper> { ApiHelperImpl(get())  }


}

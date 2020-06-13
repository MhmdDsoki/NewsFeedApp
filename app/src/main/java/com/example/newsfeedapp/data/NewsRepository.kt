package com.example.newsfeedapp.data



import androidx.lifecycle.MutableLiveData
import com.example.newsfeedapp.common.INetworkAwareHandler
import com.example.newsfeedapp.common.Resource
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.data.sources.homeCahedData.IOfflineDataSource
import com.example.newsfeedapp.data.sources.remoteApi.IOnlineDataSource
import com.example.newsfeedapp.data.sources.remoteApi.OnlineSourcesBasedRetroFit


class NewsRepository(
    private val offlineDataSource: IOfflineDataSource,
    private val onlineDataSource: IOnlineDataSource,
    private val networkHandler: INetworkAwareHandler) {


    //var sourcesList = MutableLiveData<Resource<Article>>()

    suspend fun getNewsSources() : List<Article> {

        //sourcesList.postValue(Resource.Loading())
        return if (networkHandler.isOnline()) {
            offlineDataSource.deleteAllNews()
            val data = onlineDataSource.getArticles()
            //sourcesList.postValue(Resource.Success(data))
            offlineDataSource.cacheArticles(data)
            data
        } else {
            //sourcesList.postValue(Resource.Error(data = offlineDataSource.getArticles() , msg = OnlineSourcesBasedRetroFit.errorMsg))
            offlineDataSource.getArticles()
        }
    }

}


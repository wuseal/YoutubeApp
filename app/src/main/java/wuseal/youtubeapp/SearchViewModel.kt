package wuseal.youtubeapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request

class SearchViewModel : ViewModel() {

    private val okHttpClient = OkHttpClient()
    private val _searchResult = MutableLiveData<List<VideoItem>>()
    val searchResult:LiveData<List<VideoItem>> = _searchResult


    suspend fun search(key: String) {
       val obtainSearchHtml =  withContext(Dispatchers.IO){
           val request =  Request.Builder().url("https://www.youtube.com/results?search_query=$key").build()
           try {
               okHttpClient.newCall(request).execute().body?.byteStream()?.reader()?.readText()
           } catch (e: Exception) {
               e.printStackTrace()
               null
           }
        }


        val result = Regex("\"videoId\":\".*\"").findAll(obtainSearchHtml.toString()).map { it.value }

        Log.d("Test",result.toList().joinToString("\n"))
    }
}
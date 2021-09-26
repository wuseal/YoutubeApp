package wuseal.youtubeapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class SearchViewModel : ViewModel() {

    private val okHttpClient = OkHttpClient()
    private val _searchResult = MutableLiveData<List<VideoItem>>()
    val searchResult: LiveData<List<VideoItem>> = _searchResult

    private val tmpResult:MutableList<VideoItem> = mutableListOf()

    suspend fun search(key: String) {
        val obtainSearchHtml = withContext(Dispatchers.IO) {
            val request =
                Request.Builder().url("https://www.youtube.com/results?search_query=$key").build()
            try {
                okHttpClient.newCall(request).execute().body?.byteStream()?.reader()?.readText()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        Log.d("Test", "")


        val videoItems =
            Regex("\\{\"videoRenderer\":\\{\"videoId\":\"\\w*\"").findAll(obtainSearchHtml.toString())
                .map {
                    val id = obtainSearchHtml!!.substring(it.range).substringAfter(":\"")
                        .substringBefore("\"")
                    val thumbnail =
                        obtainSearchHtml.substring(it.range.last + 1).substringAfter("\"url\":\"")
                            .substringBefore("\"")
                    val title = obtainSearchHtml.substring(it.range.last + 1)
                        .substringAfter("\"title\":{\"runs\":[{\"text\":\"").substringBefore("\"")
                    VideoItem(id, thumbnail, title)
                }.distinct()


        _searchResult.value = videoItems.toList()
    }
}
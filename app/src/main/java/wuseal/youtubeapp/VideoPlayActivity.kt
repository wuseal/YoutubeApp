package wuseal.youtubeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

class VideoPlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        supportActionBar?.hide()
        val videoItem: VideoItem = intent.getSerializableExtra("videoItem") as VideoItem
        val webView = findViewById<WebView>(R.id.web_view)
        val divide = resources.displayMetrics.density
        webView.settings.javaScriptEnabled = true
        lifecycleScope.launchWhenResumed {
            delay(1)
            val playCode ="""<iframe width="${webView.width/divide}" height="${webView.height/divide}" src="https://www.youtube.com/embed/${videoItem.id}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>"""
            Log.d("Test",playCode)
            webView.run {
                loadData(playCode, "text/html", null);
            }
        }
    }
}
package wuseal.youtubeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var adapter: VideoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.edt_search)
        findViewById<Button>(R.id.btn_search).setOnClickListener {
            lifecycleScope.launchWhenCreated {
                Toast.makeText(this@MainActivity, "startLoad...", Toast.LENGTH_SHORT).show()
                viewModel.search(editText.text.toString())
                Toast.makeText(this@MainActivity, "Load OK", Toast.LENGTH_SHORT).show()
            }
        }
        val listView = findViewById<ListView>(R.id.lv_search_result)
        viewModel.searchResult.observe(this, {
            adapter = VideoListAdapter(it)
            listView.adapter = adapter
        })
        listView.setOnItemClickListener { adapterView, view, i, l ->
            (adapter.getItem(i) as VideoItem).run {
                startActivity(Intent(this@MainActivity, VideoPlayActivity::class.java).apply {
                    putExtra("videoItem", this@run)
                })
            }
        }
    }
}
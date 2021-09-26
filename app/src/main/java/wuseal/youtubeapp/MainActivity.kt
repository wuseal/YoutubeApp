package wuseal.youtubeapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
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
        val progress = findViewById<ProgressBar>(R.id.pb_progress)
        findViewById<Button>(R.id.btn_search).setOnClickListener {
            hideKeyboard(it)
            lifecycleScope.launchWhenCreated {
                progress.visibility = View.VISIBLE
                viewModel.search(editText.text.toString())
                progress.visibility = View.GONE
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

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
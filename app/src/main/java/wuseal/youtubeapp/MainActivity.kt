package wuseal.youtubeapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<SearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn_search).setOnClickListener {
            lifecycleScope.launchWhenCreated {
                Toast.makeText(this@MainActivity, "startLoad...", Toast.LENGTH_SHORT).show()
                viewModel.search("Hello")
                Toast.makeText(this@MainActivity, "Load OK", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
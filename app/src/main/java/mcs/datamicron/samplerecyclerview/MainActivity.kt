package mcs.datamicron.samplerecyclerview

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import mcs.datamicron.samplerecyclerview.data.models.ListItem
import mcs.datamicron.samplerecyclerview.ui.main_activity.MainListAdapter
import mcs.datamicron.samplerecyclerview.utils.Utils

class MainActivity : AppCompatActivity() {
    val TAG = "##MainActivity"

    lateinit var adapter: MainListAdapter
    var items: MutableList<ListItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                statusBarColor = getColor(R.color.mainBGColor)
            }
        }
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()

        val jsonFileString: String? = Utils.getJsonFromAssets(applicationContext, "MOCK_DATA.json")
        val gson = Gson()
        val itemsType = object : TypeToken<MutableList<ListItem>>() {}.type

        items = gson.fromJson(jsonFileString, itemsType)
        items.forEachIndexed { index, listItem ->
            Log.i(TAG, "> Item $index:\n${listItem.title}")
        }

        adapter = MainListAdapter(this, items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
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
        setContentView(R.layout.activity_main)

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
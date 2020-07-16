package mcs.datamicron.samplerecyclerview

import android.content.Context
import android.os.Build
import android.os.Bundle
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
    var isDark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonFileString: String? = Utils.getJsonFromAssets(applicationContext, "MOCK_DATA.json")
        val itemsType = object : TypeToken<MutableList<ListItem>>() {}.type

        items = Gson().fromJson(jsonFileString, itemsType)

        isDark = getSavedDarkMode()
        setDarkTheme(isDark)

        adapter = MainListAdapter(this, items, isDark)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !fabSwitch.isShown)
                    fabSwitch.show()
                else if (dy > 0 && fabSwitch.isShown)
                    fabSwitch.hide()
            }
        })

        fabSwitch.setOnClickListener {
            isDark = !isDark
            setDarkTheme(isDark)
            adapter.setDark(isDark)
            adapter.notifyDataSetChanged()
            saveDarkMode(isDark)
        }
    }

    private fun setDarkTheme(isDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rootLayout.setBackgroundColor(getColor(if (isDark) R.color.mainBGNightColor else R.color.mainBGColor))
            window.statusBarColor =
                getColor(if (isDark) R.color.mainBGNightColor else R.color.mainBGColor)
        }
    }

    private fun saveDarkMode(state: Boolean) {
        val sharedPreferences =
            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isDark", state)
        editor.apply()
    }

    private fun getSavedDarkMode(): Boolean {
        val sharedPreferences =
            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isDark", isDark)
    }
}
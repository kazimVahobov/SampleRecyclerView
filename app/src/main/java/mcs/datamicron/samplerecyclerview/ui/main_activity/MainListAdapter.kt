package mcs.datamicron.samplerecyclerview.ui.main_activity

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import mcs.datamicron.samplerecyclerview.R
import mcs.datamicron.samplerecyclerview.data.models.ListItem
import java.util.*

class MainListAdapter(
    private val context: Context,
    private val list: MutableList<ListItem>, _isDark: Boolean
) :
    RecyclerView.Adapter<MainListAdapter.ViewHolder>(), Filterable {

    private var isDark: Boolean = false
    private var filteredList: MutableList<ListItem> = arrayListOf()

    init {
        setDark(_isDark)
        filteredList = list
    }

    fun setDark(state: Boolean) {
        isDark = state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]
        holder.relativeLayout.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)

        holder.title.text = item.title
        holder.content.text = item.content
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = item.time
        val date: String = DateFormat.format("dd-MM-yyyy", cal).toString()
        holder.time.text = date
        holder.avatarCardView.animation =
            AnimationUtils.loadAnimation(context, R.anim.fade_transition)
        Glide.with(context).load(item.avatar).transform(CircleCrop())
            .into(holder.avatar)

        holder.relativeLayout.setBackgroundResource(if (isDark) R.drawable.card_bg_night else R.drawable.card_bg)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = itemView.findViewById(R.id.titleTV)
        var content: TextView = itemView.findViewById(R.id.contentTV)
        var time: TextView = itemView.findViewById(R.id.dateTV)
        var avatar: ImageView = itemView.findViewById(R.id.avatar)
        var avatarCardView: CardView = itemView.findViewById(R.id.avatarCardView)
        var relativeLayout: RelativeLayout = itemView.findViewById(R.id.relativeLayout)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults? {
                val key = constraint.toString()
                filteredList = if (key.isEmpty()) list
                else {
                    val filtered: MutableList<ListItem> = arrayListOf()
                    list.forEach {
                        if (it.title.toLowerCase().contains(key.toLowerCase())) {
                            filtered.add(it)
                        }
                    }
                    filtered
                }
                val result = FilterResults()
                result.values = filteredList
                return result
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                if (results.values is MutableList<*>)
                filteredList = results.values as MutableList<ListItem>
                notifyDataSetChanged()
            }
        }
    }
}
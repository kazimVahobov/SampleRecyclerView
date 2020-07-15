package mcs.datamicron.samplerecyclerview.ui.main_activity

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import mcs.datamicron.samplerecyclerview.R
import mcs.datamicron.samplerecyclerview.data.models.ListItem
import java.util.*

class MainListAdapter(
    private val context: Context,
    private val list: MutableList<ListItem>
) :
    RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
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
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = itemView.findViewById(R.id.titleTV)
        var content: TextView = itemView.findViewById(R.id.contentTV)
        var time: TextView = itemView.findViewById(R.id.dateTV)
        var avatar: ImageView = itemView.findViewById(R.id.avatar)
        var avatarCardView: CardView = itemView.findViewById(R.id.avatarCardView)
        var relativeLayout: RelativeLayout = itemView.findViewById(R.id.relativeLayout)
    }
}
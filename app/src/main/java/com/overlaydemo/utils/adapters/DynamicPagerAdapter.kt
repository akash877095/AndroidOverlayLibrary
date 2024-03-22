import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overlaydemo.R

class DynamicPagerAdapter(private val layoutCount: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val VIEW_TYPE_DUMMY = 0

        private const val VIEW_TYPE_NORMAL = 1

    }

    override fun getItemCount(): Int {
        return layoutCount + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_DUMMY else VIEW_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_DUMMY -> {
                val dummyView =
                    inflater.inflate(R.layout.dummy_view_for_first_item, parent, false)
                DummyViewHolder(dummyView)
            }
            VIEW_TYPE_NORMAL -> {
                val itemView =
                    inflater.inflate(R.layout.second_item_layout, parent, false)
                ViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            // TODO: Implement your logic for normal items
        }
    }

    inner class DummyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
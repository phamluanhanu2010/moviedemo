package luanpv.example.movieapp.ui.activities.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import luanpv.example.movieapp.R
import luanpv.example.movieapp.data.entities.response.MovieItemResponse

class MovieItemAdapter(
    val listOrig: List<MovieItemResponse>,
    val onClick: (MovieItemResponse) -> Unit,
) :
    RecyclerView.Adapter<MovieItemAdapter.ViewHolder>() {
    val list = arrayListOf<MovieItemResponse>()

    init {
        list.addAll(listOrig)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int =
        list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvTitle.text = item.Title
        holder.tvYear.text = "Year: " + item.Year
        Picasso.get().load(item.Poster).placeholder(R.drawable.ic_placeholder).fit().into(holder.imgPoster)
    }

    fun addData(items: List<MovieItemResponse>) {
        list.addAll(items)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val tvTitle: TextView by lazy {
            view.findViewById(R.id.tv_title) as TextView
        }

        val tvYear: TextView by lazy {
            view.findViewById(R.id.tv_year) as TextView
        }
        val imgPoster: ImageView by lazy {
            view.findViewById(R.id.img_poster) as ImageView
        }
    }
}
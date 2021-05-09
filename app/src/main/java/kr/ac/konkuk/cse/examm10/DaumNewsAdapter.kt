package kr.ac.konkuk.cse.examm10

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.FragmentItemBinding


class DaumNewsAdapter(
    val newsList: ArrayList<DaumNews>
) : RecyclerView.Adapter<DaumNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = newsList[position]
        holder.binding.apply {
            content.text = item.title
        }
    }

    override fun getItemCount(): Int = newsList.size

    interface OnItemClickListener {
        fun OnItemClick(holder: ViewHolder, view: View, data: DaumNews, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: FragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.content.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, newsList[adapterPosition], adapterPosition)
            }
        }
    }
}
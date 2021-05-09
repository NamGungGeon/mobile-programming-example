package kr.ac.konkuk.cse.exam7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.cse.databinding.ListDataBinding

class MyDataAdapter(val items: ArrayList<MyData>) :
    RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClicked(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(holder: ViewHolder, view: View, data: MyData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = ListDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)

//        val view= LayoutInflater.from(parent.context).inflate(R.layout.list_data, parent, false)
//        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            appclass.text = item.appclass
            applabel.text = item.applabel
            appicon.setImageDrawable(item.appicon)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun moveItem(oldPos: Int, newPos: Int) {
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)

        this.notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos: Int) {
        items.removeAt(pos)
        this.notifyItemRemoved(pos)
    }
}
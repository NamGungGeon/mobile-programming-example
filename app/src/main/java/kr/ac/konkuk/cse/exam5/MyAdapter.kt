package kr.ac.konkuk.cse.exam5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.cse.R

class MyAdapter(private val items:ArrayList<MyData>):RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView= itemView.findViewById<TextView>(R.id.row_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= items[position]
        holder.textView.text= item.name
        holder.textView.textSize= item.pt.toFloat()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
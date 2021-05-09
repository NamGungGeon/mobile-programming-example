package kr.ac.konkuk.cse.exam6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.cse.R

class MyWordAdapter(private val items:ArrayList<MyWord>):RecyclerView.Adapter<MyWordAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView= itemView.findViewById<TextView>(R.id.row_text)
        val secretTextView= itemView.findViewById<TextView>(R.id.row_secret)
        init{
            itemView.setOnClickListener{
                val secretState= secretStateList[adapterPosition]
                secretStateList[adapterPosition]= !secretState
                secretTextView.visibility= if(!secretState) View.GONE else View.VISIBLE
                itemClickListener?.onItemClicked(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(holder:ViewHolder, view:View, data:MyWord, position:Int)
    }

    var secretStateList:ArrayList<Boolean> = ArrayList<Boolean>()

    init{
        for (i in 0..items.size){
            secretStateList.add(true)
        }
    }
    var itemClickListener:OnItemClickListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.list_word, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= items[position]
        holder.textView.text= item.word
        holder.secretTextView.text= item.meaning
        holder.secretTextView.visibility= if(secretStateList[position]) View.GONE else View.VISIBLE
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun moveItem(oldPos:Int, newPos:Int){
        val item= items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)

        this.notifyItemMoved(oldPos, newPos)
    }
    fun removeItem(pos: Int){
        items.removeAt(pos)
        this.notifyItemRemoved(pos)
    }
}
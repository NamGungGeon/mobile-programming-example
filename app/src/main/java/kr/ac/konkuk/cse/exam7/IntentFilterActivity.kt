package kr.ac.konkuk.cse.exam7

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.cse.databinding.ActivityWordMasterBinding

class IntentFilterActivity : AppCompatActivity() {

    lateinit var binding: ActivityWordMasterBinding
    lateinit var adapter: MyDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecylcerView()
    }


    private fun initRecylcerView() {
        binding.wordList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyDataAdapter(ArrayList<MyData>())

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val applist = packageManager.queryIntentActivities(intent, 0)
        if (applist.size > 0) {
            for (appInfo in applist) {
                val applabel = appInfo.loadLabel(packageManager)
                val appclass = appInfo.activityInfo.name
                val apppackname = appInfo.activityInfo.packageName
                val appicon = appInfo.loadIcon(packageManager)

                adapter.items.add(MyData(applabel.toString(), appclass, apppackname, appicon))
            }
            adapter.notifyDataSetChanged()
        }

        adapter.itemClickListener = object : MyDataAdapter.OnItemClickListener {
            override fun onItemClicked(
                holder: MyDataAdapter.ViewHolder,
                view: View,
                data: MyData,
                position: Int
            ) {
                val intent = packageManager.getLaunchIntentForPackage(data.appackname)
                startActivity(intent)
            }
        }
        binding.wordList.adapter = adapter
        val simpleCB = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCB)
        itemTouchHelper.attachToRecyclerView(binding.wordList)
    }
}
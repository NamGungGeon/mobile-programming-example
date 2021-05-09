package kr.ac.konkuk.cse.exam5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kr.ac.konkuk.cse.R

class MyRecyclerActivity : AppCompatActivity() {
    var data= ArrayList<MyData>()
    lateinit var recylerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_recycler)

        init()
        initRecylcerView()
    }

    private fun initRecylcerView(){
        recylerView= findViewById<RecyclerView>(R.id.recyclerview)
        recylerView.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recylerView.adapter= MyAdapter(data)
    }

    private fun init(){
        data.add(MyData("item1", 10))
        data.add(MyData("item2", 20))
        data.add(MyData("item3", 30))
        data.add(MyData("item4", 40))
        data.add(MyData("item5", 50))
        data.add(MyData("item6", 60))
        data.add(MyData("item7", 70))
        data.add(MyData("item8", 80))
        data.add(MyData("item9", 90))
        data.add(MyData("item10", 100))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuitem1->{
                recylerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
            R.id.menuitem2->{
                recylerView.layoutManager= GridLayoutManager(this, 3)
            }
            R.id.menuitem3->{
                recylerView.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
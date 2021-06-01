package kr.ac.konkuk.cse.examm12

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kr.ac.konkuk.cse.databinding.ActivityProduct2Binding

class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProduct2Binding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyProductAdapter
    lateinit var rdb: DatabaseReference

    var findQuery: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProduct2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("Products/items")
        val query = rdb.limitToFirst(50)
        val option =
            FirebaseRecyclerOptions.Builder<Product>().setQuery(query, Product::class.java)
                .build()
        adapter = MyProductAdapter(option)
        adapter.itemClickListener = object : MyProductAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                binding.apply {
                    productId.setText(adapter.getItem(position).pId.toString())
                    productName.setText(adapter.getItem(position).pName)
                    productQuantity.setText(adapter.getItem(position).pQuantity.toString())
                }
            }
        }

        binding.apply {
            recyclerview.layoutManager = layoutManager
            recyclerview.adapter = adapter

            insertBtn.setOnClickListener {
                initAdapter()
                val item = Product(
                    productId.text.toString().toInt(),
                    productName.text.toString(),
                    productQuantity.text.toString().toInt()
                )
                rdb.child(item.pId.toString()).setValue(item)
                clearEditText()
            }
            findBtn.setOnClickListener {
                if (!findQuery)
                    findQuery = true

                if (adapter != null)
                    adapter.stopListening()

                val query = rdb.orderByChild("pName").equalTo(productName.text.toString())
                val option =
                    FirebaseRecyclerOptions.Builder<Product>().setQuery(query, Product::class.java)
                        .build()
                adapter = MyProductAdapter(option)
                adapter.itemClickListener = object : MyProductAdapter.OnItemClickListener {
                    override fun OnItemClick(view: View, position: Int) {
                        binding.apply {
                            productId.setText(adapter.getItem(position).pId.toString())
                            productName.setText(adapter.getItem(position).pName)
                            productQuantity.setText(adapter.getItem(position).pQuantity.toString())
                        }
                    }
                }
                recyclerview.adapter = adapter
                adapter.startListening()
                clearEditText()
            }
            deleteBtn.setOnClickListener {
                initAdapter()

                rdb.child(productId.text.toString()).removeValue()
                clearEditText()
            }
            updateBtn.setOnClickListener {
                initAdapter()

                rdb.child(productId.text.toString()).child("pQuantity")
                    .setValue(productQuantity.text.toString().toInt())
            }
        }
    }

    private fun initAdapter() {
        if (findQuery) {
            findQuery = false
            if (adapter != null)
                adapter.stopListening()

            val query = rdb.limitToFirst(50)
            val option =
                FirebaseRecyclerOptions.Builder<Product>().setQuery(query, Product::class.java)
                    .build()
            adapter = MyProductAdapter(option)
            adapter.itemClickListener = object : MyProductAdapter.OnItemClickListener {
                override fun OnItemClick(view: View, position: Int) {
                    binding.apply {
                        productId.setText(adapter.getItem(position).pId.toString())
                        productName.setText(adapter.getItem(position).pName)
                        productQuantity.setText(adapter.getItem(position).pQuantity.toString())
                    }
                }
            }

            binding.recyclerview.adapter = adapter
            adapter.startListening()
        }
    }

    private fun clearEditText() {
        binding.apply {
            productId.text.clear()
            productName.text.clear()
            productQuantity.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}

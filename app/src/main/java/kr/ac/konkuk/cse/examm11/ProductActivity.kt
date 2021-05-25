package kr.ac.konkuk.cse.examm11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.ActivityProductBinding
import java.io.FileOutputStream

class ProductActivity : AppCompatActivity() {
    lateinit var binding:ActivityProductBinding
    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDB()
        myDBHelper= MyDBHelper(this)
        init()
        getAllRecords()
    }
    private fun initDB(){
        val dbFile= getDatabasePath("mydb.db")
        if(!dbFile.parentFile.exists()){
            dbFile.parentFile.mkdir()
        }
        if(!dbFile.exists()){
            val file= resources.openRawResource(R.raw.mydb)
            val fileSize= file.available()
            val buf= ByteArray(fileSize)
            file.read(buf)
            file.close()

            dbFile.createNewFile()
            val output= FileOutputStream(dbFile)
            output.write(buf)
            output.close()
        }
    }
    fun init(){
        binding.apply {
            testsql.addTextChangedListener {
                val pname= it.toString()
                val result= myDBHelper.findProduct2(pname)
            }
            insertBtn.setOnClickListener {
                val name= productName.text.toString()
                val quantity= productQuantity.text.toString().toInt()
                val product= Product(0, name, quantity)
                val result= myDBHelper.insertProduct(product)
                if(result){
                    getAllRecords()
                    Toast.makeText(this@ProductActivity, "DATA INSERT SUCCESS", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ProductActivity, "DATA INSERT FAIL", Toast.LENGTH_SHORT).show()
                }
                clearEditText()
            }
            findBtn.setOnClickListener {
                val name= productName.text.toString()
                val result= myDBHelper.findProduct(name)

                if(result){
                    Toast.makeText(this@ProductActivity, "DATA FIND SUCCESS", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ProductActivity, "DATA FIND FAIL", Toast.LENGTH_SHORT).show()
                }
            }
            deleteBtn.setOnClickListener {
                val pid= productId.text.toString()
                val result= myDBHelper.deleteProduct(pid)

                if(result){
                    Toast.makeText(this@ProductActivity, "DATA DELETE SUCCESS", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ProductActivity, "DATA DELETE FAIL", Toast.LENGTH_SHORT).show()
                }
                getAllRecords()
                clearEditText()
            }
            updateBtn.setOnClickListener {
                val id= productId.text.toString().toInt()
                val name= productName.text.toString()
                val quantity= productQuantity.text.toString().toInt()
                val product= Product(id, name, quantity)
                val result= myDBHelper.updateProduct(product)
                if(result){
                    getAllRecords()
                    Toast.makeText(this@ProductActivity, "DATA UPDATE SUCCESS", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ProductActivity, "DATA UPDATE FAIL", Toast.LENGTH_SHORT).show()
                }
                clearEditText()
            }
        }
    }
    fun clearEditText(){
        binding.apply {
            productId.text.clear()
            productName.text.clear()
            productQuantity.text.clear()
        }
    }

    fun getAllRecords(){
        myDBHelper.getAll()
    }

}
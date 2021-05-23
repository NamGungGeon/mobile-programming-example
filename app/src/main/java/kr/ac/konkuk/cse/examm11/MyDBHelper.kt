package kr.ac.konkuk.cse.examm11

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView

class MyDBHelper(
    val context: Context,
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "mydb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "products"
        val PID = "pid"
        val PNAME = "pname"
        val PQUANTITY = "pquantity"
    }

    fun getAll() {
        val strSql = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(strSql, null)
        showRecord(cursor)
        cursor.close()
        db.close()
    }

    private fun showRecord(cursor: Cursor) {
        cursor.moveToFirst()
        val attrCnt = cursor.columnCount
        val activity = context as ProductActivity
        activity.binding.tableLayout.removeAllViewsInLayout()

        val tablerow = TableRow(activity)
        val rowParam = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        tablerow.layoutParams = rowParam
        val viewParam = TableRow.LayoutParams(
            0, 100, 1f
        )
        for (i in 0 until attrCnt) {
            val textView = TextView(activity)
            textView.layoutParams = viewParam
            textView.text = cursor.getColumnName(i)
            textView.setBackgroundColor(Color.LTGRAY)
            textView.textSize = 15f
            textView.gravity = Gravity.CENTER
            tablerow.addView(textView)
        }
        activity.binding.tableLayout.addView(tablerow)

        //레코드 추가하기
        if (cursor.count == 0)
            return
        do {
            val row = TableRow(activity)
            row.layoutParams = rowParam
            row.setOnClickListener{
                for(i in 0 until attrCnt){
                    val textView= row.getChildAt(i) as TextView
                    when(textView.tag){
                        0-> activity.binding.productId.setText(textView.text)
                        1-> activity.binding.productName.setText(textView.text)
                        2-> activity.binding.productQuantity.setText(textView.text)
                    }
                }
            }
            for (i in 0 until attrCnt) {
                val textView = TextView(activity)
                textView.tag= i
                textView.layoutParams = viewParam
                textView.text = cursor.getString(i)
                textView.setBackgroundColor(Color.WHITE)
                textView.textSize = 15f
                textView.gravity = Gravity.CENTER
                row.addView(textView)
            }
            activity.binding.tableLayout.addView(row)
        } while (cursor.moveToNext())
    }

    fun findProduct(name: String): Boolean {
        val sql = "SELECT * FROM $TABLE_NAME WHERE $PNAME='$name'"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)

        val flag = cursor.count != 0
        if (flag) {
            showRecord(cursor)
        }
        cursor.close()
        db.close()

        return flag
    }

    fun insertProduct(product: Product): Boolean {
        val values = ContentValues()
        values.put(PNAME, product.pName)
        values.put(PQUANTITY, product.pQuantity)
        val db = writableDatabase
        return if (db.insert(TABLE_NAME, null, values) > 0) {
            db.close()
            true
        } else {
            db.close()
            false
        }
    }

    fun deleteProduct(pid: String):Boolean{
        val sql = "SELECT * FROM $TABLE_NAME WHERE $PID='$pid'"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)

        val flag = cursor.count != 0
        if (flag) {
            cursor.moveToFirst()
            db.delete(TABLE_NAME, "$PID=?", arrayOf(pid))
        }
        cursor.close()
        db.close()

        return flag
    }
    fun updateProduct(product: Product):Boolean{
        val pid= product.pId

        val sql = "SELECT * FROM $TABLE_NAME WHERE $PID='$pid'"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)

        val flag = cursor.count != 0
        if (flag) {
            cursor.moveToFirst()
            val values= ContentValues()
            values.put(PNAME, product.pName)
            values.put(PQUANTITY, product.pQuantity)
            db.update(TABLE_NAME, values, "$PID=?", arrayOf(pid.toString()))
        }
        cursor.close()
        db.close()

        return flag
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
                "$PID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$PNAME text," +
                "$PQUANTITY INTEGER);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "DROP TABLE IF EXISTS $TABLE_NAME"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

    fun findProduct2(name: String): Boolean {
        val sql = "SELECT * FROM $TABLE_NAME WHERE $PNAME LIKE '$name%'"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)

        val flag = cursor.count != 0
        if (flag) {
            showRecord(cursor)
        }
        cursor.close()
        db.close()

        return flag
    }


}
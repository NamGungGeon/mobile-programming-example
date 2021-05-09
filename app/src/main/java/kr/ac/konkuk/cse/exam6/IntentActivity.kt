package kr.ac.konkuk.cse.exam6

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kr.ac.konkuk.cse.databinding.ActivityIntentBinding

class IntentActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntentBinding
    val CALL_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun callAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE 권한이 사용되어야 합니다").setTitle("권한 허용")
            .setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    CALL_REQUEST
                )
            }
        builder.create().show()
    }

    fun callAction() {
        val number = Uri.parse("tel:010-1234-1234")
        val callIntent = Intent(Intent.ACTION_CALL, number)

        if (
            android.os.Build.VERSION.SDK_INT >= 23 &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callAlertDlg()
            return
        }
        startActivity(callIntent)
    }

    fun init() {
        with(binding) {
            callbtn.setOnClickListener {
                callAction()
            }
            msgBtn.setOnClickListener {
                val msg = Uri.parse("sms:010-12344-12344")
                val intent = Intent(Intent.ACTION_SENDTO, msg)
                startActivity(intent)
            }
            webBtn.setOnClickListener {
                val number = Uri.parse("https://www.naver.com")
                val intent = Intent(Intent.ACTION_VIEW, number)
                startActivity(intent)
            }
            mapBtn.setOnClickListener {
                val number = Uri.parse("geo:37.543684,127.077130?z=16")
                val intent = Intent(Intent.ACTION_VIEW, number)
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 승인", Toast.LENGTH_LONG).show()
                    callAction()
                } else {
                    Toast.makeText(this, "권한 거절", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
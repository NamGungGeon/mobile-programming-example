package kr.ac.konkuk.cse.examm14

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kr.ac.konkuk.cse.databinding.ActivityBroadcastBinding

class BroadcastActivity : AppCompatActivity() {
    lateinit var binding: ActivityBroadcastBinding
    lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPermission()
        init()

        if (intent.hasExtra("msg")) {
            val msg = intent.getStringExtra("msg")
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        //foreground receiver
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    if (intent.action.equals(Intent.ACTION_POWER_CONNECTED))
                        Toast.makeText(context, "배터리 충전 시작", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initPermission() {
        //API 29부터는 BroadcastReceiver에서 startActivity를 사용하기 위해 설정에서 OVERLAY 권한을 받아야 한다
        if (Build.VERSION.SDK_INT >= 29) {
            if (Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECEIVE_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "문자 수신 동의함", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.RECEIVE_SMS),
                100
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            if (intent.hasExtra("msg")) {
                val msg = intent.getStringExtra("msg")
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "문자 수신 동의함", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "문자 수신 동의 필요함", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }
}
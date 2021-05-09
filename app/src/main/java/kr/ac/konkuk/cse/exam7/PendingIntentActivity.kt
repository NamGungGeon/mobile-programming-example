package kr.ac.konkuk.cse.exam7

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.ActivityPendingIntentBinding
import kr.ac.konkuk.cse.databinding.DialogMypickerBinding
import java.util.*

class PendingIntentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPendingIntentBinding
    var myMemo = ""
    var myHour = 0
    var myMinute = 0
    var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPendingIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val str = intent.getStringExtra("time")
        if (str != null) {
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
        init()
    }

    private fun init() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dlgBinding = DialogMypickerBinding.inflate(layoutInflater)

            val dBuilder = AlertDialog.Builder(this, R.style.myDialog)
            dBuilder.setView(dlgBinding.root)
                .setPositiveButton("추가") { _, _ ->
                    myMemo = dlgBinding.pickerEditText.text.toString()
                    myHour = dlgBinding.timePicker.hour
                    myMinute = dlgBinding.timePicker.minute

                    message = myHour.toString() + "시 " + myMinute + "분 : " + myMemo

                    val timerTask = object : TimerTask() {
                        override fun run() {
                            makeNotification()
                        }
                    }
                    val timer = Timer()
                    timer.schedule(timerTask, 2000)
                    Toast.makeText(applicationContext, "알림이 추가되었음", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("취소") { _, _ ->
                }
                .show()
//                val dlg= dBuilder.create()
//                dlg.show()
        }

    }


    fun makeNotification() {
        //create notification Channel
        val nId = "MyChannel"
        val nName = "TimeCheckChannel"

        val nChannel = NotificationChannel(nId, nName, NotificationManager.IMPORTANCE_DEFAULT)
        nChannel.enableVibration(true)
        nChannel.enableLights(true)
        nChannel.lightColor = Color.BLUE
        nChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        //build notification
        val builder = NotificationCompat.Builder(this, nId)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentTitle("일정 알림")
            .setContentText(message)
            .setAutoCancel(true)

        val intent = Intent(applicationContext, PendingIntentActivity::class.java)
        intent.putExtra("time", message)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pIntent)

        val notification = builder.build()


        //register notification channel/notification to notification service
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(nChannel)

        val notificaitonId = 10
        manager.notify(notificaitonId, notification)
    }
}
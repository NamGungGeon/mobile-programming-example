package kr.ac.konkuk.cse.examm14

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyReceiver : BroadcastReceiver() {
    private val usagePattern = Regex("""^[가-힣a-zA-Z0-9]+$""")
    private val paymentPattern = Regex("""^*\d{3}""")
    private val dayPattern = Regex("""^\d{2}/d{2}}""")
    val scope= CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult= goAsync()
        scope.launch {
            if (intent.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
                val bundle = intent.extras
                val objects = bundle?.get("pdus") as Array<*>
                val sms = objects[0] as ByteArray
                val format = bundle.getString("format")
                val message = SmsMessage.createFromPdu(sms, format)
                var msg = message.messageBody
                if (msg.contains("건국카드")) {
                    val tempstr = msg.split("\n")
                    var resultstr = ""
                    for (str in tempstr.subList(1, tempstr.size)) {
                        if (usagePattern.containsMatchIn(str))
                            resultstr += "$str: "
                        else if (paymentPattern.containsMatchIn(str))
                            resultstr += "$str: "
                        else if (dayPattern.containsMatchIn(str)) {
                            val temp = str.split(" ")
                            val daytemp = temp[0].split("/")
                            val month = daytemp[0]
                            val day = daytemp[1]
                            resultstr += "${month}월 ${day}일 카드 승인됨"
                        }
                    }
                    if (resultstr.isNotEmpty()){
                        val newIntent = Intent(context, BroadcastActivity::class.java)
                        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        newIntent.putExtra("msg", resultstr)
                        //API29부터는 BoardcastReceiver가 startActivity 를 사용할 수 없다
                        context.startActivity(newIntent)
                    }
                    pendingResult.finish()
                }
            }
        }
    }
}
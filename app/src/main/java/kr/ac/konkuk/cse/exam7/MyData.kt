package kr.ac.konkuk.cse.exam7

import android.graphics.drawable.Drawable
import java.io.Serializable

class MyData(
    var applabel: String,
    var appclass: String,
    var appackname: String,
    var appicon: Drawable
) : Serializable {
}
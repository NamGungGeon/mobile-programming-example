package kr.ac.konkuk.cse.exam9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.ActivityMyFragmentBinding

class MyFragmentActivity : AppCompatActivity() {
    lateinit var binding:ActivityMyFragmentBinding
    val myViewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMyFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, ImageFragment()).commit()
    }
}
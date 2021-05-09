package kr.ac.konkuk.cse.exam91

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.ActivityFragmentWrapper2Binding

class FragmentWrapperActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityFragmentWrapper2Binding

    val texts= arrayListOf<String>("이미지", "리스트")
    val icons= arrayListOf<Int>(R.drawable.ic_baseline_sentiment_dissatisfied_24, R.drawable.ic_baseline_sentiment_satisfied_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityFragmentWrapper2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.viewPager.adapter= MyFragStateAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab,position->

            tab.text= texts[position]
            tab.setIcon(icons[position])
        }.attach()
    }
}
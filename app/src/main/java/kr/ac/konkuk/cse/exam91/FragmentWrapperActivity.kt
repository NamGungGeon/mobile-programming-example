package kr.ac.konkuk.cse.exam91

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.ActivityFragmentWrapperBinding

class FragmentWrapperActivity : AppCompatActivity() {
    lateinit var binding:ActivityFragmentWrapperBinding

    val imgFragment= ImageFragment()
    val itemFragment= ItemFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityFragmentWrapperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, imgFragment).commit()

        binding.apply {
            toImageBtn.setOnClickListener{
                if(!imgFragment.isVisible)
                    supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, imgFragment).commit()
            }
            toListBtn.setOnClickListener{
                if(!itemFragment.isVisible)
                    supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, itemFragment).commit()
            }
        }
    }
}
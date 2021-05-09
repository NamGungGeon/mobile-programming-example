package kr.ac.konkuk.cse.exam9

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.FragmentImageBinding

class ImageFragment : Fragment() {
    var binding: FragmentImageBinding? = null
    val mViewModel: MyViewModel by activityViewModels()
    val imgList = arrayListOf<Int>(R.drawable.image_1, R.drawable.image_2, R.drawable.image_3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.apply {
            imageView.setOnClickListener {
//                if(resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT){
//                    val i= Intent(activity, MySecondActivity::class.java)
//                    i.putExtra("imgNum", mViewModel.selectedNum.value)
//                    startActivity(i)
//                }
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, TextFragment()).commit()
            }
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioBtn1 -> {
                        mViewModel.setLiveData(0)
                    }
                    R.id.radioBtn2 -> {
                        mViewModel.setLiveData(1)
                    }
                    R.id.radioBtn3 -> {
                        mViewModel.setLiveData(2)
                    }
                }
                imageView.setImageResource(imgList[mViewModel.selectedNum.value!!])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
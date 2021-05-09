package kr.ac.konkuk.cse.exam9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.FragmentImageBinding
import kr.ac.konkuk.cse.databinding.FragmentTextBinding

class TextFragment : Fragment() {
    var binding: FragmentTextBinding? = null
    val mViewModel: MyViewModel by activityViewModels()
    val data= arrayListOf<String>("ImageData1", "ImageData2", "ImageData3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTextBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgNum= requireActivity().intent.getIntExtra("imgNum", -1)

        binding!!.apply {
            if(imgNum!= -1){
                myTextView.text=data[imgNum]
            }else{
                mViewModel.selectedNum.observe(viewLifecycleOwner, Observer {
                    myTextView.text= data[it]
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding= null
    }

}
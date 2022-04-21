package com.quantata.kotlinlearning.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.quantata.kotlinlearning.R
import com.quantata.kotlinlearning.databinding.FragmentBlueBinding

// Fragment - viewBinding 사용시 문제점
// https://yoon-dailylife.tistory.com/57
/** Fragment 에서 View Binding을 사용할 경우 Fragment 는 View 보다 오래 지속되어, Fragment 의 Lifecycle로 인해 메모리 누수가 발생할 수 있기 때문이다.
 * 예를 들어 Fragment 에서 Navigation component 또는 BacStack or Detach 를 사용하는 경우, onDestroyView() 이후에 Fragment view는 종료되지만, Fragment 는 여전히 살아 있다. -> 메모리 누수 발생
 * 그래서 반드시 binding 변수를 onDestroyView() 이후에 null 로 만들어 주어야 함.
*/
/**
 * 구글은 Fragment 재사용을 위해 View 들을 메모리에 보관하도록 Fragment 의 동작을 변경함.
 * 그래서 onDestroy() - onDestroyView() 가 호출되고 나서도 View Binding 에 대한 참조를 Garbage Collector 에서 Garbage Collection 을 명확하게 해 줄 필요가 있음.
 */
class BlueFragment : Fragment() {

    private var _binding: FragmentBlueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, // 뷰를 생성하는 객체
        container: ViewGroup?, // 생성할 뷰(자식 뷰)가 들어갈 부모 뷰
        savedInstanceState: Bundle? // 이전 프래그먼트 객체에서 전달된 데이터(번들)
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//        return inflater.inflate(R.layout.fragment_red, container, false)
        _binding = FragmentBlueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
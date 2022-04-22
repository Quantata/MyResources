package com.quantata.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.quantata.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener { // 클릭 이벤트 처리 인터페이스 = OnClickListener
    private lateinit var binding: ActivityMainBinding
    private var isRunning = false // 실행 여부 확인용 변수

    // Timer 관련 변수
    lateinit var timer : Timer
    var time = 0

    lateinit var startText : String

    /*
     * 2022.04.22 : 이렇게 text마다 변수를 선언하게되면 findViewById 랑 다를 것이 거의 없음.
     * 변경시 자동으로 업데이트 해주지만 변수는 계속 늘어남. 단순히 텍스트 외에 DTO, LiveData 등이랑 같이 연계되면 더 간단해지고 그땐 객체는 어차피 선언해줘야하니까 더 효율성이 높아져서 사용하는듯!
     * 그리고 그냥 binding.textView.text 로 해서 접근해서 업데이트 해주면 inValidateAll() 안하고 사용 가능 할 것 같음.
     */
    var milliSecText : String = ".00"
    var secondText : String = ":00"
    var minuteText : String = "00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        startText = getString(R.string.start) // 시작으로 설정

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start -> {
                if(isRunning) // 스톱워치 측정 중이 었다면
                    pause()
                else
                    start()
            }
            R.id.btn_refresh -> {
                if(!isRunning)
                    refresh()
            }
        }
    }

    private fun start() {
        // 스톱워치 측정 실행하는 로직
        startText = getString(R.string.pause)
        binding.invalidateAll() // UI 를 새로고침 하기 위해 모든 바인딩 표현식을 무효화하고 새로운 리바인드를 요청

        binding.btnStart.setBackgroundColor(getColor(R.color.light_red)) // 일시정지 버튼으로 변경
        binding.btnRefresh.setBackgroundColor(getColor(R.color.light_gray)) // 재설정 버튼 비활성화처럼 보이도록 함

        isRunning = true

        // 스톱워치 시작하는 로직
        timer = timer(period = 10) { // 이 {} 안에서는 Background Thread 에서 돌기 때문에 timer 갱신은 Main Thread 에서 해줘야 함.
            time++ // 10 milli second 단위 타이머

            // 시간 계산
            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000 //

            runOnUiThread { // UI Thread 생성
                if(isRunning) { // UI 업데이트 조건 설정
                    // 밀리초
                    milliSecText =
                        if (milli_second < 10) ".0${milli_second}" else ".${milli_second}"
                    // 초
                    secondText = if (second < 10) ":0${second}" else ":${second}"
                    // 분
                    minuteText = "$minute"
                    binding.invalidateAll() // 이 부분을 해줘야 변경된 Text 값을 업데이트 해
                }
            }
        }

    }

    private fun pause() {
        // 스톱워치 측정을 일시정지하는 로직
        isRunning = false // 멈춤 상태로 전환
        timer.cancel() // 타이머 멈추기

        startText = getString(R.string.start)
        binding.btnStart.setBackgroundColor(getColor(R.color.light_blue))
        binding.btnRefresh.setBackgroundColor(getColor(R.color.light_yellow)) // 재설정 버튼 활성화된 것 처럼 변경

        binding.invalidateAll()
    }

    // 재설정은 멈춤 상태에서만 할 수 있도록 변경
    private fun refresh() {
        // 초기화 하는 로직
        isRunning = false // 멈춤 상태로 전환
        timer.cancel() // 타이머 멈추기

        startText = getString(R.string.start)
        binding.btnStart.setBackgroundColor(getColor(R.color.light_blue))

        // 타이머 초기화
        time = 0
        milliSecText = ".00"
        secondText = ":00"
        minuteText = "00"

        binding.invalidateAll()
    }



}
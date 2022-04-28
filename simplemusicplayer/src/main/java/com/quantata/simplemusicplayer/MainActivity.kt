package com.quantata.simplemusicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.quantata.simplemusicplayer.databinding.ActivityMainBinding
import com.quantata.simplemusicplayer.service.MusicPlayerService

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var mService : MusicPlayerService? = null

    // 서비스와 구성요소 상태 모니터링
    // bindService() 함수를 사용할 때 두번째 인수로 들어감.
    val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = (service as MusicPlayerService.MusicPlayerBinder).getService() // MusicPlayerBinder 로 형변환
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null // 만약 service 가 끊긴다면 mService 를 null 로 만들어줌
       }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_play -> {
                play()
            }
            R.id.btn_pause -> { pause() }
            R.id.btn_stop -> { stop() }

        }
    }

    override fun onResume() {
        super.onResume()

        // Service 실행
        if(mService == null) { // mService 가 null 이면 '아직 Service 가 Activity 와 연결되지 않았다' 는 말이므로 startService() or startForegroundService() 함수 호출
            // startService() 나 startForegroundService() 는 이미 해당 Service 가 만들어진 상태라면 Service 를 새로만들지 않음
            // 안드로이드 O 이상이면 startForegroundService 사용해야 함
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, MusicPlayerService::class.java))
            } else {
                startService(Intent(applicationContext, MusicPlayerService::class.java))
            }

            // 액티비티를 서비스와 바인드시킵니다.
            val intent = Intent(this, MusicPlayerService::class.java)
            // 서비스와 바인드
            // bindService(어떤 Service 와 바인드 할지, 연결되면 onServiceConnected 를 연결이 끊기면 onServiceDisconnected() 를 실행, BIND_AUTO_CREATE=바인드 시점에 Service 가 실행되지 않았다면 Service 를 생성해라)
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        super.onPause()
        // 사용자가 액티비티를 떠났을때 처리
        if(mService != null) { // mService 가 null 이 아님 -> 바인드가 된 상태
            // 바인드가된 상태일때, 음악이 실행되지 않고 있는 상태라면 Service 종료 후 바인딩 해제,
                // 음악이 실행되는 중이라면 바인딩만 해제 후 다시 앱으로 돌아왔을 때 해당 서비스에 바인딩 될 수 있도록 함.
            if(!mService!!.isPlaying()) { // mService 가 재생되고 있지 않다면
                mService!!.stopSelf() // Service 종료
            }
            unbindService(mServiceConnection) // 서비스로부터 연결을 끊습니다. = 바인드 해제
            mService = null
        }
    }

    private fun play() {
        mService?.play()
    }

    private fun pause() {
        mService?.pause()
    }

    private fun stop() {
        mService?.stop()
    }


}
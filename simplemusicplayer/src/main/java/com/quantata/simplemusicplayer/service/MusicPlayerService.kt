package com.quantata.simplemusicplayer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import com.quantata.simplemusicplayer.R

class MusicPlayerService : Service() {

    var mMediaPlayer: MediaPlayer? = null // 미디어 플레이어 객체를 null 로 초기화
    var mBinder: MusicPlayerBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder : Binder() { // 바인더를 반환해 서비스 함수를 사용할 수 있게 함.
        fun getService(): MusicPlayerService {
            return this@MusicPlayerService
        }
    }

    override fun onCreate() { // 서비스가 생성될 때, 딱 한번만 실행
        super.onCreate()
        startForegroundService() // 포그라운드 서비스 시작
    }
    override fun onBind(intent: Intent): IBinder {
        return mBinder // 바인더 반환
    }

    // startService() 를 호출하면 실행되는 콜백 함수
    // 시작된 상태 & 백그라운드
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        return super.onStartCommand(intent, flags, startId)
        return START_STICKY
        /**
         * START_STICKY : 시스템이 서비스를 중단하면 서비스를 다시 실행하고, onStartCommand() 함수 호출
         * START_NOT_STICKY : 시스템이 서비스를 중단시키면 서비스를 재생성하지 않습니다.
         * START_REDELIVER_INTENT : 시스템이 서비스를 중단하면 서비스를 다시 실행하고 onStartCommand() 함수를 호출.
         * 또한, 서비스가 종료되기 전 마지막으로 전달된 Intent 를 재전달. 반드시 명령을 실행해야 하는 경우에 사용.
         */
    }

    // 서비스 종료
    override fun onDestroy() {
        super.onDestroy()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
    }

    // 알림 채널을 만들고 startForeground() 함수 실행.
    // API Level 26 버전부터는 반드시 startService 가 아닌 startForegroundService() 를 실행하여 사용자로 하여금 서비스가 실행되고 있다는 사실을 알려줘야 함.
    fun startForegroundService() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val mChannel =
                NotificationChannel( // 알림 채널 생성 : 알림의 중요도와 용도에 따라 구분하여 사용자가 앱의 알림을 관리할 수 있게 해야 함.
                    "CHANNEL_ID",
                    "CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(mChannel)

            // 알림 생성
            val notification: Notification = Notification.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_play)   // 알림 아이콘
                .setContentTitle("뮤직 플레이어 앱")    // 알림의 제목 설정
                .setContentText("앱이 실행 중입니다.")   // 알림의 내용 설정
                .build()

            startForeground(1, notification) // 임수로 알림 ID 와 알림 지정 = (일림의 식별자, 보여줄 알림)
        }
    }

    // 재생 중인지 확인
    fun isPlaying() : Boolean {
        return (mMediaPlayer != null && mMediaPlayer?.isPlaying ?: false)
    }

    // 음악 재생
    fun play() {
        if(mMediaPlayer == null) { // 음악이 재생중이지 않은 경우
            // 음악 파일의 리소스를 가져와 미디어 플레이어 객체를 할당해 줍니다.
            mMediaPlayer = MediaPlayer.create(this, R.raw.chocolate)

            mMediaPlayer?.setVolume(1.0f, 1.0f) // 볼륨 지정
            mMediaPlayer?.isLooping = true // 반복재생 여부
            mMediaPlayer?.start() // 음악을 재생
        } else { // 음악 재생중인 경우
            if(mMediaPlayer!!.isPlaying) { // 음악이 플레이되는 경우
                Toast.makeText(this, "음악이 이미 재생중 입니다.", Toast.LENGTH_SHORT).show()
            } else { // 일시정지 상태인 경우
                mMediaPlayer?.start() // 음악을 재생합니다.
            }
        }
    }

    // 일시 정지
    fun pause() {
        mMediaPlayer?.let {
            if (it.isPlaying) {
                it.pause() // 음악을 일시 정지 함.
            }
        }
    }

    // 제셍 증지
    fun stop() {
        mMediaPlayer?.let {
            if (it.isPlaying) {
                it.stop() // 음악을 멈춤
                it.release() // 미디어 플레이어에 할당된 자원을 해제
                mMediaPlayer = null
            } else { // 일시 정지상태인 경우
                it.stop() // 음악을 멈춤
                it.release() // 미디어 플레이어에 할당된 자원을 해제
                mMediaPlayer = null
            }
        }
    }
}
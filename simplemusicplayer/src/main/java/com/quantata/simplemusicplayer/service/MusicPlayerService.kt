package com.quantata.simplemusicplayer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MusicPlayerService : Service() {

    override fun onCreate() { // 서비스가 생성될 때, 딱 한번만 실행
        super.onCreate()
        startForegroundService() // 포그라운드 서비스 시작
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    // 시작된 상태 & 백그라운드
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    // 서비스 종료
    override fun onDestroy() {
        super.onDestroy()
    }

    // 알림 채널을 만들고 startForeground() 함수 실행.
    // API Level 26 버전부터는 반드시 startService 가 아닌 startForegroundService() 를 실행하여 사용자로 하여금 서비스가 실행되고 있다는 사실을 알려줘야 함.
    fun startForegroundService() {}

    fun isPlaying() {} // 재생 중인지 확인

    fun play() {} // 재생

    fun pause() {} // 일시 중지

    fun stop() {} // 완전 정지
}
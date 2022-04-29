package com.quantata.qrcodereader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.quantata.qrcodereader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // 바인딩 변수 생성

    /* ListenableFuture 형 변수 선언.
     * ListenableFuture 에 Task 가 제대로 끝났을 때, 동작을 지정해줄 수 있음.
     * (참고로, Future 는 안드로이드의 병렬 프로그래밍에서 Task 가 제대로 끝났는지 확인할 때 사용
     */
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()
    }

    // 미리보기와 이미지 분석 시작
    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this) // cameraProviderFuture 에 객체의 참조값을 할당
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get() // 카메라의 생명주기를 Activity 나 Fragment 와 같은 생명주기에 바인드해주는 ProcessCameraProvider 객체를 가져옵니다.

            val preview = getPreview() // 미리보기 객체를 가져옴.
            /*
             * 후면 카메라 : DEFAULT_BACK_CAMERA
             * 전면 카메라 : DEFAULT_FRONT_CAMERA
             */
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // 후면 카메라 선택

            cameraProvider.bindToLifecycle(this, cameraSelector, preview) // 미리보기, 이미지분석, 이미지 캡쳐 중 무엇을 사용할지 선택. 하나 이상 선택 가능. 일단 미리보기(preview) 만 삽입

        }, ContextCompat.getMainExecutor(this)) // cameraProviderFuture Task 가 끝나면 실행된다.

    }

    // 미리보기 객체 변환
    private fun getPreview() : Preview {
        val preview: Preview = Preview.Builder().build() // Preview 객체 생성
        /* setSurfProvider 함수는 Preview 객체에 SurfaceProvider 를 설정해줌
         * SurfaceProvider 는 Preview 에 Surface 를 제공해주는 Interface
         * -> 화면에 보여지는 pixel 들이 모여있는 객체가 Surface
         */
        preview.setSurfaceProvider(binding.barcodePreview.surfaceProvider)

        return preview
    }
}
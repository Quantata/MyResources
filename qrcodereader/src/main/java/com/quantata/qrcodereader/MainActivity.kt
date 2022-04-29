package com.quantata.qrcodereader

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.quantata.qrcodereader.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // 바인딩 변수 생성

    /* ListenableFuture 형 변수 선언.
     * ListenableFuture 에 Task 가 제대로 끝났을 때, 동작을 지정해줄 수 있음.
     * (참고로, Future 는 안드로이드의 병렬 프로그래밍에서 Task 가 제대로 끝났는지 확인할 때 사용
     */
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    // permission 관련
    private val PERMISSIONS_REQUEST_CODE = 1 // 태그 기능을 하는 코드. 일종의 이름표. 나중에 요청한 결과를 받을때(onRequestPermissionResult 에서) 필요. 0 이상이 숫자이기만 하면 됨.
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA) // 카메라 권한 지정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!hasPermission(this)) {
            // 권한이 없다면 권한을 요청합니다.
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        } else {
            startCamera()
        }
    }

    // 권한 유무 확인
    private fun hasPermission(context: Context) = PERMISSIONS_REQUIRED.all {
        // ContextCompat 은 Resource 에서 값을 가져오거나 Permission 을 확인할 때 사용하는데 이때, SDK 버전을 고려하지 않아도 되도록 내부적으로 SDK 버전을 처리해둔 클래스.
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // requestPermission 의 인수로 넣은 PERMISSION_REQUEST_CODE 와 맞는지 확인
        if(requestCode == PERMISSIONS_REQUEST_CODE) {
            // 권한이 수락되면 startCamera() 를 호출하고 권한이 거부되면 Activity 종료
            if(PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) { // empty 이면 null, 아니면 array 의 첫번째 항목 가져오기
                Toast.makeText(this@MainActivity, "권한 요청이 승인되었습니다.", Toast.LENGTH_SHORT).show()
                startCamera()
            } else {
                Toast.makeText(this@MainActivity, "권한 요청이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    // Getting image analysis result
    private fun getImageAnalysis() : ImageAnalysis {
        val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
        val imageAnalysis = ImageAnalysis.Builder().build()


        imageAnalysis.setAnalyzer(cameraExecutor,
                // QRCodeAnalyzer 객체를생성 setAnalyzer() 함수의 인수로 넣어줌.
                QRCodeAnalyzer(object : OnDetectListener { // object 를 통해 인터페이스 객체 만들어 주고, onDetect() 함수 오버라이드
                    override fun onDetect(msg: String) {
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }))

        return imageAnalysis
    }

    // 미리보기와 이미지 분석 시작
    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this) // cameraProviderFuture 에 객체의 참조값을 할당
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get() // 카메라의 생명주기를 Activity 나 Fragment 와 같은 생명주기에 바인드해주는 ProcessCameraProvider 객체를 가져옵니다.

            val preview = getPreview() // 미리보기 객체를 가져옴.
            val imageAnalysis = getImageAnalysis() // ImageAnalysis 객체 생성
            /*
             * 후면 카메라 : DEFAULT_BACK_CAMERA
             * 전면 카메라 : DEFAULT_FRONT_CAMERA
             */
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // 후면 카메라 선택

            // 미리보기, 이미지분석, 이미지 캡쳐 중 무엇을 사용할지 선택. 하나 이상 선택 가능. 일단 미리보기(preview) 만 삽입 -> 이미지 분석(imageAnalysis)도 삽입
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)

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
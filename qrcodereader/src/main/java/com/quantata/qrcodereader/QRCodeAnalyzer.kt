package com.quantata.qrcodereader

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

/*
 * MainActivity 에서 QR 코드 데이터를 인식하려면 QRCodeAnalyzer 객체를 생성할 때 OnDetectListener 를 구현하여 주 생성자의 인수로 넘겨줘야 함.
 * QRCodeAnalyzer 에서는 이 리스너를 통하여 MainActivity 와 소통할 수 있게 됨!!
 */
class QRCodeAnalyzer(val onDetectListener: OnDetectListener) : ImageAnalysis.Analyzer {

    // 바코드 스캐닝 객체 생성
    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if(mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees) // 이미지가 찍힐 당시 카메라의 회전 각도를 고려하여 입력이미지를 생성

            scanner.process(image)      // scanner.process(image) 를 통해 이미지 분석
                .addOnSuccessListener { qrCodes ->
                    // qrCodes 와 같은 배열이 넘어오는 이유는 한 화면에 다수의 QR Code 가 찍히게 되면 모든 QR Code 를 분석해 각각 배열로 보내기 떼문
                    // 리스너가 들어갈 자리
                    for(qrCode in qrCodes) {
                        onDetectListener.onDetect(qrCode.rawValue ?: "")
                    }
                }
                .addOnFailureListener{
                    // Writing the log when it is failure
                    it.printStackTrace()
                }
                .addOnCompleteListener {
                    // close the proxy when it is completed.
                    imageProxy.close()
                }
        }
    }
}
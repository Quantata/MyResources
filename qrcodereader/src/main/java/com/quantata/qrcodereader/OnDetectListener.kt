package com.quantata.qrcodereader

interface OnDetectListener {
    fun onDetect(msg: String) // onDetect() 함수는 QRCodeAnalyzer 에서 QR 코드가 인식되었을 때 호출할 함수로서 데이터 내용(=msg)을 인수로 받습니다.
}
package com.jocoos.flipflop.media.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jocoos.flipflop.media.FlipFlopMedia
import com.jocoos.flipflop.media.api.FFRTC
import com.jocoos.flipflop.media.api.FFRTCListener
import kotlinx.android.synthetic.main.activity_rtc.*

class ReceiverActivity : AppCompatActivity(), FFRTCListener {

    private val rtc = FlipFlopMedia.getRTC()
    private val myID = System.currentTimeMillis()

    private var roomID: Long = 0 // 방송하기에서 만들어진 roomID로 바꿔준다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rtc)

        rtc.listener = this

        // 준비
        rtc.prepare(this, preview, null)
    }

    override fun onStop() {
        super.onStop()
        rtc.reset()
    }

    // prepare 성공 후
    override fun onPrepared(rtc: FFRTC) {
        //서버에 접속
        rtc.connect()
    }
    // 서버와 접속이 성공 했을때
    override fun onConnected(rtc: FFRTC) {
        // 방에 접속한다.
        rtc.join(roomID, myID)
    }
    // 서버 접속이 끊어 졌을때
    override fun onDisconnected(rtc: FFRTC) {

    }
    // 방 생성이 성공 했을때
    override fun onRoomCreated(rtc: FFRTC, roomID: Long) {


    }
    // 방 삭제가 성공 했을때
    override fun onRoomDeleted(rtc: FFRTC, roomID: Long) {

    }
    // 에러가 났을때
    override fun onError(rtc: FFRTC, errorCode: Int, error: String) {
        Toast.makeText(this, "errorCode: $errorCode error: $error", Toast.LENGTH_LONG).show()
    }
}
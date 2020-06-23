package com.jocoos.flipflop.media.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jocoos.flipflop.media.FlipFlopMedia
import com.jocoos.flipflop.media.api.FFConference
import com.jocoos.flipflop.media.api.FFConferenceListener
import com.jocoos.flipflop.media.view.FFView
import kotlinx.android.synthetic.main.activity_conference.*

class ConferenceSubscriberActivity : AppCompatActivity(), FFConferenceListener {
    private val conference = FlipFlopMedia.getConference()
    private val myID = System.currentTimeMillis()

    private var roomID: Long = 0 // 화상회의 시작하기에서 만들어진 roomID로 바꿔준다.

    private var availableViews: ArrayList<FFView> = arrayListOf()
    private var usingViews: MutableMap<Long, FFView> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conference)

        availableViews = arrayListOf(playerView2, playerView3, playerView4)

        conference.listener = this

        // 준비
        conference.prepare(this, playerView1, null)
    }

    override fun onStop() {
        super.onStop()

        // 리소스를 해제한다.
        conference.reset()
    }
    // prepare 성공 후
    override fun onPrepared(conference: FFConference) {
        // 서버에 접속
        conference.connect()
    }
    // 서버와 접속이 성공 했을때
    override fun onConnected(conference: FFConference) {
        // 방에 접속한다.
        conference.join(roomID, myID)
    }
    // 서버 접속이 끊어 졌을때
    override fun onDisconnected(conference: FFConference) {

    }
    // 방 생성이 성공 했을때
    override fun onRoomCreated(conference: FFConference, roomID: Long) {

    }
    // 방 삭제가 성공 했을때
    override fun onRoomDeleted(conference: FFConference, roomID: Long) {

    }
    // 유저가 방에 참여했을때
    override fun onJoined(conference: FFConference, roomID: Long, userID: Long): FFView? {

        val v = availableViews.removeAt(0)
        usingViews[userID] = v
        return v
    }
    // 유저가 방에 나갔을때
    override fun onLeaved(conference: FFConference, roomID: Long, userID: Long) {
        val v = usingViews[userID]
        v?.let {
            usingViews.remove(userID)
            availableViews.add(0, v)
        }
    }
    // 에러가 났을때
    override fun onError(conference: FFConference, errorCode: Int, error: String) {
        Toast.makeText(this, "errorCode: $errorCode error: $error", Toast.LENGTH_LONG).show()
    }
}
package dev.janus.farmachallange.utils.clases

import android.os.CountDownTimer

class Timer(private val seconds: Long) {
    private lateinit var timer: CountDownTimer
    fun startTemp(onTick: (Long) -> Unit, onFinish: () -> Unit) {
        timer = object : CountDownTimer(seconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                onTick(secondsRemaining)
            }
            override fun onFinish() {
                onFinish()
            }
        }
        timer.start()
    }
    fun startTempHearts(onTick: (Long, Long) -> Unit, onFinish: () -> Unit) {
        timer = object : CountDownTimer(seconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesRemaining = millisUntilFinished / 60000
                val secondsRemaining = (millisUntilFinished % 60000) / 1000
                onTick(minutesRemaining,secondsRemaining)
            }
            override fun onFinish() {
                onFinish()
            }
        }
        timer.start()
    }

    fun cancelTem() {
        timer.cancel()

    }


}
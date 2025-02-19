package com.zapplications.salonbooking.core.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

fun View.scaleVisibilityAnimation(
    from: Float = .5f,
    to: Float = 1f,
    animDuration: Int = 150,
    withEndAction: () -> Unit = {}
) {
    scaleX = from
    scaleY = from
    animate().apply {
        scaleX(to)
        scaleY(to)
        duration = animDuration.toLong()
        withEndAction {
            withEndAction()
            cancel()
        }
        start()
    }
}

fun View.scaleAnimation(
    from: Float = 1f,
    to: Float = 1.2f,
    animDuration: Long = 150
) {
    val scaleXFirst = ObjectAnimator.ofFloat(this, "scaleX", from, to)
    val scaleYFirst = ObjectAnimator.ofFloat(this, "scaleY", from, to)
    val scaleXSecond = ObjectAnimator.ofFloat(this, "scaleX", to, from).apply {
        startDelay = animDuration
    }
    val scaleYSecond = ObjectAnimator.ofFloat(this, "scaleY", to, from).apply {
        startDelay = animDuration
    }

    AnimatorSet().apply {
        duration = animDuration
        playTogether(scaleXFirst, scaleYFirst, scaleXSecond, scaleYSecond)
        start()
    }
}

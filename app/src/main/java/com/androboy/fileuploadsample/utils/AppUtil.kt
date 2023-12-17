package com.androboy.fileuploadsample.utils
import android.view.View
import androidx.core.content.ContextCompat
import com.androboy.fileuploadsample.SampleApp

object AppUtil {

    fun preventTwoClick(view: View?) {
        if (view != null) {
            view.isEnabled = false
            view.postDelayed(Runnable { view.isEnabled = true }, 800)
        }
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(SampleApp.INSTANCE, color)
    }



}
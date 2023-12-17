package com.androboy.fileuploadsample.utils
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.androboy.fileuploadsample.R
import com.androboy.fileuploadsample.SampleApp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File

object AppUtil {

    fun preventTwoClick(view: View?) {
        if (view != null) {
            view.isEnabled = false
            view.postDelayed(Runnable { view.isEnabled = true }, 800)
        }
    }

    /**
     * Method is used to check internet is connected or not
     */
    fun isConnection(): Boolean {
        val connectivityManager = SampleApp.INSTANCE
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager
            .activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(SampleApp.INSTANCE, color)
    }

    fun showToast(msg: String?) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(SampleApp.INSTANCE, msg, Toast.LENGTH_LONG)
                .show()
        }
    }


    fun loadImage(view: ImageView?, file: File?) {
        if (view != null && file != null) Glide.with(SampleApp.INSTANCE)
            .load(file)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .thumbnail(0.1f)
            .into(view)
    }



}
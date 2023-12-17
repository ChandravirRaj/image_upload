package com.androboy.fileuploadsample.utils

import android.content.Context
import android.content.DialogInterface
import android.text.Html
import androidx.appcompat.app.AlertDialog
import com.androboy.fileuploadsample.R
import com.androboy.fileuploadsample.callback.IDialogCallback

object DialogUtils {

     fun showAlert(
        context: Context?,
        title: String,
        message: String,
        ok: String?,
        no: String?,
        callback: IDialogCallback?
    ) {
        context?.let {
            val dialog =
                AlertDialog.Builder(it)
                    .setTitle(Html.fromHtml("<font color='#ffffff'>$title</font>"))
                    .setMessage(Html.fromHtml("<font color='#bbbbbb'>$message</font>"))
                    .setPositiveButton(
                        ok
                    ) { _: DialogInterface?, _: Int ->
                        callback?.onClick(true)
                    }
                    .setNegativeButton(
                        no
                    ) { _: DialogInterface?, _: Int ->
                        callback?.onClick(false)
                    }.show()

            dialog.window?.setBackgroundDrawableResource(android.R.color.background_dark);

            dialog.setCanceledOnTouchOutside(false)
            val positiveButton =
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton =
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            positiveButton.setTextColor(AppUtil.getColor(R.color.color_6A4242))
            negativeButton.setTextColor(AppUtil.getColor(R.color.color_6A4242))
        }
    }

}


package com.androboy.fileuploadsample.callback

/**
 * Callback in all gpsAlert dialog and click on ok or cancel button callback
 */
interface IDialogCallback {

    /**
     * onClick()
     * @param isOk Boolean
     * */
    fun onClick(isOk: Boolean)
}
package com.example.myapplication.ui.alert

import android.app.AlertDialog
import android.content.Context

object AlertDialogUtil {

    fun showAlertDialog(
        context: Context,
        title: String? = null,
        body: String,
        okButton: String,
        cancelButton: String? = null,
        onDialogOkClick: () -> Unit = {},
        onDialogCancelClick: () -> Unit = {},
    ) {
        AlertDialog.Builder(context)
            .apply {
                title?.let { setTitle(it) }
                setMessage(body)
                setPositiveButton(okButton) { _, _ -> onDialogOkClick() }
                cancelButton?.let { setNegativeButton(cancelButton) { _, _ -> onDialogCancelClick() } }
                show()
            }
    }
}
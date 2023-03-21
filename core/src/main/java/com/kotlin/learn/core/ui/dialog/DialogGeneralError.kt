/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.kotlin.learn.core.R
import com.kotlin.learn.core.databinding.DialogGeneralErrorBinding
import com.kotlin.learn.core.ui.dialog.data.BaseDataDialogGeneral
import com.kotlin.learn.core.ui.extensions.hide
import com.kotlin.learn.core.ui.extensions.show

class DialogGeneralError(
    private val data: BaseDataDialogGeneral,
    private val onClickPrimaryButton: () -> Unit,
    private val onClickSecondaryButton: () -> Unit = {},
    private val onDismiss: () -> Unit = {}
) : DialogFragment() {
    lateinit var binding: DialogGeneralErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogGeneralErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListeners()
        isCancelable = data.isCancelable
    }

    private fun setupView() = with(binding) {
        tvTitleCommonDialogSingleButton.text = data.title
        tvContentCommonDialogSingleButton.text = data.message
        btnActionPrimaryCommonDialog.text = data.textPrimaryButton
        data.icon?.let { ivIconDialogWithAction.setImageResource(it) }

        btnActionPrimaryCommonDialog.text = data.textPrimaryButton
        if (data.secondaryIsVisible == true) {
            btnActionSecondaryCommonDialog.show()
            dialogGeneral.removeView(btnActionPrimaryCommonDialog)
            dialogGeneral.addView(btnActionPrimaryCommonDialog)
            btnActionSecondaryCommonDialog.text = data.textPrimaryButton
            btnActionPrimaryCommonDialog.text =
                resources.getString(R.string.button_general_error_max_retry_close)
        } else btnActionSecondaryCommonDialog.hide()
    }

    private fun setupListeners() = with(binding) {
        if (data.secondaryIsVisible == true) {
            btnActionPrimaryCommonDialog.setOnClickListener { onClickSecondaryButton() }
            btnActionSecondaryCommonDialog.setOnClickListener {
                if (data.dismissOnAction) dismiss()
                onClickPrimaryButton()
            }
        } else {
            btnActionPrimaryCommonDialog.setOnClickListener {
                if (data.dismissOnAction) onDismiss()
                onClickPrimaryButton()
            }
            btnActionSecondaryCommonDialog.setOnClickListener { onClickSecondaryButton() }
        }
    }

}
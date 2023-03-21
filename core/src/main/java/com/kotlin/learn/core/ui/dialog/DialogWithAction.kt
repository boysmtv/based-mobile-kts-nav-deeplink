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
import android.view.ViewGroup
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.kotlin.learn.core.databinding.FragmentDialogWithActionBinding
import com.kotlin.learn.core.ui.dialog.data.BaseDataDialog
import com.kotlin.learn.core.ui.extensions.hide
import com.kotlin.learn.core.ui.extensions.setOnSingleClickListener
import com.kotlin.learn.core.ui.extensions.show

class DialogWithAction(
    private val onClickButtonPrimary: () -> Unit,
    private val onClickButtonWithIcon: (() -> Unit)? = null,
    private val onClickButtonSecondary: (() -> Unit)? = null
) : DialogFragment() {

    lateinit var binding: FragmentDialogWithActionBinding
    lateinit var data: BaseDataDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDialogWithActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListeners()
    }

    fun setDialog(data: BaseDataDialog) {
        this.data = data
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
    }

    private fun setupView() = with(binding) {
        tvTitleCommonDialogSingleButton.text = data.title
        tvContentCommonDialogSingleButton.text = data.content
        btnActionPrimaryCommonDialog.text = data.primaryButtonText
        btnActionSecondaryCommonDialog.text = data.secondaryButtonText
        if (data.buttonWithIconText.isNotEmpty()) {
            btnActionPrimaryWithIconCommonDialog.text = data.buttonWithIconText
        }

        data.icon?.let { ivIconDialogWithAction.setImageResource(it) }

        if (data.isIconShow) ivExclamationBlack.show()
        else ivExclamationBlack.hide()

        if (data.buttonWithIconShow) {
            btnActionSecondaryCommonDialog.hide()
            btnActionPrimaryCommonDialog.hide()
            btnActionPrimaryWithIconCommonDialog.show()
        } else {
            btnActionPrimaryWithIconCommonDialog.hide()
            validateButtonPrimaryAndSecondary()
        }
    }

    private fun validateButtonPrimaryAndSecondary() = with(binding) {
        when {
            data.primaryButtonShow && data.secondaryButtonShow -> {
                btnActionPrimaryCommonDialog.show()
                btnActionSecondaryCommonDialog.show()
            }
            data.primaryButtonShow -> {
                btnActionPrimaryCommonDialog.show()
                btnActionSecondaryCommonDialog.hide()
            }
            data.secondaryButtonShow -> {
                btnActionSecondaryCommonDialog.show()
                btnActionPrimaryCommonDialog.hide()
            }
            else -> {
                btnActionSecondaryCommonDialog.hide()
                btnActionPrimaryCommonDialog.hide()
            }
        }
    }

    private fun setupListeners() = with(binding) {
        btnActionSecondaryCommonDialog.setOnSingleClickListener {
            onClickButtonSecondary?.invoke()
            dismiss()
        }
        btnActionPrimaryCommonDialog.setOnSingleClickListener {
            onClickButtonPrimary()
            dismiss()
        }
        btnActionPrimaryWithIconCommonDialog.setOnSingleClickListener {
            onClickButtonWithIcon?.invoke()
            dismiss()
        }
    }
}
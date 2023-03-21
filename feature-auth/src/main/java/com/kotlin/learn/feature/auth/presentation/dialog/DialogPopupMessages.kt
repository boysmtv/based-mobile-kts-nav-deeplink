/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.feature.auth.presentation.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kotlin.learn.core.base.BaseDialogFragment
import com.kotlin.learn.core.common.util.RespondConstants.HttpCode.CODE_500
import com.kotlin.learn.core.util.Constants.DESCRIPTION
import com.kotlin.learn.core.util.Constants.TITLE
import com.kotlin.learn.feature.auth.R
import com.kotlin.learn.feature.auth.databinding.FragmentDialogPopupMessagesLoginBinding
import com.kotlin.learn.feature.auth.utils.AuthConstants.HTTP_STATUS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogPopupMessages : BaseDialogFragment<FragmentDialogPopupMessagesLoginBinding>() {

    override val injectedDialogFragment: DialogFragment = this

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDialogPopupMessagesLoginBinding = FragmentDialogPopupMessagesLoginBinding.inflate(
        inflater,
        container, false
    )

    override fun initView() {
        initData()
    }

    private fun initData() = with(mBinding) {
        tvTitleDialogPopupMessages.text = requireArguments().getString(TITLE).toString()
        tvDescDialogPopupMessages.text = requireArguments().getString(DESCRIPTION).toString()
        if (requireArguments().getString(HTTP_STATUS).toString() == CODE_500) {
            ivStatusDialogPopupMessages.setImageResource(R.drawable.ic_error_connection)
        } else ivStatusDialogPopupMessages.setImageResource(R.drawable.ic_close_rounded_red)

        ivCloseDialogPopupMessages.setOnClickListener { dismiss() }
    }
}
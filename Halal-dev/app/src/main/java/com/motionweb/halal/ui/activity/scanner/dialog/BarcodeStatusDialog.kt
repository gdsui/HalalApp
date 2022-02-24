package com.motionweb.halal.ui.activity.scanner.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.motionweb.halal.databinding.DialogBarcodeStatusBinding
import com.motionweb.halal.ui.fragment.error.ErrorDialog

class BarcodeStatusDialog private constructor(builder: Builder) : DialogFragment() {

    private var _vb: DialogBarcodeStatusBinding? = null
    private val vb: DialogBarcodeStatusBinding get() = _vb!!

    @DrawableRes
    private val icon: Int? = builder.icon

    @StringRes
    private val titleRes: Int? = builder.titleRes
    private val title: String? = builder.title

    @StringRes
    private val messageRes: Int? = builder.messageRes
    private val message: String? = builder.message

    private val action: () -> Unit = builder.action

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = DialogBarcodeStatusBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        if (titleRes != null) {
            vb.tvStatus.setText(titleRes)
        }
        if (title != null) vb.tvStatus.text = title

        if (messageRes != null) vb.tvProduct.setText(messageRes)
        if (message != null) vb.tvProduct.text = message
        if (icon != null) vb.ivLogo.setImageResource(icon)
        setupListeners()
    }

    private fun setupListeners() {
        vb.btnOk.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    companion object {
        inline fun show(fragmentManager: FragmentManager, tag: String?, block: Builder.() -> Unit) =
            Builder(fragmentManager, tag).apply(block).show()
    }

    class Builder(private val fragmentManager: FragmentManager, private val tag: String?) {
        @DrawableRes
        var icon: Int? = null

        @StringRes
        var titleRes: Int? = null
        var title: String? = null

        @StringRes
        var messageRes: Int? = null
        var message: String? = null

        var action: () -> Unit = {}

        fun show() = BarcodeStatusDialog(this).show(fragmentManager, tag)
    }
}
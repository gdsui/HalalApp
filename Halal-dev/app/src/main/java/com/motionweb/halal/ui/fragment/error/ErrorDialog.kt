package com.motionweb.halal.ui.fragment.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.motionweb.halal.databinding.ErrorDialogLayoutBinding


class ErrorDialog private constructor(builder: Builder) : DialogFragment() {

    @DrawableRes
    private val icon: Int? = builder.icon

    @StringRes
    private val title: Int? = builder.title

    private val textTitle: String? = builder.textTitle

    @StringRes
    private val message: Int? = builder.message
    private val action: () -> Unit = builder.action

    private val iconAction: ImageView.() -> Unit = builder.iconAction

    private var _vb: ErrorDialogLayoutBinding? = null
    private val vb: ErrorDialogLayoutBinding get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = ErrorDialogLayoutBinding.inflate(inflater, container, false)
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
        if (title != null) {
            vb.tvTitle.setText(title)
        }
        if (textTitle != null) {
            vb.tvTitle.text = textTitle
        }
        if (message != null) {
            vb.tvMessage.setText(message)
        }
        if (icon != null) {
            vb.ivIcon.setImageResource(icon)
            vb.ivIcon.iconAction()
        }
        setupListeners()
    }

    private fun setupListeners() {
        vb.ivClose.setOnClickListener {
            dismiss()
        }
        vb.btnAction.setOnClickListener {
            action.invoke()
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
        var title: Int? = null

        var textTitle: String? = null

        @StringRes
        var message: Int? = null
        var action: () -> Unit = {}
        var iconAction: ImageView.() -> Unit = {}

        fun show() = ErrorDialog(this).show(fragmentManager, tag)
    }

}



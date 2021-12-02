package com.zarinpal.utils

import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.core.content.ContextCompat
import com.zarinpal.R
import com.zarinpal.databinding.DialogCustomBinding

class CustomDialog constructor(private val builder: CustomDialogBuilder) :
    Dialog(builder.activity, R.style.LightDialogTheme), View.OnClickListener {

    private val binding = DialogCustomBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
    }

    override fun onClick(view: View) {

        when (view) {
            binding.btnPositive -> builder.positiveButton?.listener?.onClick(this)
            binding.btnNegative -> builder.negativeButton?.listener?.onClick(this)
        }
    }

    override fun show() {

        // set icon
        builder.icon?.let {

            binding.imgIcon.setImageResource(it)

            builder.iconTint?.let {
                binding.imgIcon.setColorFilter(ContextCompat.getColor(context, it))
            }

            binding.imgIcon.visibility = View.VISIBLE
        }

        // set title
        builder.title?.let {
            binding.txtTitle.text = it
            binding.txtTitle.visibility = View.VISIBLE
        }

        // set message
        builder.message?.let {
            binding.txtMessage.text = it
            binding.txtMessage.visibility = View.VISIBLE
        }

        // set positive button
        builder.positiveButton?.let {
            binding.btnPositive.text = it.text
            binding.btnPositive.visibility = View.VISIBLE
            binding.btnPositive.setOnClickListener(this)
        }

        // set negative button
        builder.negativeButton?.let {
            binding.btnNegative.text = it.text
            binding.btnNegative.visibility = View.VISIBLE
            binding.btnNegative.setOnClickListener(this)
        }

        // show buttons divider
        if (builder.positiveButton != null && builder.negativeButton != null)
            binding.divider.visibility = View.VISIBLE

        // set cancelable
        setCancelable(builder.cancelable)
        setCanceledOnTouchOutside(builder.cancelable)

        super.show()
    }

    fun interface DialogClickListener {
        fun onClick(dialog: CustomDialog)
    }
}

@DslMarker
annotation class ButtonMarker

class CustomDialogBuilder(
    val activity: Activity,
    var cancelable: Boolean = true,
    var icon: Int? = null,
    var iconTint: Int? = null,
    var title: String? = null,
    var message: String? = null,
    var positiveButton: PositiveButton? = null,
    var negativeButton: NegativeButton? = null
) {

    fun positiveButton(text: String, listener: CustomDialog.DialogClickListener) {
        positiveButton = PositiveButton(text, listener)
    }

    fun negativeButton(text: String, listener: CustomDialog.DialogClickListener) {
        negativeButton = NegativeButton(text, listener)
    }

    fun build() = CustomDialog(this)
}

@ButtonMarker
class PositiveButton(var text: String, var listener: CustomDialog.DialogClickListener)

@ButtonMarker
class NegativeButton(var text: String, var listener: CustomDialog.DialogClickListener)

fun dialog(activity: Activity, block: CustomDialogBuilder.() -> Unit) =
    CustomDialogBuilder(activity).apply(block).build()
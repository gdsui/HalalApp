package com.motionweb.halal.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.button.MaterialButton
import com.motionweb.halal.R
import com.motionweb.halal.databinding.FoodTypeBinding

class FoodTypeButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val vb: FoodTypeBinding by lazy(LazyThreadSafetyMode.NONE) {
        FoodTypeBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    fun setFoodType(type: FoodTypes) {
        when (type) {
            FoodTypes.HARAM -> {
                if (vb.root.childCount == 0) {
                    vb.root.addView(FoodButtons.haramButton(context, true))
                    vb.root.addView(FoodButtons.questionableButton(context, false))
                    vb.root.addView(FoodButtons.halalButton(context, false))
                }
            }
            FoodTypes.QUESTIONABLE -> {
                if (vb.root.childCount == 0) {
                    vb.root.addView(FoodButtons.haramButton(context, false))
                    vb.root.addView(FoodButtons.questionableButton(context, true))
                    vb.root.addView(FoodButtons.halalButton(context, false))
                }
            }
            FoodTypes.HALAL -> {
                if (vb.root.childCount == 0) {
                    vb.root.addView(FoodButtons.haramButton(context, false))
                    vb.root.addView(FoodButtons.questionableButton(context, false))
                    vb.root.addView(FoodButtons.halalButton(context, true))
                }
            }
        }
    }

    enum class FoodTypes {
        HARAM, QUESTIONABLE, HALAL
    }

    private class FoodButtons {

        companion object {

            private val params = LayoutParams(200, WRAP_CONTENT)
                .also {
                    it.setMargins(0, 0, 8, 0)
                }

            fun haramButton(context: Context, isActive: Boolean): View {
                return if (isActive) {
                    AppCompatButton(
                        ContextThemeWrapper(context, R.style.FoodTypeHaramActiveStyle),
                        null,
                        R.style.FoodTypeHaramActiveStyle
                    ).also {
                        it.layoutParams = params
                    }
                } else {
                    AppCompatButton(
                        ContextThemeWrapper(context, R.style.FoodTypeHaramInactiveStyle),
                        null,
                        R.style.FoodTypeHaramInactiveStyle
                    ).also {
                        it.layoutParams = params
                    }
                }
            }

            fun questionableButton(context: Context, isActive: Boolean): View {
                return if (isActive) {
                    AppCompatButton(
                        ContextThemeWrapper(context, R.style.FoodTypeQuestionableActiveStyle),
                        null,
                        R.style.FoodTypeQuestionableActiveStyle
                    ).also {
                        it.layoutParams = params
                    }
                } else {
                    AppCompatButton(
                        ContextThemeWrapper(context, R.style.FoodTypeQuestionableInactiveStyle),
                        null,
                        R.style.FoodTypeQuestionableInactiveStyle
                    ).also {
                        it.layoutParams = params
                    }
                }
            }

            fun halalButton(context: Context, isActive: Boolean): View {
                return if (isActive) {
                    AppCompatButton(
                        ContextThemeWrapper(
                            context,
                            R.style.FoodTypeHalalActiveStyle
                        ), null, R.style.FoodTypeHalalActiveStyle
                    ).also {
                        it.layoutParams = params
                    }
                } else {
                    AppCompatButton(
                        ContextThemeWrapper(
                            context,
                            R.style.FoodTypeHalalInactiveStyle
                        ), null, R.style.FoodTypeHalalInactiveStyle
                    ).also {
                        it.layoutParams = LayoutParams(200, WRAP_CONTENT)
                            .also { param ->
                                param.setMargins(0, 0, 0, 0)
                            }
                    }
                }
            }

        }
    }

}
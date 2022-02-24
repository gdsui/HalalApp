package com.motionweb.halal.data.network.ecode

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.motionweb.halal.customview.FoodTypeButton
import kotlinx.parcelize.Parcelize

@Parcelize
data class ECode(
    val id: Int,
    val code: String,
    val title: String,
    val type: String,
    @SerializedName("damage_description")
    val damageDescription: String,
    @SerializedName("useful_description")
    val usefulDescription: String
) : Parcelable {

    fun toFoodType(): FoodTypeButton.FoodTypes? {
        return when (type) {
            "good" -> {
                FoodTypeButton.FoodTypes.HALAL
            }
            "doubt" -> {
                FoodTypeButton.FoodTypes.QUESTIONABLE
            }
            "harm" -> {
                FoodTypeButton.FoodTypes.HARAM
            }
            else -> return null
        }
    }

}
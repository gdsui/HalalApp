package com.motionweb.halal.utils

import android.content.Context

class DefaultValues private constructor() {

    companion object {
        fun avatarPath(context: Context): String = context.getAssetsFilePath(Keys.AVATAR_NAME)
    }

}
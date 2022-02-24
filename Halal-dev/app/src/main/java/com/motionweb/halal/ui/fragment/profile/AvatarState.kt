package com.motionweb.halal.ui.fragment.profile

sealed class AvatarState {
    data class Default(val path: String?): AvatarState()
    data class FromServer(val url: String) : AvatarState()
    data class FromGallery(val path: String?) : AvatarState()
}
package com.github.gr3gdev.loader.gltf.objects

import kotlinx.serialization.Serializable

@Serializable
data class TextureInfo(
    val index: Int,
    val texCoord: Int = 0,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

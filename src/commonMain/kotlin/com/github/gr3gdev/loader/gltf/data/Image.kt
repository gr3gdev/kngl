package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val uri: String ? = null,
    val mimeType: String ? = null,
    val bufferView: Int ? = null,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

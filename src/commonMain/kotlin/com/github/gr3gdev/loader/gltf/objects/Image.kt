package com.github.gr3gdev.loader.gltf.objects

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val uri: String ? = null,
    val mimeType: String ? = null,
    val bufferView: Int ? = null,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
) {
    init {
        if (uri != null) {
            // TODO: load image (image/png or image/jpeg)
        }
    }
}

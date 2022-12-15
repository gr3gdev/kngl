package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Texture(
    val sampler: Int ? = null,
    val source: Int ? = null,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

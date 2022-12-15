package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Sampler(
    val magFilter: Int ? = null,
    val minFilter: Int ? = null,
    val wrapS: Int = 10497,
    val wrapT: Int = 10497,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

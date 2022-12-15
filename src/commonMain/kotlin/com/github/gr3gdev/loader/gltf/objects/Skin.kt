package com.github.gr3gdev.loader.gltf.objects

import kotlinx.serialization.Serializable

@Serializable
data class Skin(
    val inverseBindMatrices: Int ? = null,
    val skeleton: Int ? = null,
    val joints: Array<Int>,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

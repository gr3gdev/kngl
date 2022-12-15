package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Scene(
    val nodes: Array<Int> ? = null,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Mesh(
    val primitives: Array<Primitive>,
    val weights: Array<Float> ? = null,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
) {
    // TODO: Primitive#targets
    @Serializable
    data class Primitive(
        val attributes: Map<String, Int>,
        val indices: Int ? = null,
        val material: Int ? = null,
        val mode: Int = 4,
        val extensions: Extension ? = null,
        val extras: Extras? = null
    )
}

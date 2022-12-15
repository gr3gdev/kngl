package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Accessor(
    val bufferView: Int? = null,
    val byteOffset: Int = 0,
    val componentType: Int,
    val normalized: Boolean = false,
    val count: Int,
    val type: String,
    val max: Array<Float>? = null,
    val min: Array<Float>? = null,
    val sparse: Sparse? = null,
    val name: String? = null,
    val extensions: Extension? = null,
    val extras: Extras? = null
) {
    @Serializable
    data class Sparse(
        val count: Int,
        val indices: Indices,
        val values: Values,
        val extensions: Extension ? = null,
        val extras: Extras?
    ) {
        @Serializable
        data class Indices(
            val bufferView: Int,
            val byteOffset: Int = 0,
            val componentType: Int,
            val extensions: Extension ? = null,
            val extras: Extras?
        )

        @Serializable
        data class Values(
            val bufferView: Int,
            val byteOffset: Int = 0,
            val extensions: Extension ? = null,
            val extras: Extras?
        )
    }
}
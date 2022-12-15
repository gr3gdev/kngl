package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val copyright: String ? = null,
    val generator: String ? = null,
    val version: String,
    val minVersion: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

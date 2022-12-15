package com.github.gr3gdev.loader.gltf.objects

import kotlinx.serialization.Serializable

@Serializable
data class Animation(
    val channels: Array<Channel>,
    val samplers: Array<Sampler>,
    val name: String? = null,
    val extensions: Extension? = null,
    val extras: Extras? = null
) {
    @Serializable
    data class Channel(
        val sampler: Int,
        val target: Target,
        val extensions: Extension? = null,
        val extras: Extras? = null
    )

    @Serializable
    data class Sampler(
        val input: Int,
        val interpolation: String = "LINEAR",
        val output: Int,
        val extensions: Extension? = null,
        val extras: Extras? = null
    )

    @Serializable
    data class Target(
        val node: Int? = null,
        val path: String,
        val extensions: Extension? = null,
        val extras: Extras? = null
    )
}

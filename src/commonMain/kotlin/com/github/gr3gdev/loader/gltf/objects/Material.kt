package com.github.gr3gdev.loader.gltf.objects

import kotlinx.serialization.Serializable

@Serializable
data class Material(
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras ? = null,
    val pbrMetallicRoughness: PbrMetallicRoughness ? = null,
    val normalTexture: NormalTextureInfo ? = null,
    val occlusionTexture: OcclusionTextureInfo ? = null,
    val emissiveTexture: TextureInfo ? = null,
    val emissiveFactor: Array<Float> = arrayOf(0f, 0f, 0f),
    val alphaMode: String = "OPAQUE",
    val alphaCutoff: Float = 0.5f,
    val doubleSided: Boolean = false
) {
    @Serializable
    data class PbrMetallicRoughness(
        val baseColorFactor: Array<Float> = arrayOf(1f, 1f, 1f, 1f),
        val baseColorTexture: TextureInfo ? = null,
        val metallicFactor: Float = 1f,
        val roughnessFactor: Float = 1f,
        val metallicRoughnessTexture: TextureInfo ? = null,
        val extensions: Extension ? = null,
        val extras: Extras? = null
    )

    @Serializable
    data class NormalTextureInfo(
        val index: Int,
        val texCoord: Int = 0,
        val scale: Float = 1f,
        val extensions: Extension ? = null,
        val extras: Extras? = null
    )

    @Serializable
    data class OcclusionTextureInfo(
        val index: Int,
        val texCoord: Int = 0,
        val strength: Float = 1f,
        val extensions: Extension ? = null,
        val extras: Extras? = null
    )
}

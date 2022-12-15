package com.github.gr3gdev.loader.gltf.objects

import kotlinx.serialization.Serializable

@Serializable
data class Extension(
    val extensionsUsed: Array<String> ? = null,
    val extensionsRequired: Array<String> ? = null,
    val accessors: Array<Accessor> ? = null,
    val animations: Array<Animation> ? = null,
    val asset: Asset,
    val buffers: Array<Buffer> ? = null,
    val bufferViews: Array<BufferView> ? = null,
    val cameras: Array<Camera> ? = null,
    val images: Array<Image> ? = null,
    val materials: Array<Material> ? = null,
    val meshes: Array<Mesh> ? = null,
    val nodes: Array<Node> ? = null,
    val samplers: Array<Sampler> ? = null,
    val scene: Int ? = null,
    val scenes: Array<Scene> ? = null,
    val skins: Array<Skin> ? = null,
    val textures: Array<Texture> ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)

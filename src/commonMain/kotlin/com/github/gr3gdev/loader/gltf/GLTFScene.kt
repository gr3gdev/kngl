package com.github.gr3gdev.loader.gltf

import com.github.gr3gdev.loader.gltf.objects.*
import kotlinx.serialization.Serializable

@Serializable
class GLTFScene(
    val accessors: Array<Accessor>,
    val animations: Array<Animation>,
    val asset: Asset,
    val bufferViews: Array<BufferView>,
    val buffers: Array<Buffer>,
    val images: Array<Image>,
    val materials: Array<Material>,
    val meshes: Array<Mesh>,
    val nodes: Array<Node>,
    val samplers: Array<Sampler>,
    val scene: Int,
    val scenes: Array<Scene>,
    val textures: Array<Texture>
)

package com.github.gr3gdev.loader.gltf

import com.github.gr3gdev.loader.gltf.data.*
import com.github.gr3gdev.loader.util.Files
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
) {
    /**
     * Load Buffers and Images.
     */
    fun load(directory: String) {
        // TODO: load Buffers ".bin" file (application/octet-stream)
        buffers
            .filter { it.uri != null }
            .forEach { buffer ->
                println("BUFFER : " + Files.readByteBuffer("$directory/${buffer.uri}", buffer.byteLength))
            }
        // TODO: load Images (image/png or image/jpeg)
        images
            .filter { it.uri != null }
            .forEach { image ->
                println("IMAGE : $directory/${image.uri}")
            }
    }
}

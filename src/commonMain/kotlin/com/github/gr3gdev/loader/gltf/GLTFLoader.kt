package com.github.gr3gdev.loader.gltf

import com.github.gr3gdev.loader.util.Files
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Implementation of https://registry.khronos.org/glTF/specs/2.0/glTF-2.0.html
 */
class GLTFLoader {

    /**
     * Parse a ".gltf" file.
     */
    fun parse(filepath: String): GLTFScene {
        val scene = Json.decodeFromString<GLTFScene>(Files.readAllText(filepath))
        scene.load(filepath.substring(0, filepath.lastIndexOf("/")))
        return scene
    }
}
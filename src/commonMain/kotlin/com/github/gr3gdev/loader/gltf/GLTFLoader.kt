package com.github.gr3gdev.loader.gltf

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen

/**
 * Implementation of https://registry.khronos.org/glTF/specs/2.0/glTF-2.0.html
 */
class GLTFLoader {

    private fun readAllText(filepath: String): String {
        val builder = StringBuilder()
        val file = fopen(filepath, "r") ?: throw IllegalArgumentException("Cannot open file $filepath")
        try {
            memScoped {
                val readBufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(readBufferLength)
                var line = fgets(buffer, readBufferLength, file)?.toKString()
                while (line != null) {
                    builder.append(line)
                    line = fgets(buffer, readBufferLength, file)?.toKString()
                }
            }
        } finally {
            fclose(file)
        }
        return builder.toString()
    }

    /**
     * Parse a ".gltf" file.
     */
    fun parse(filepath: String): GLTFScene {
        return Json.decodeFromString(readAllText(filepath))
    }
}
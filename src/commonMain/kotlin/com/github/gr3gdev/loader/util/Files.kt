package com.github.gr3gdev.loader.util

import kotlinx.cinterop.*
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen

object Files {

    fun readByteBuffer(filepath: String, length: Int): Array<Byte> {
        val file = fopen(filepath, "r") ?: throw IllegalArgumentException("Cannot open file $filepath")
        try {
            memScoped {
                val buffer = allocArray<ByteVar>(length)
                fgets(buffer, length, file)
                return (0 until length).map { buffer[it] }.toTypedArray()
            }
        } finally {
            fclose(file)
        }
    }

    fun readAllText(filepath: String): String {
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

}
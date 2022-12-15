package com.github.gr3gdev.loader.gltf

import platform.posix.fopen
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GLTFLoaderTest {

    private lateinit var loader : GLTFLoader

    @BeforeTest
    fun before() {
        loader = GLTFLoader()
    }

    @Test
    fun load_animatedCube() {
        val scene = loader.parse("src/commonTest/resources/samples/AnimatedCube/AnimatedCube.gltf")
        assertNotNull(scene)
        assertEquals(7, scene.accessors.size)
        assertEquals(1, scene.animations.size)
        assertEquals("VKTS glTF 2.0 exporter", scene.asset.generator)
        assertEquals("2.0", scene.asset.version)
        assertEquals(7, scene.bufferViews.size)
        assertEquals(1, scene.buffers.size)
        assertEquals(2, scene.images.size)
        assertEquals(1, scene.materials.size)
        assertEquals(1, scene.meshes.size)
        assertEquals(1, scene.nodes.size)
        assertEquals(1, scene.samplers.size)
        assertEquals(0, scene.scene)
        assertEquals(1, scene.scenes.size)
        assertEquals(2, scene.textures.size)
    }
}
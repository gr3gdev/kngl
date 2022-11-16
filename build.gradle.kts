plugins {
    kotlin("multiplatform") version "1.7.20"
}

group = "com.github.gr3gdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
    nativeTarget.apply {
        compilations.getByName("main") {
            cinterops {
                val glew by creating {
                    defFile(project.file("src/nativeInterop/cinterop/glew.def"))
                }
                val glfw by creating {
                    defFile(project.file("src/nativeInterop/cinterop/glfw.def"))
                }
            }
        }
        binaries {
            executable {
                entryPoint = "com.github.gr3gdev.main"
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting
    }
}

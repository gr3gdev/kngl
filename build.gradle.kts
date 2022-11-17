import org.gradle.kotlin.dsl.support.unzipTo

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
        hostOs == "Mac OS X" -> macosX64("nativeOSX")
        hostOs == "Linux" -> linuxX64("nativeLinuw")
        isMingwX64 -> mingwX64("nativeMingw")
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

val glfwVersion = "3.3.8"
val glewVersion = "2.1.0"

fun download(url: String, dest: String) {
    ant.withGroovyBuilder {
        "get"("src" to url, "dest" to dest)
    }
}

fun buildSources(sourcePath: String, destPath: String) {
    exec {
        workingDir(sourcePath)
        commandLine("cmake", "-S .", "-B .", "-DCMAKE_INSTALL_PREFIX=$destPath")
    }
    exec {
        workingDir(sourcePath)
        commandLine("make")
        commandLine("make", "install")
    }
}

tasks {
    register("installGLFW") {
        download(
            "https://github.com/glfw/glfw/releases/download/$glfwVersion/glfw-$glfwVersion.zip",
            "build/glfw.zip"
        )
        unzipTo(File("build"), File("build/glfw.zip"))
        buildSources(
            "build/glfw-$glfwVersion",
            "${project.file("lib").absolutePath}/glfw"
        )
    }

    register("installGLFWForWindows") {
        download(
            "https://github.com/glfw/glfw/releases/download/$glfwVersion/glfw-$glfwVersion.bin.WIN64.zip",
            "build/glfwWindows.zip"
        )
        unzipTo(File("lib"), File("build/glfwWindows.zip"))
    }

    register("installGLEW") {
        download(
            "https://freefr.dl.sourceforge.net/project/glew/glew/$glewVersion/glew-$glewVersion.zip",
            "build/glew.zip"
        )
        unzipTo(File("build"), File("build/glew.zip"))
        buildSources(
            "build/glew-$glewVersion/build/cmake",
            "${project.file("lib").absolutePath}/glew"
        )
    }

    register("installGLEWForWindows") {
        download(
            "https://freefr.dl.sourceforge.net/project/glew/glew/$glewVersion/glew-$glewVersion-win32.zip",
            "build/glewWindows.zip"
        )
        unzipTo(File("lib"), File("build/glewWindows.zip"))
    }

    register("initOpenGL") {
        //dependsOn("installGLFW")
        dependsOn("installGLFWForWindows")
        //dependsOn("installGLEW")
        dependsOn("installGLEWForWindows")
    }
}

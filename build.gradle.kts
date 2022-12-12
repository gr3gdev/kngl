import org.gradle.kotlin.dsl.support.unzipTo

plugins {
    kotlin("multiplatform") version "1.7.20"
}

group = "com.github.gr3gdev"
version = "1.0-SNAPSHOT"

val userHome = File(System.getenv("USERPROFILE") ?: "")

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
    val defDir = when {
        isMingwX64 -> "src/nativeInterop/cinterop/windows"
        else -> "src/nativeInterop/cinterop"
    }
    val opts = when {
        isMingwX64 -> listOf(
            "-L${userHome}\\.konan\\dependencies\\msys2-mingw-w64-x86_64-2\\x86_64-w64-mingw32\\lib",
            "-L${project.rootDir}"
        )
        else -> emptyList()
    }
    nativeTarget.apply {
        compilations.getByName("main") {
            cinterops {
                val glew by creating {
                    defFile(project.file("$defDir/glew.def"))
                }
                val glfw by creating {
                    defFile(project.file("$defDir/glfw.def"))
                }
            }
        }
        binaries {
            executable {
                entryPoint = "com.github.gr3gdev.main"
                linkerOpts(opts)
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

fun download(url: String, dest: File) {
    if (!dest.parentFile.exists()) {
        dest.parentFile.mkdirs()
    }
    ant.withGroovyBuilder {
        "get"("src" to url, "dest" to dest)
    }
}

tasks {
    register("installGLFWForWindows") {
        doFirst {
            download(
                "https://github.com/glfw/glfw/releases/download/$glfwVersion/glfw-$glfwVersion.zip",
                project.file("build/glfwWindows.zip")
            )
        }
        doLast {
            unzipTo(project.file("build"), project.file("build/glfwWindows.zip"))
        }
    }

    register("installGLFWBinariesForWindows") {
        doFirst {
            download(
                "https://github.com/glfw/glfw/releases/download/$glfwVersion/glfw-$glfwVersion.bin.WIN64.zip",
                project.file("build/glfwBinWindows.zip")
            )
        }
        doLast {
            unzipTo(project.file("build"), project.file("build/glfwBinWindows.zip"))
            copy {
                from(project.file("build/glfw-$glfwVersion.bin.WIN64/lib-mingw-w64/glfw3.dll"))
                into("C:\\mingw64\\x86_64-w64-mingw32\\bin")
            }
        }
    }

    register("installGLEWForWindows") {
        doFirst {
            download(
                "https://freefr.dl.sourceforge.net/project/glew/glew/$glewVersion/glew-$glewVersion.zip",
                project.file("build/glewWindows.zip")
            )
        }
        doLast {
            unzipTo(project.file("build"), project.file("build/glewWindows.zip"))
        }
    }

    register("initOpenGLForWindows") {
        doFirst {
            println("Required : MinGW-w64 and CMake for Windows")
        }
        dependsOn("installGLFWForWindows")
        dependsOn("installGLEWForWindows")
        doLast {
            exec {
                workingDir(project.file("build/glew-$glewVersion/build/cmake"))
                commandLine(project.file("glew-windows.cmd").absolutePath)
            }
            exec {
                workingDir(project.file("build/glfw-$glfwVersion"))
                commandLine(project.file("glfw-windows.cmd").absolutePath)
            }
        }
    }

    register("copyDlls", Copy::class) {
        dependsOn("installGLFWBinariesForWindows")
        from("C:\\mingw64\\x86_64-w64-mingw32\\bin") {
            include("glew32.dll")
            include("glfw3.dll")
        }
        into("build\\bin\\nativeMingw\\releaseExecutable")
    }
}

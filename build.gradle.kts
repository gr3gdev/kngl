import org.gradle.kotlin.dsl.support.unzipTo

plugins {
    kotlin("multiplatform") version "1.7.20"
}

group = "com.github.gr3gdev"
version = "1.0-SNAPSHOT"

val korimVersion = "2.2.0"

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
                    if (isMingwX64) {
                        defFile(project.file("src/nativeInterop/cinterop/glew-windows.def"))
                    } else {
                        defFile(project.file("src/nativeInterop/cinterop/glew.def"))
                    }
                }
                val glfw by creating {
                    if (isMingwX64) {
                        defFile(project.file("src/nativeInterop/cinterop/glfw-windows.def"))
                    } else {
                        defFile(project.file("src/nativeInterop/cinterop/glfw.def"))
                    }
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
        val commonTest by getting {
            dependencies {
                implementation("com.soywiz.korlibs.korim:korim:$korimVersion")
            }
        }
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
                "https://github.com/glfw/glfw/releases/download/$glfwVersion/glfw-$glfwVersion.bin.WIN64.zip",
                project.file("build/glfwWindows.zip")
            )
        }
        doLast {
            unzipTo(project.file("lib"), project.file("build/glfwWindows.zip"))
        }
    }

    register("installGLEWForWindows") {
        doFirst {
            download(
                "https://freefr.dl.sourceforge.net/project/glew/glew/$glewVersion/glew-$glewVersion-win32.zip",
                project.file("build/glewWindows.zip")
            )
        }
        doLast {
            unzipTo(project.file("lib"), project.file("build/glewWindows.zip"))
        }
    }

    register("initOpenGL") {
        dependsOn("installGLFWForWindows")
        dependsOn("installGLEWForWindows")
    }
}

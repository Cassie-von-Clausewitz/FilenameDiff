package filenamediff

import com.andreapivetta.kolor.green
import com.andreapivetta.kolor.lightBlue
import com.andreapivetta.kolor.red
import java.io.File

data class FilenameDiff(val lhsFolder: File, val rhsFolder: File) {
    fun prepareOutput(): List<String> {
        val output = mutableListOf<String>()

        val folder1 = lhsFolder
        check(folder1.isDirectory) { "First argument must be a directory" }

        val folder2 = rhsFolder
        check(folder2.isDirectory) { "Second argument must be a directory" }

        val set1 = folder1.listFiles()?.map { it.toFSObj() }?.toSet() ?: emptySet()
        val set2 = folder2.listFiles()?.map { it.toFSObj() }?.toSet() ?: emptySet()

        output.add("")
        output.add("Files from first directory not in second".lightBlue())
        output.add("----------------------------------------".lightBlue())
        set1.forEach {
            when (it) {
                is FSObj.Folder -> {
                    if (!set2.contains(it)) {
                        output.add("$it".red())
                    }
                }
                is FSObj.File -> {
                    val find = set2.find { set2Item ->
                        set2Item is FSObj.File && set2Item.name.contains(it.name) && set2Item.extension != it.extension
                    }

                    if (find != null) {
                        find.let { fsobj ->
                            if (fsobj != null) {
                                val f = fsobj as FSObj.File
                                output.add("${f.name}.".lightBlue() + f.extension.red())
                            }
                        }
                    } else {
                        if (!set2.contains(it)) {
                            println(it)
                            output.add("$it".red())
                        }
                    }
                }
            }
        }
        output.add("")

        output.add("Files from second directory not in first".lightBlue())
        output.add("----------------------------------------".lightBlue())
        set2.forEach {
            when (it) {
                is FSObj.Folder -> {
                    if (!set1.contains(it)) {
                        output.add("$it".green())
                    }
                }
                is FSObj.File -> {
                    val find = set1.find { set1Item ->
                        set1Item is FSObj.File && set1Item.name.contains(it.name) && set1Item.extension != it.extension
                    }

                    if (find != null) {
                        find.let { fsobj ->
                            // needed because java
                            if (fsobj != null) {
                                val f = fsobj as FSObj.File
                                output.add("${f.name}.".lightBlue() + f.extension.green())
                            }
                        }
                    } else {
                        if (!set1.contains(it)) {
                            output.add("$it".green())
                        }
                    }
                }
            }
        }
        output.add("")

        return output
    }
}

sealed class FSObj() {
    data class Folder(val name: String): FSObj() {
        override fun toString(): String {
            return "$name/"
        }
    }
    data class File(val name: String, val extension: String): FSObj() {
        override fun toString(): String {
            return "$name.$extension"
        }
    }
}

fun File.toFSObj(): FSObj {
    return if (isDirectory) {
        FSObj.Folder(this.name)
    } else {
        FSObj.File(this.nameWithoutExtension, this.extension)
    }
}
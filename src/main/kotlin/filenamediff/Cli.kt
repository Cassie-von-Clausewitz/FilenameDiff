package filenamediff

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.File


class FilenameDiffCommand: CliktCommand() {
    val lhsFile by argument("Left Hand Folder", "Provide a folder to scan and diff")
    val rhsFile by argument("Right Hand Folder", "Provide a folder to scan and diff")

    override fun run() {
        println("lhsFile $lhsFile")
        println("rhsFile $rhsFile")

        try {
            val l = File(lhsFile)
            val r = File(rhsFile)

            val filenameDiff = FilenameDiff(l, r)
            filenameDiff.prepareOutput().forEach { println(it) }
        } catch (t: Throwable) {
            println("Error opening file ${t.message}")
            t.printStackTrace()
        }
    }
}

fun main(args: Array<String>) = FilenameDiffCommand().main(args)
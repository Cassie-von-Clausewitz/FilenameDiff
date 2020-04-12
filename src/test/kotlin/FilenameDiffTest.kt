import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotEqualTo
import com.andreapivetta.kolor.green
import com.andreapivetta.kolor.lightBlue
import com.andreapivetta.kolor.red
import com.andreapivetta.kolor.yellow
import filenamediff.FSObj
import filenamediff.FilenameDiff
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import java.io.File


class FilenameDiffTest {
    @MockK
    lateinit var file1: File

    @MockK
    lateinit var file2: File

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `filenames match but all extensions differ`() {
        every { file1.isDirectory } returns true
        every { file1.listFiles() } returns genesisGamesMdExtension

        every { file2.isDirectory } returns true
        every { file2.listFiles() } returns genesisGamesGenExtension

        val spy = spyk(FilenameDiff(file1, file2))
        val output = spy.prepareOutput()

        println()
        println(object{}.javaClass.enclosingMethod.name.yellow())
        println("file1 [${file1.listFiles().joinToString { it.name }}]")
        println("file2 [${file2.listFiles().joinToString { it.name }}]")
        output.forEachIndexed { i, s -> println("$i. $s") }

        assertThat(output).isNotEmpty()

        assertThat(output[3]).isNotEqualTo("FIFA 94.gen")
        assertThat(output[3]).isEqualTo("FIFA 94.".lightBlue() + "gen".red())

        verify { file1.isDirectory }
        verify { file1.listFiles() }

        verify { file2.isDirectory }
        verify { file2.listFiles() }
    }

    @Test
    fun `added folder to second folder`() {
        every { file1.isDirectory } returns true
        every { file1.listFiles() } returns neoGeoGames

        every { file2.isDirectory } returns true
        every { file2.listFiles() } returns neoGeoGamesAddFolder

        val spy = spyk(FilenameDiff(file1, file2))
        val output = spy.prepareOutput()

        println()
        println(object{}.javaClass.enclosingMethod.name.yellow())
        println("file1 [${file1.listFiles().joinToString { it.name }}]")
        println("file2 [${file2.listFiles().joinToString { it.name }}]")
        output.forEachIndexed { i, s -> println("$i. $s") }

        assertThat(output).isNotEmpty()

        assertThat(output[6]).isNotEqualTo(FSObj.Folder("KoF").toString())
        assertThat(output[6]).isEqualTo(FSObj.Folder("KoF").toString().green())

        verify { file1.isDirectory }
        verify { file1.listFiles() }

        verify { file2.isDirectory }
        verify { file2.listFiles() }
    }

    @Test
    fun `added to second folder`() {
        every { file1.isDirectory } returns true
        every { file1.listFiles() } returns gbaGamesOriginal

        every { file2.isDirectory } returns true
        every { file2.listFiles() } returns gbaGamesAdded

        val spy = spyk(FilenameDiff(file1, file2))
        val output = spy.prepareOutput()

        println()
        println(object{}.javaClass.enclosingMethod.name.yellow())
        println("file1 [${file1.listFiles().joinToString { it.name }}]")
        println("file2 [${file2.listFiles().joinToString { it.name }}]")
        output.forEachIndexed { i, s -> println("$i. $s") }

        assertThat(output).isNotEmpty()

        assertThat(output[6]).isNotEqualTo(FSObj.File("Mega Man & Bass", "gba").toString())
        assertThat(output[6]).isEqualTo(FSObj.File("Mega Man & Bass", "gba").toString().green())

        assertThat(output[7]).isNotEqualTo(FSObj.File("Mega Man Battle Chip Challenge", "gba").toString())
        assertThat(output[7]).isEqualTo(FSObj.File("Mega Man Battle Chip Challenge", "gba").toString().green())

        verify { file1.isDirectory }
        verify { file1.listFiles() }

        verify { file2.isDirectory }
        verify { file2.listFiles() }
    }

    @Test
    fun `removed from second folder`() {
        every { file1.isDirectory } returns true
        every { file1.listFiles() } returns gbaGamesOriginal

        every { file2.isDirectory } returns true
        every { file2.listFiles() } returns gbaGamesRemoved

        val spy = spyk(FilenameDiff(file1, file2))
        val output = spy.prepareOutput()

        println()
        println(object{}.javaClass.enclosingMethod.name.yellow())
        println("file1 [${file1.listFiles().joinToString { it.name }}]")
        println("file2 [${file2.listFiles().joinToString { it.name }}]")
        output.forEachIndexed { i, s -> println("$i. $s") }

        assertThat(output).isNotEmpty()

        assertThat(output[3]).isNotEqualTo(FSObj.File("Mario Kart - Super Circuit", "gba").toString())
        assertThat(output[3]).isEqualTo(FSObj.File("Mario Kart - Super Circuit", "gba").toString().red())

        assertThat(output[4]).isNotEqualTo(FSObj.File("Mario & Luigi Superstar Saga", "gba").toString())
        assertThat(output[4]).isEqualTo(FSObj.File("Mario & Luigi Superstar Saga", "gba").toString().red())

        verify { file1.isDirectory }
        verify { file1.listFiles() }

        verify { file2.isDirectory }
        verify { file2.listFiles() }
    }

    private val genesisGamesMdExtension: Array<File>
        get() {
            val files = mutableListOf<File>()

            files.add(mockFile("FIFA 94.md", "md"))
            files.add(mockFile("NHL 94.md", "md"))

            return files.toTypedArray()
        }

    private val genesisGamesGenExtension: Array<File>
        get() {
            val files = mutableListOf<File>()

            files.add(mockFile("FIFA 94.gen", "gen"))
            files.add(mockFile("NHL 94.gen", "gen"))

            return files.toTypedArray()
        }

    private val neoGeoGames: Array<File>
        get() {
            val files = mutableListOf<File>()

            files.add(mockFile("MetalSlug.zip", "zip"))
            files.add(mockFile("MetalSlug2.zip", "zip"))
            files.add(mockFile("MetalSlug3.zip", "zip"))
            files.add(mockFile("MetalSlug4.zip", "zip"))

            return files.toTypedArray()
        }

    private val neoGeoGamesAddFolder: Array<File>
        get() {
            val files = mutableListOf<File>()

            files.add(mockFile("MetalSlug.zip", "zip"))
            files.add(mockFile("MetalSlug2.zip", "zip"))
            files.add(mockFile("MetalSlug3.zip", "zip"))
            files.add(mockFile("MetalSlug4.zip", "zip"))
            files.add(mockFolder("KoF"))

            return files.toTypedArray()
        }

    private val gbaGamesAdded: Array<File>
        get() {
            val files = mutableListOf<File>()

            files.add(mockFile("Mario Golf - Advance Tour.gba", "gba"))
            files.add(mockFile("Mario Kart - Super Circuit.gba", "gba"))
            files.add(mockFile("Mario & Luigi Superstar Saga.gba", "gba"))
            files.add(mockFile("Mario Party Advance.gba", "gba"))
            files.add(mockFile("Mega Man & Bass.gba", "gba"))
            files.add(mockFile("Mega Man Battle Chip Challenge.gba", "gba"))

            return files.toTypedArray()
        }

    private val gbaGamesRemoved: Array<File>
        get() {
            val files = mutableListOf<File>()

            files.add(mockFile("Mario Golf - Advance Tour.gba", "gba"))
            files.add(mockFile("Mario Party Advance.gba", "gba"))

            return files.toTypedArray()
        }

    private val gbaGamesOriginal: Array<File>
        get() {
            val files = mutableListOf<File>()

            files.add(mockFile("Mario Golf - Advance Tour.gba", "gba"))
            files.add(mockFile("Mario Kart - Super Circuit.gba", "gba"))
            files.add(mockFile("Mario & Luigi Superstar Saga.gba", "gba"))
            files.add(mockFile("Mario Party Advance.gba", "gba"))

            return files.toTypedArray()
        }

    private fun mockFile(name: String, extension: String): File {
        val file = mockk<File>()
        every { file.name } returns "$name.$extension"
        every { file.extension } returns extension
        every { file.nameWithoutExtension } returns name
        every { file.isDirectory } returns false
        return file
    }

    private fun mockFolder(name: String): File {
        val file = mockk<File>()
        every { file.name } returns name
        every { file.isDirectory } returns true
        return file
    }
}
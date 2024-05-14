package com.jetbrains.rider.plugins.fsharp.test.cases

import com.intellij.openapi.vfs.LocalFileSystem
import com.jetbrains.rdclient.util.idea.waitAndPump
import com.jetbrains.rider.plugins.fsharp.test.fcsHost
import com.jetbrains.rider.test.annotations.TestEnvironment
import com.jetbrains.rider.test.asserts.shouldNotBeNull
import com.jetbrains.rider.test.base.BaseTestWithSolution
import com.jetbrains.rider.test.env.enums.SdkVersion
import com.jetbrains.rider.test.scriptingApi.changeFileContent
import com.jetbrains.rd.platform.util.lifetime
import com.jetbrains.rider.test.annotations.Mute
import com.jetbrains.rider.test.enums.PlatformType
import org.testng.annotations.Test
import java.io.File
import java.time.Duration

@Test
@TestEnvironment(sdkVersion = SdkVersion.LATEST_STABLE)
class FileSystemShimTest : BaseTestWithSolution() {
  override fun getSolutionDirectoryName() = "CoreConsoleApp"

  @Test
  @Mute("RIDER-111885", platforms= [PlatformType.LINUX_ALL])
  fun externalFileChange() {
    val file = activeSolutionDirectory.resolve("Program.fs")
    val stampBefore = getTimestamp(file)

    val newText = "namespace NewTextHere"
    changeFileContent(project, file) { newText }

    LocalFileSystem.getInstance().refresh(false)
    waitAndPump(
      project.lifetime,
      { getTimestamp(file) > stampBefore },
      Duration.ofSeconds(15000),
      { "Timestamp wasn't changed." })
    val stampAfter = getTimestamp(file)

    val (source, timestamp) = project.fcsHost.getSourceCache.sync(file.path).shouldNotBeNull("Couldn't get the source.")
    assert(source == newText) { "Source differs from new text." }
    assert(timestamp == stampAfter) { "Timestamp differs from expected." }
  }

  private fun getTimestamp(file: File) =
    project.fcsHost.getLastModificationStamp.sync(file.path)

}

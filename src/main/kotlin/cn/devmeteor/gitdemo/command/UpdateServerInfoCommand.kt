package cn.devmeteor.gitdemo.command

import cn.devmeteor.gitdemo.Config
import cn.devmeteor.gitdemo.command.exception.CommandFailException
import java.io.File

class UpdateServerInfoCommand private constructor() : Command<Unit> {

    companion object {
        fun newInstance() = UpdateServerInfoCommand()
    }

    private var repoPathFile: File? = null

    fun repoPath(path: String): UpdateServerInfoCommand = try {
        repoPath(File(path))
    } catch (e: Exception) {
        throw CommandFailException("仓库路径无效")
    }

    fun repoPath(path: File): UpdateServerInfoCommand {
        repoPathFile = path
        return this
    }

    override fun execute() {
        Config.getPlatform().execCommand("git update-server-info", repoPathFile)
    }
}
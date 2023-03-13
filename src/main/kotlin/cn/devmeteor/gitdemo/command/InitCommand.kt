package cn.devmeteor.gitdemo.command

import cn.devmeteor.gitdemo.Config
import cn.devmeteor.gitdemo.command.exception.CommandFailException
import java.io.File

class InitCommand private constructor() : Command<Unit> {

    companion object {
        fun newInstance() = InitCommand()
    }

    private var repoPathFile: File? = null

    fun repoPath(path: String): InitCommand = try {
        repoPath(File(path))
    } catch (e: Exception) {
        throw CommandFailException("仓库路径无效")
    }

    fun repoPath(path: File): InitCommand {
        repoPathFile = path
        return this
    }

    override fun execute() {
        Config.getPlatform().execCommand("git init --bare", repoPathFile)
    }
}
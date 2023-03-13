package cn.devmeteor.gitdemo.command

import cn.devmeteor.gitdemo.Config
import cn.devmeteor.gitdemo.command.exception.CommandFailException
import java.io.File

class InfoRefsCommand private constructor(): Command<String> {

    companion object {
        fun newInstance() = InfoRefsCommand()
    }

    private var repoPathFile: File? = null
    private var serviceName: String? = null

    fun repoPath(path: String): InfoRefsCommand = try {
        repoPath(File(path))
    } catch (e: Exception) {
        throw CommandFailException("仓库路径无效")
    }

    fun repoPath(path: File): InfoRefsCommand {
        repoPathFile = path
        return this
    }

    fun service(service: String):InfoRefsCommand {
        serviceName = service.removePrefix("git-")
        return this
    }

    override fun execute(): String {
        serviceName.checkArgNotNull("serviceName")
        return Config.getPlatform()
            .execCommand("git $serviceName --stateless-rpc  --advertise-refs .", repoPathFile)
    }
}
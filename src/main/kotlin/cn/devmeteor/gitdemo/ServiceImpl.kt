package cn.devmeteor.gitdemo

import cn.devmeteor.gitdemo.command.*
import java.io.File
import java.io.InputStream

@org.springframework.stereotype.Service
class ServiceImpl : Service {
    override fun createRepo(username: String, repoName: String) {
        val repoPath = "repos/$username/$repoName.git"
        val repoPathFile = File(repoPath)
        if (repoPathFile.exists()) {
            repoPathFile.deleteRecursively()
        }
        repoPathFile.mkdirs()
//        exec("git init --bare", repoPathFile)
        InitCommand.newInstance()
            .repoPath(repoPathFile)
            .execute()
//        exec("git update-server-info", repoPathFile)
        UpdateServerInfoCommand.newInstance()
            .repoPath(repoPathFile)
            .execute()
    }

    override fun getInfoRefs(user: String, repo: String, service: String): ByteArray {
        val repoPath = "repos/$user/$repo"
        val repoPathFile = repoPath.getValidRepoFile()
        val firstLine = getFirstLine(service)
        val infoRefs = InfoRefsCommand.newInstance()
            .repoPath(repoPathFile)
            .service(service)
            .execute()
        val res = firstLine + "0000" + infoRefs
        return res.toByteArray()
    }

    override fun receivePack(user: String, repo: String, input: InputStream): ByteArray {
        val repoPath = "repos/$user/$repo"
        repoPath.getValidRepoFile()
//        val cmd = "git receive-pack --stateless-rpc $repoPath"
//            val process = Runtime.getRuntime().exec(cmd)
//            process.outputStream.write(Objects.requireNonNull(input.readBytes()))
//            input.close()
//            process.outputStream.flush()
//            process.outputStream.close()
//            return process.inputStream.readBytes()
        return ReceivePackCommand.newInstance()
            .repoPath(repoPath)
            .input(input)
            .execute()
    }

    override fun uploadPack(user: String, repo: String, input: InputStream): ByteArray {
        val repoPath = "repos/$user/$repo"
        repoPath.getValidRepoFile()
//        val cmd = "git upload-pack --stateless-rpc $repoPath"
//            val process = Runtime.getRuntime().exec(cmd)
//            process.outputStream.write(Objects.requireNonNull(input.readBytes()))
//            input.close()
//            process.outputStream.flush()
//            process.outputStream.close()
//            return process.inputStream.readBytes()
        return UploadPackCommand.newInstance()
            .repoPath(repoPath)
            .input(input)
            .execute()
    }

    private fun getFirstLine(service: String): String {
        val main = "# service=$service\n"
        var len = (main.length + 4).toString(16)
        repeat(4 - len.length) {
            len = "0$len"
        }
        return len + main
    }

//    private fun exec(command: String, dir: File): String? {
//        try {
//            val process = Runtime.getRuntime().exec(command, null, dir)
//            val sb = StringBuilder()
//            val scanner = Scanner(process.inputStream)
//            while (scanner.hasNextLine()) {
//                val line = scanner.nextLine()
//                println(line)
//                sb.append(line).append("\n")
//            }
//            return sb.removeSuffix("\n").toString()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return null
//    }
}
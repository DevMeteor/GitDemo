package cn.devmeteor.gitdemo.command

import cn.devmeteor.gitdemo.RepoNotFoundException
import cn.devmeteor.gitdemo.command.exception.ArgNullException
import java.io.File

fun String.getValidRepoFile(): File {
    val file = File(this)
    if (!file.exists()) {
        throw RepoNotFoundException(this)
    }
    return file
}

fun Any?.checkArgNotNull(argName:String){
    if (this==null){
        throw ArgNullException(argName)
    }
}
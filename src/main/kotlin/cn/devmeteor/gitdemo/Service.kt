package cn.devmeteor.gitdemo

import java.io.InputStream

interface Service {
    fun createRepo(username: String, repoName: String)
    fun getInfoRefs(
        user: String,
        repo: String,
        service: String
    ): ByteArray
    fun receivePack(
        user: String,
        repo: String,
        input:InputStream
    ):ByteArray
    fun uploadPack(
        user: String,
        repo: String,
        input: InputStream
    ): ByteArray
}
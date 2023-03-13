package cn.devmeteor.gitdemo

import cn.devmeteor.gitdemo.gitprotocol.GitProtocol
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.CacheControl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
class Controller {

    @Autowired
    lateinit var gitService: Service

    @PostMapping("/repo/create")
    fun createRepo(username: String, repoName: String) {
        gitService.createRepo(username, repoName)
    }

    @GetMapping("/{user}/{repo}/info/refs")
    fun getInfoRefs(
        @PathVariable user: String,
        @PathVariable repo: String,
        service: String
    ): ResponseEntity<ByteArray> {
        val contentType = GitProtocol.getInfoRefsResponseContentType(service)
        return try {
            ResponseEntity.ok()
                .header("Content-Type", contentType)
                .cacheControl(CacheControl.noCache())
                .body(gitService.getInfoRefs(user, repo, service))
        } catch (repoNotFound: RepoNotFoundException) {
            ResponseEntity.notFound()
                .header("Content-Type", contentType)
                .cacheControl(CacheControl.noCache())
                .build()
        }
    }

    @PostMapping("/{user}/{repo}/git-receive-pack")
    fun receivePack(
        @PathVariable user: String,
        @PathVariable repo: String,
        request: HttpServletRequest
    ): ResponseEntity<ByteArray> {
        val contentType = GitProtocol.ReceivePackProtocol.responseContentType
        return try {
            ResponseEntity.ok()
                .header("Content-Type", contentType)
                .cacheControl(CacheControl.noCache())
                .body(gitService.receivePack(user, repo, request.inputStream))
        } catch (repoNotFound: RepoNotFoundException) {
            ResponseEntity.notFound()
                .header("Content-Type", contentType)
                .cacheControl(CacheControl.noCache())
                .build()
        }
    }

    @PostMapping("/{user}/{repo}/git-upload-pack")
    fun uploadPack(
        @PathVariable user: String,
        @PathVariable repo: String,
        request: HttpServletRequest
    ): ResponseEntity<ByteArray> {
        val contentType = GitProtocol.UploadPackProtocol.responseContentType
        return try {
            ResponseEntity.ok()
                .header("Content-Type", contentType)
                .cacheControl(CacheControl.noCache())
                .body(gitService.uploadPack(user, repo, request.inputStream))
        } catch (repoNotFound: RepoNotFoundException) {
            ResponseEntity.notFound()
                .header("Content-Type", contentType)
                .cacheControl(CacheControl.noCache())
                .build()
        }

    }

}
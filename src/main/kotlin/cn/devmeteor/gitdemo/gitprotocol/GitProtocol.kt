package cn.devmeteor.gitdemo.gitprotocol

enum class GitProtocol(
    val protocolName: String,
    val requestContentType: String? = null,
    val responseContentType: String? = null
) {

    InitProtocol("init"),

    UpdateServerInfoProtocol("update-server-info"),

    InfoRefsProtocol(
        "info-refs",
        responseContentType = "application/x-git-%s-advertisement"
    ),

    ReceivePackProtocol(
        "receive-pack",
        "application/x-git-receive-request",
        "application/x-git-receive-result"
    ),

    UploadPackProtocol(
        "upload-pack",
        "application/x-git-upload-request",
        "application/x-git-upload-result"
    );


    companion object {
        fun getProtocolByService(service: String): GitProtocol =
            with(service.removePrefix("git-")) {
                return@with when (this) {
                    InitProtocol.protocolName -> InitProtocol
                    UpdateServerInfoProtocol.protocolName -> UpdateServerInfoProtocol
                    InfoRefsProtocol.protocolName -> InfoRefsProtocol
                    ReceivePackProtocol.protocolName -> ReceivePackProtocol
                    UploadPackProtocol.protocolName -> UploadPackProtocol
                    else -> throw ProtocolNotFoundException(this)
                }
            }

        fun getProtocolByRequestContentType(requestContentType: String): GitProtocol =
            when (requestContentType) {
                ReceivePackProtocol.requestContentType -> ReceivePackProtocol
                UploadPackProtocol.requestContentType -> UploadPackProtocol
                else -> throw ProtocolNotFoundException(requestContentType)
            }

        fun getProtocolByResponseContentType(responseContentType: String, service: String? = null) {
            when (responseContentType) {
                service?.let { getInfoRefsResponseContentType(it) } -> InfoRefsProtocol
                ReceivePackProtocol.responseContentType -> ReceivePackProtocol
                UploadPackProtocol.responseContentType -> UploadPackProtocol
            }
        }

        fun getInfoRefsResponseContentType(service: String) =
            String.format(InfoRefsProtocol.responseContentType!!, service.removePrefix("git-"))

    }

}
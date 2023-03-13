package cn.devmeteor.gitdemo

import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class Interceptor:HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val sb = StringBuilder(1000);
        val url =request.requestURL.toString()
        if(!url.contains(".css") && !url.contains(".js") && !url.contains(".png") &&  !url.contains(".jpg")) {
            println("*******************访问地址："+url+"*******************");
            println("*******************客户端请求地址和操作端口：" + request.getRemoteAddr() + ":" + request.getRemotePort() + "*******************");
        }

        //获取请求参数
        val em = request.parameterNames;
        val data = mutableMapOf<String,String>();
        while (em.hasMoreElements()) {
            val name = em.nextElement() as String
            val value = request.getParameter(name).toString()
            data[name] = value;
        }
        sb .append("-------------------------------------------------------------\n")
        val headerNames = request.headerNames
        sb.append("Cookies   : ").append("\n")
        request.cookies?.forEach {
            sb.append(it.name).append(" ").append(it.value).append("\n")
        }
        sb.append("Headers   : ").append("\n")
        while(headerNames.hasMoreElements()){
            val key=headerNames.nextElement()
            sb.append(key," ",request.getHeader(key)).append("\n")
        }
        sb.append("Params    : ").append(data).append("\n");
        sb.append("URI       : ").append(request.requestURI).append("\n");
        sb.append("URL       : ").append(request.requestURL).append("\n");
        sb .append("-------------------------------------------------------------\n");
        println(sb.toString());
        return true
    }
}
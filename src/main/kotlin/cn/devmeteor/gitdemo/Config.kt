package cn.devmeteor.gitdemo

import cn.devmeteor.gitdemo.systemplatform.SystemPlatform

object Config {

    private var systemPlatformType = SystemPlatform.Type.TypeWindows

    fun getPlatform(): SystemPlatform = systemPlatformType.systemPlatform

}
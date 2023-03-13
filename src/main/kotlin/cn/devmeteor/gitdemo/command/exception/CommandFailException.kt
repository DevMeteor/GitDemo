package cn.devmeteor.gitdemo.command.exception

class CommandFailException(command:String):Exception("命令执行失败:$command")
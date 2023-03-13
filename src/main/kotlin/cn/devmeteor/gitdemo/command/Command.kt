package cn.devmeteor.gitdemo.command

interface Command<R> {

    fun execute(): R

}
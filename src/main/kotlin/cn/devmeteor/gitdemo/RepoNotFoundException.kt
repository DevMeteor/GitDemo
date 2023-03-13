package cn.devmeteor.gitdemo

class RepoNotFoundException(repoPath:String) : Exception("$repoPath:仓库不存在")
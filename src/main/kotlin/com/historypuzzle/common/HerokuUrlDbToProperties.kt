package com.historypuzzle.common


object HerokuUrlDbToProperties {
    fun extractDbProperties(url: String?): List<String> {
        if (url?.isEmpty() != false) {
            return emptyList()
        }
        val regex = "postgres://(?<username>[^:]+):(?<password>[^:]+)@(?<serverName>[^:]+):(?<portNumber>[0-9]+)/(?<databaseName>.+)".toRegex()

        val matches = regex.matchEntire(url)
        check(matches != null) { "Incorrect database url format" }

        val groups = matches.groups as MatchNamedGroupCollection

        val username = groups["username"]!!.value
        val password = groups["password"]!!.value
        val serverName = groups["serverName"]!!.value
        val portNumber = groups["portNumber"]!!.value
        val databaseName = groups["databaseName"]!!.value


        return listOf(
                "database.user=$username",
                "database.password=$password",
                "database.host=$serverName",
                "database.port=$portNumber",
                "database.db=$databaseName"
        )
    }
}

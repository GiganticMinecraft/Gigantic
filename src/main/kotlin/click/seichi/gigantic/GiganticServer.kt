package click.seichi.gigantic

/**
 * @author tar0ss
 */
enum class GiganticServer(
        val id: Int,
        val bungeeName: String
) {
    SPRING_ONE(1, "h1")
    ;

    companion object {
        val idMap = values().map { it.id to it }.toMap()
        val bungeeNameMap = values().map { it.bungeeName to it }.toMap()

        fun findById(id: Int) = idMap[id]
        fun findByBungeeName(bungeeName: String) = bungeeNameMap[bungeeName]

    }
}
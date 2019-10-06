package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object ServerConfig : SimpleConfiguration("server") {

    val BUNGEE_NAME by lazy { getString("bungee_name")!! }
}
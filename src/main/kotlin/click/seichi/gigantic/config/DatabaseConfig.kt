package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object DatabaseConfig : SimpleConfiguration("database") {

    val HOST: String by lazy { getString("host") }

    val DATABASE: String by lazy { getString("database") }

    val USER: String by lazy { getString("user") }

    val PASSWORD: String by lazy { getString("password") }
}
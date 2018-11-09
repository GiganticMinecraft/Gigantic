package click.seichi.gigantic.battle

/**
 * @author tar0ss
 */
interface BattleMonster {

    val health: Long

    val maxHealth: Long

    val attack: Long

    val speed: Double

    fun onTurn()

    fun isDead() = health <= 0L
}
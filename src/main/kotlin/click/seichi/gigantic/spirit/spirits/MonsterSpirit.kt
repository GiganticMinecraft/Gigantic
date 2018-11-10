package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class MonsterSpirit(
        spawnReason: SpawnReason,
        location: Location,
        val monster: SoulMonster,
        targetPlayer: Player? = null
) : Spirit(spawnReason, location.chunk) {

    // TODO implements

    override val lifespan = 20 * 60
    override val spiritType = SpiritType.MONSTER


}
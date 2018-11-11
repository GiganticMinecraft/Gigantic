package click.seichi.gigantic.monster.ai.ais

import click.seichi.gigantic.monster.ai.SoulMonsterAI
import org.bukkit.Location

/**
 * @author tar0ss
 */
class SimpleSoulMonsterAI : SoulMonsterAI {


    override fun getMovementSpeed(currentLocation: Location, nextLocation: Location): Double {
        return 0.1
    }
}
package click.seichi.gigantic.skill.breakskill

import click.seichi.gigantic.message.lang.skill.BreakSkillLang
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.util.Box
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
class Explosion : BreakSkill() {

    override val shortName = BreakSkillLang.EXPLOSION_SHORT_NAME
    override val displayName = BreakSkillLang.EXPLOSION_LONG_NAME

    override fun calcConsumeMana(n: Int) = (n / Math.pow(n.toDouble(), 0.2) - 1).toInt()

    override fun calcConsumeDurability(n: Int) = n

    override fun calcCoolTime(n: Int) = ((Math.pow(n.toDouble(), 0.25) - 1) * 20).toLong()
            .let { if (it < 20) 0 else it }

    override fun calcBox(gPlayer: GiganticPlayer) =
            gPlayer.status.explosionLevel.let { level ->
                when (level) {
                    1 -> Box(1, 2, 1)
                    2 -> Box(3, 2, 1)
                    3 -> Box(3, 3, 3)
                    in 4..9 -> Box(
                            (level - 2) * 2 + 1,
                            (level - 3) * 2 + 1,
                            (level - 2) * 2 + 1
                    )
                    else -> throw IllegalArgumentException()
                }
            }

    override fun isUnlocked(gPlayer: GiganticPlayer) = gPlayer.status.seichiLevel.current >= 10
    // TODO implements
    override fun isCooldown(gPlayer: GiganticPlayer) = false

    override fun getConsumeTool(gPlayer: GiganticPlayer): ItemStack = gPlayer.player.inventory.itemInMainHand
    // TODO implements
    override fun isActive(gPlayer: GiganticPlayer): Boolean = true

    override fun getStyle(gPlayer: GiganticPlayer): BreakStyle = BreakStyle.NORMAL
}
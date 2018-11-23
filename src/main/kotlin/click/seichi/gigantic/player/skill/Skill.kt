package click.seichi.gigantic.player.skill

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.SkillMessages
import click.seichi.gigantic.player.Invokable
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
enum class Skill(
        val id: Int,
        val slot: Int,
        private val icon: ItemStack,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText>?,
        private val achievement: Achievement?,
        private val invoker: Invokable
) {
    FLASH(
            1,
            1,
            ItemStack(Material.FEATHER),
            SkillMessages.FLASH,
            SkillMessages.FLASH_LORE,
            Achievement.SKILL_FLASH,
            Skills.FLASH
    ),
    HEAL(
            2,
            0,
            ItemStack(Material.ROSE_RED),
            SkillMessages.HEAL,
            SkillMessages.HEAL_LORE,
            null,
            Skills.HEAL
    ),
    MINE_BURST(
            3,
            2,
            ItemStack(Material.BLAZE_POWDER),
            SkillMessages.MINE_BURST,
            SkillMessages.MINE_BURST_LORE,
            Achievement.SKILL_MINE_BURST,
            Skills.MINE_BURST
    ),
    KODAMA_DRAIN(
            4,
            3,
            ItemStack(Material.OAK_WOOD),
            SkillMessages.KODAMA_DRAIN,
            SkillMessages.KODAMA_DRAIN_LORE,
            Achievement.SKILL_KODAMA_DRAIN,
            Skills.KODAMA_DRAIN
    )
    ;

    fun tryCast(player: Player) = if (isGranted(player)) invoker.tryInvoke(player) else false

    fun isGranted(player: Player) = achievement?.isGranted(player) ?: true

    // 解禁条件を表示するためにplayerを引数に
    fun getIcon(player: Player) = icon.clone()

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore?.map { it.asSafety(locale) }

}
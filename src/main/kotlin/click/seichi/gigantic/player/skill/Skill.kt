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

        // スキル一覧メニューで使用
        val slot: Int,
        private val icon: ItemStack,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText>?,
        // スキル発動に必要な実績
        private val achievement: Achievement?,
        // 発動
        private val invoker: Invokable
) {
    FLASH(
            1,
            2,
            ItemStack(Material.FEATHER),
            SkillMessages.FLASH,
            SkillMessages.FLASH_LORE,
            Achievement.SKILL_FLASH,
            Skills.FLASH
    ),
    HEAL(
            2,
            0,
            ItemStack(Material.RED_DYE),
            SkillMessages.HEAL,
            SkillMessages.HEAL_LORE,
            null,
            Skills.HEAL
    ),
    MINE_BURST(
            3,
            3,
            ItemStack(Material.BLAZE_POWDER),
            SkillMessages.MINE_BURST,
            SkillMessages.MINE_BURST_LORE,
            Achievement.SKILL_MINE_BURST,
            Skills.MINE_BURST
    ),
    MINE_COMBO(
            4,
            1,
            ItemStack(Material.MAGMA_CREAM),
            SkillMessages.MINE_COMBO,
            SkillMessages.MINE_COMBO_LORE,
            Achievement.SKILL_MINE_COMBO,
            Skills.MINE_COMBO
    ),
    JUMP(
            5,
            5,
            ItemStack(Material.PHANTOM_MEMBRANE),
            SkillMessages.JUMP,
            SkillMessages.JUMP_LORE,
            Achievement.SKILL_JUMP,
            Skills.JUMP
    ),
    FOCUS_TOTEM(
            6,
            4,
            ItemStack(Material.TOTEM_OF_UNDYING),
            SkillMessages.FOCUS_TOTEM,
            SkillMessages.FOCUS_TOTEM_LORE,
            Achievement.SKILL_FOCUS_TOTEM,
            Skills.FOCUS_TOTEM
    )
    ;

    companion object {
        // 重複確認
        val hasDuplicateId = values().size != values().map { it.id }.toSet().size
    }

    fun tryCast(player: Player) = if (isGranted(player)) invoker.tryInvoke(player) else false

    fun isGranted(player: Player) = achievement?.isGranted(player) ?: true

    fun getIcon(player: Player) = icon.clone()

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore?.map { it.asSafety(locale) }

}
package click.seichi.gigantic.effect

import click.seichi.gigantic.Currency
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.effect.effector.GeneralBreakEffector
import click.seichi.gigantic.effect.effector.MultiBreakEffector
import click.seichi.gigantic.effect.effectors.GeneralBreakEffectors
import click.seichi.gigantic.effect.effectors.MultiBreakEffectors
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.toRainbow
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.EffectMessages
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 *
 * TODO: refactor
 */
enum class GiganticEffect(val uniqueId: Int,
                          private val _icon: ItemStack,
                          val currency: Currency,
                          val requireAmount: Int,
                          val displayName: String,
                          val description: String,
                          private val localizedName: LocalizedText,
                          private val localizedLore: Set<LocalizedText>,
                          private val generalEffector: GeneralBreakEffector? = null,
                          private val multiEffector: MultiBreakEffector? = null) {

    DEFAULT(
            0,
            ItemStack(Material.GRASS_BLOCK),
            Currency.DEFAULT,
            0,
            "${ChatColor.WHITE}${ChatColor.BOLD}通常エフェクト",
            """
                ${ChatColor.WHITE}マイクラの通常エフェクト
            """.trimIndent(),
            EffectMessages.DEFAULT,
            EffectMessages.DEFAULT_LORE,
            GeneralBreakEffectors.DEFAULT,
            MultiBreakEffectors.DEFAULT
    ),
    EXPLOSION(
            1,
            ItemStack(Material.TNT),
            Currency.VOTE_POINT,
            50,
            "${ChatColor.RED}${ChatColor.BOLD}エクスプロージョン",
            """
                ${ChatColor.WHITE}爆発する
            """.trimIndent(),
            EffectMessages.EXPLOSION,
            EffectMessages.EXPLOSION_LORE,
            GeneralBreakEffectors.EXPLOSION,
            MultiBreakEffectors.EXPLOSION
    ),
    BLIZZARD(
            2,
            ItemStack(Material.PACKED_ICE),
            Currency.VOTE_POINT,
            70,
            "${ChatColor.AQUA}${ChatColor.BOLD}ブリザード",
            """
                ${ChatColor.WHITE}凍結する
            """.trimIndent(),
            EffectMessages.BLIZZARD,
            EffectMessages.BLIZZARD_LORE,
            generalEffector = GeneralBreakEffectors.BLIZZARD,
            multiEffector = MultiBreakEffectors.BLIZZARD
    ),
    MAGIC(
            3,
            ItemStack(Material.RED_WOOL),
            Currency.DONATE_POINT,
            50,
            "マジック".toRainbow(true),
            """
                ${"カラフルな羊毛に変身する".toRainbow()}
            """.trimIndent(),
            EffectMessages.MAGIC,
            EffectMessages.MAGIC_LORE,
            generalEffector = GeneralBreakEffectors.MAGIC,
            multiEffector = MultiBreakEffectors.MAGIC
    ),
    FLAME(
            4,
            ItemStack(Material.NETHER_WART),
            Currency.VOTE_POINT,
            30,
            "${ChatColor.GOLD}${ChatColor.BOLD}フレイム",
            """
                ${ChatColor.WHITE}燃え散る
            """.trimIndent(),
            EffectMessages.FLAME,
            EffectMessages.FLAME_LORE,
            generalEffector = GeneralBreakEffectors.FLAME,
            multiEffector = MultiBreakEffectors.FLAME
    ),
    WITCH_SCENT(
            5,
            ItemStack(Material.CHORUS_PLANT),
            Currency.DONATE_POINT,
            30,
            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}魔女の残り香",
            """
                ${ChatColor.WHITE}心安らぐ香り
            """.trimIndent(),
            EffectMessages.WITCH_SCENT,
            EffectMessages.WITCH_SCENT_LORE,
            generalEffector = GeneralBreakEffectors.WITCH_SCENT,
            multiEffector = MultiBreakEffectors.WITCH_SCENT
    ),
    SLIME(
            6,
            ItemStack(Material.SLIME_BALL),
            Currency.DONATE_POINT,
            10,
            "${ChatColor.GREEN}${ChatColor.BOLD}スライム",
            """
                ${ChatColor.WHITE}転生したらスライムだった件
            """.trimIndent(),
            EffectMessages.SLIME,
            EffectMessages.SLIME_LORE,
            generalEffector = GeneralBreakEffectors.SLIME,
            multiEffector = MultiBreakEffectors.SLIME
    ),
    BUBBLE(
            7,
            ItemStack(Material.TUBE_CORAL),
            Currency.DONATE_POINT,
            10,
            "${ChatColor.AQUA}${ChatColor.BOLD}泡沫の夢",
            """
                ${ChatColor.WHITE}Σ(‘A`)ハッ！なんだ夢か...
            """.trimIndent(),
            EffectMessages.BUBBLE,
            EffectMessages.BUBBLE_LORE,
            multiEffector = MultiBreakEffectors.BUBBLE
    ),
    ALCHEMIA(
            8,
            ItemStack(Material.REDSTONE),
            Currency.DONATE_POINT,
            20,
            "${ChatColor.GREEN}${ChatColor.BOLD}アルケミア",
            """
                ${ChatColor.WHITE}禁忌に触れるもの
            """.trimIndent(),
            EffectMessages.ALCHEMIA,
            EffectMessages.ALCHEMIA_LORE,
            multiEffector = MultiBreakEffectors.ALCHEMIA
    ),

    ;

    companion object {
        private val idMap = values().map { it.uniqueId to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }

    val hasGeneralBreakEffect = generalEffector != null

    val hasMultiBreakEffect = multiEffector != null

    val icon: ItemStack
        get() = _icon.clone()

    val lore: String
        get() = "$displayName\n${description.trimIndent()}"

    fun generalBreak(player: Player, block: Block) {
        (generalEffector ?: DEFAULT.generalEffector)!!.generalBreak(player, block)
    }

    fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
        (multiEffector ?: DEFAULT.multiEffector)!!.multiBreak(player, base, breakBlockSet)
    }

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore.map { it.asSafety(locale) }

    // 購入しているか
    fun isBought(player: Player) = (Config.DEBUG_MODE && DebugConfig.EFFECT_UNLOCK) || player.getOrPut(Keys.EFFECT_BOUGHT_MAP[this]!!)

    // 購入した日付
    fun boughtAt(player: Player) = player.getOrPut(Keys.EFFECT_BOUGHT_TIME_MAP[this]!!)

    // 購入
    fun buy(player: Player) {
        player.offer(Keys.EFFECT_BOUGHT_MAP.getValue(this), true)
        player.offer(Keys.EFFECT_BOUGHT_TIME_MAP.getValue(this), DateTime.now())
    }

    // 購入可能か
    fun canBuy(player: Player): Boolean {
        if (isBought(player)) return false
        return currency.calcRemainAmount(player) >= requireAmount
    }

    // 選択中か
    fun isSelected(player: Player) = player.getOrPut(Keys.EFFECT) == this

    // 選択する　
    fun select(player: Player) = player.offer(Keys.EFFECT, this)

}
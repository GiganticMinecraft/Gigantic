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
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.EffectMessages
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
enum class GiganticEffect(
        // 変更禁止 id=0はデフォルトエフェクトに対応
        val id: Int,
        // エフェクトメニューに配置されるスロット
        val slot: Int,
        // エフェクトメニューに表示されるItemStack
        private val icon: ItemStack,
        // 購入方法
        val currency: Currency,
        // 必要ポイント
        val amount: Int,
        // 名前
        private val localizedName: LocalizedText,
        // 説明文
        private val localizedLore: Set<LocalizedText>,
        // 通常破壊のエフェクト
        private val generalEffector: GeneralBreakEffector? = null,
        // マルチブレイクのエフェクト
        private val multiEffector: MultiBreakEffector? = null
) {
    DEFAULT(
            0,
            // DEFAULT のみ無意味な値
            0,
            ItemStack(Material.GRASS_BLOCK),
            Currency.DEFAULT,
            // DEFAULT のみ無意味な値
            0,
            EffectMessages.DEFAULT,
            EffectMessages.DEFAULT_LORE,
            GeneralBreakEffectors.DEFAULT,
            MultiBreakEffectors.DEFAULT

    ),
    EXPLOSION(
            1,
            1,
            ItemStack(Material.TNT),
            Currency.VOTE_POINT,
            50,
            EffectMessages.EXPLOSION,
            EffectMessages.EXPLOSION_LORE,
            GeneralBreakEffectors.EXPLOSION,
            MultiBreakEffectors.EXPLOSION
    ),
    BLIZZARD(
            2,
            2,
            ItemStack(Material.PACKED_ICE),
            Currency.VOTE_POINT,
            70,
            EffectMessages.BLIZZARD,
            EffectMessages.BLIZZARD_LORE,
            generalEffector = GeneralBreakEffectors.BLIZZARD,
            multiEffector = MultiBreakEffectors.BLIZZARD
    ),
    MAGIC(
            3,
            3 + 9,
            Head.RAINBOW_WOOL.toItemStack(),
            Currency.DONATE_POINT,
            50,
            EffectMessages.MAGIC,
            EffectMessages.MAGIC_LORE,
            generalEffector = GeneralBreakEffectors.MAGIC,
            multiEffector = MultiBreakEffectors.MAGIC
    ),
    FLAME(
            4,
            0,
            ItemStack(Material.NETHER_WART),
            Currency.VOTE_POINT,
            30,
            EffectMessages.FLAME,
            EffectMessages.FLAME_LORE,
            generalEffector = GeneralBreakEffectors.FLAME,
            multiEffector = MultiBreakEffectors.FLAME
    ),
    WITCH_SCENT(
            5,
            2 + 9,
            ItemStack(Material.ENDER_PEARL),
            Currency.DONATE_POINT,
            30,
            EffectMessages.WITCH_SCENT,
            EffectMessages.WITCH_SCENT_LORE,
            generalEffector = GeneralBreakEffectors.WITCH_SCENT,
            multiEffector = MultiBreakEffectors.WITCH_SCENT
    ),
    SLIME(
            6,
            0 + 9,
            ItemStack(Material.SLIME_BALL),
            Currency.DONATE_POINT,
            10,
            EffectMessages.SLIME,
            EffectMessages.SLIME_LORE,
            generalEffector = GeneralBreakEffectors.SLIME,
            multiEffector = MultiBreakEffectors.SLIME
    ),
    BUBBLE(
            7,
            1 + 9,
            ItemStack(Material.TUBE_CORAL),
            Currency.DONATE_POINT,
            10,
            EffectMessages.BUBBLE,
            EffectMessages.BUBBLE_LORE,
            multiEffector = MultiBreakEffectors.BUBBLE
    )

    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }

    val hasGeneralBreakEffect = generalEffector != null

    val hasMultiBreakEffect = multiEffector != null

    fun generalBreak(player: Player, block: Block) {
        (generalEffector ?: DEFAULT.generalEffector)!!.generalBreak(player, block)
    }

    fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
        (multiEffector ?: DEFAULT.multiEffector)!!.multiBreak(player, base, breakBlockSet)
    }

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore.map { it.asSafety(locale) }

    fun getIcon() = icon.clone()

    // 購入しているか
    fun isBought(player: Player) = (Config.DEBUG_MODE && DebugConfig.EFFECT_UNLOCK) || player.getOrPut(Keys.EFFECT_BOUGHT_MAP[this]!!)

    // 購入した日付
    fun boughtAt(player: Player) = player.getOrPut(Keys.EFFECT_BOUGHT_TIME_MAP[this]!!)

    // 購入
    fun buy(player: Player) {
        player.offer(Keys.EFFECT_BOUGHT_MAP[this]!!, true)
    }

    // 購入可能か
    fun canBuy(player: Player): Boolean {
        if (isBought(player)) return false
        return currency.calcRemainAmount(player) >= amount
    }

    // 選択中か
    fun isSelected(player: Player) = player.getOrPut(Keys.EFFECT) == this

    // 選択する　
    fun select(player: Player) = player.offer(Keys.EFFECT, this)

}
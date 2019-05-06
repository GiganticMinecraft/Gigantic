package click.seichi.gigantic.effect

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.effect.effector.GeneralBreakEffector
import click.seichi.gigantic.effect.effector.MultiBreakEffector
import click.seichi.gigantic.effect.effectors.GeneralBreakEffectors
import click.seichi.gigantic.effect.effectors.MultiBreakEffectors
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.EffectMessages
import click.seichi.gigantic.product.Product
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
        // 商品
        val product: Product?,
        // エフェクトメニューに表示されるItemStack
        private val icon: ItemStack,
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
            null,
            ItemStack(Material.GRASS_BLOCK),
            EffectMessages.DEFAULT,
            EffectMessages.DEFAULT_LORE,
            GeneralBreakEffectors.DEFAULT,
            MultiBreakEffectors.DEFAULT

    ),
    EXPLOSION(
            1,
            Product.EXPLOSION,
            ItemStack(Material.TNT),
            EffectMessages.EXPLOSION,
            EffectMessages.EXPLOSION_LORE,
            GeneralBreakEffectors.EXPLOSION,
            MultiBreakEffectors.EXPLOSION
    ),
    BLIZZARD(
            2,
            Product.BLIZZARD,
            ItemStack(Material.PACKED_ICE),
            EffectMessages.BLIZZARD,
            EffectMessages.BLIZZARD_LORE,
            generalEffector = GeneralBreakEffectors.BLIZZARD,
            multiEffector = MultiBreakEffectors.BLIZZARD
    ),
    MAGIC(
            3,
            Product.MAGIC,
            ItemStack(Material.RED_WOOL),
            EffectMessages.MAGIC,
            EffectMessages.MAGIC_LORE,
            generalEffector = GeneralBreakEffectors.MAGIC,
            multiEffector = MultiBreakEffectors.MAGIC
    ),
    FLAME(
            4,
            Product.FLAME,
            ItemStack(Material.NETHER_WART),
            EffectMessages.FLAME,
            EffectMessages.FLAME_LORE,
            generalEffector = GeneralBreakEffectors.FLAME,
            multiEffector = MultiBreakEffectors.FLAME
    ),
    WITCH_SCENT(
            5,
            Product.WITCH_SCENT,
            ItemStack(Material.CHORUS_PLANT),
            EffectMessages.WITCH_SCENT,
            EffectMessages.WITCH_SCENT_LORE,
            generalEffector = GeneralBreakEffectors.WITCH_SCENT,
            multiEffector = MultiBreakEffectors.WITCH_SCENT
    ),
    SLIME(
            6,
            Product.SLIME,
            ItemStack(Material.SLIME_BALL),
            EffectMessages.SLIME,
            EffectMessages.SLIME_LORE,
            generalEffector = GeneralBreakEffectors.SLIME,
            multiEffector = MultiBreakEffectors.SLIME
    ),
    BUBBLE(
            7,
            Product.BUBBLE,
            ItemStack(Material.TUBE_CORAL),
            EffectMessages.BUBBLE,
            EffectMessages.BUBBLE_LORE,
            multiEffector = MultiBreakEffectors.BUBBLE
    ),
    ALCHEMIA(
            8,
            Product.ALCHEMIA,
            ItemStack(Material.REDSTONE),
            EffectMessages.ALCHEMIA,
            EffectMessages.ALCHEMIA_LORE,
            multiEffector = MultiBreakEffectors.ALCHEMIA
    ),

    ;

    companion object {
        // 重複確認
        val hasDuplicateId = values().size != values().map { it.id }.toSet().size

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

    fun canSelect(player: Player) = (Config.DEBUG_MODE && DebugConfig.EFFECT_UNLOCK) ||
            this == DEFAULT ||
            product?.boughtAmount(player) ?: 0 > 0

    // 選択中か
    fun isSelected(player: Player) = player.getOrPut(Keys.EFFECT) == this

    // 選択する　
    fun select(player: Player) = player.offer(Keys.EFFECT, this)

}
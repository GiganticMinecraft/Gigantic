package click.seichi.gigantic.player

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.EffectMessages
import org.bukkit.Material
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
        private val localizedLore: Set<LocalizedText>
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
            EffectMessages.DEFAULT_LORE
    ),
    EXPLOSION(
            1,
            0,
            ItemStack(Material.TNT),
            Currency.VOTE_POINT,
            50,
            EffectMessages.EXPLOSION,
            EffectMessages.EXPLOSION_LORE
    ),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
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
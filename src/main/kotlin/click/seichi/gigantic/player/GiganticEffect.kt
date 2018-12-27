package click.seichi.gigantic.player

import click.seichi.gigantic.cache.key.Keys
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
        val buyType: BuyType,
        // 必要ポイント
        val amount: Int,
        // 名前
        val localizedName: LocalizedText,
        // 説明文
        val localizedLore: Set<LocalizedText>
) {
    DEFAULT(
            0,
            // DEFAULT のみ無意味な値
            0,
            ItemStack(Material.GRASS_BLOCK),
            BuyType.DEFAULT,
            // DEFAULT のみ無意味な値
            0,
            EffectMessages.DEFAULT,
            EffectMessages.DEFAULT_LORE
    ),
    EXPLOSION(
            1,
            0,
            ItemStack(Material.TNT),
            BuyType.VOTE_POINT,
            50,
            EffectMessages.EXPLOSION,
            EffectMessages.EXPLOSION_LORE
    ),
    ;

    enum class BuyType {
        // マイクラデフォ
        DEFAULT,
        // 投票
        VOTE_POINT,
        // Spade 通貨
        POMME,
        // 寄付金
        DONATE_POINT,
    }


    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore.map { it.asSafety(locale) }

    fun getIcon() = icon.clone()

    // 購入しているか
    fun isBought(player: Player) = player.getOrPut(Keys.EFFECT_BOUGHT_MAP[this]!!)

    // 購入した日付
    fun boughtAt(player: Player) = player.getOrPut(Keys.EFFECT_BOUGHT_TIME_MAP[this]!!)

    // 購入
    fun buy(player: Player) {
        // TODO implements
        player.offer(Keys.EFFECT_BOUGHT_MAP[this]!!, true)
    }

    // 購入可能か
    fun canBuy(player: Player): Boolean {
        // TODO implements
        return true
    }

}
package click.seichi.gigantic.player

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.EffectMessages

/**
 * @author tar0ss
 */
enum class GiganticEffect(
        val id: Int,
        // ショップに配置されるスロット
        val slot: Int,
        // 購入方法
        val buyType: BuyType,
        // 必要ポイント
        val amount: Int,
        // 名前
        val localizedText: LocalizedText,
        // 説明文
        val lore: Set<LocalizedText>
) {
    EXPLOSION(
            1,
            0,
            BuyType.VOTE_POINT,
            50,
            EffectMessages.EXPLOSION,
            EffectMessages.EXPLOSION_LORE
    ),
    ;

    enum class BuyType {
        // 投票
        VOTE_POINT,
        // Spade 通貨
        POMME,
        // 寄付金
        DONATE_POINT,
    }
}
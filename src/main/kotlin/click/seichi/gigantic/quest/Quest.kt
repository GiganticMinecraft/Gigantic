package click.seichi.gigantic.quest

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.soul.SoulMonster
import java.util.*

/**
 * @author tar0ss
 */
enum class Quest(
        val id: Int,
        val title: LocalizedText,
        vararg monsters: SoulMonster
) {
    LADON(
            100,
            LocalizedText(
                    Locale.JAPANESE to "金の森を守りしもの"
            ),
            SoulMonster.LADON
    ),
    UNDINE(
            200,
            LocalizedText(
                    Locale.JAPANESE to "水を操りしもの"
            ),
            SoulMonster.UNDINE
    ),
    SALAMANDRA(
            300,
            LocalizedText(
                    Locale.JAPANESE to "火を操りしもの"
            ),
            SoulMonster.SALAMANDRA
    ),
    SYLPHID(
            400,
            LocalizedText(
                    Locale.JAPANESE to "風を操りしもの"
            ),
            SoulMonster.SYLPHID
    ),
    NOMOS(
            500,
            LocalizedText(
                    Locale.JAPANESE to "土を操りしもの"
            ),
            SoulMonster.NOMOS
    ),
    LOA(
            600,
            LocalizedText(
                    Locale.JAPANESE to "自然を操りしもの"
            ),
            SoulMonster.LOA
    ),
    ;

    val monsterSet = monsters.toSet()
}
package click.seichi.gigantic.soul

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.MonsterMessages

/**
 * @author tar0ss
 */
enum class SoulMonster(
        val id: Int,
        val localizedName: LocalizedText,
        val localizedLore: List<LocalizedText>?
) {
    LADON(100, MonsterMessages.LADON, null),
    UNDINE(200, MonsterMessages.UNDINE, null),
    SALAMANDRA(300, MonsterMessages.SALAMANDRA, null),
    SYLPHID(400, MonsterMessages.SYLPHID, null),
    NOMOS(500, MonsterMessages.NOMOS, null),
    LOA(600, MonsterMessages.LOA, null)
    ;

}
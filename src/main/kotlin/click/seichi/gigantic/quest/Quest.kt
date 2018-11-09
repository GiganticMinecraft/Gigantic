package click.seichi.gigantic.quest

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.QuestMessages
import click.seichi.gigantic.soul.SoulMonster
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class Quest(
        val id: Int,
        val localizedName: LocalizedText,
        val localizedLore: List<LocalizedText>?,
        vararg monsters: SoulMonster
) {
    LADON(100, QuestMessages.LADON, null, SoulMonster.LADON),
    UNDINE(200, QuestMessages.UNDINE, null, SoulMonster.UNDINE),
    SALAMANDRA(300, QuestMessages.SALAMANDRA, null, SoulMonster.SALAMANDRA),
    SYLPHID(400, QuestMessages.SYLPHID, null, SoulMonster.SYLPHID),
    NOMOS(500, QuestMessages.NOMOS, null, SoulMonster.NOMOS),
    LOA(600, QuestMessages.LOA, null, SoulMonster.LOA),
    ;

    val monsterList = monsters.toList()

    companion object {
        val COLOR = ChatColor.LIGHT_PURPLE

        fun getClientList(player: Player) =
                values().mapNotNull { player.getOrPut(Keys.QUEST_MAP[it] ?: return@mapNotNull null) }

        fun getOrderedClientList(player: Player) =
                getClientList(player).filter { it.isOrdered }
    }


    fun order(player: Player) {
        player.getOrPut(Keys.QUEST_MAP[this] ?: return)?.order()
    }

    fun isOrdered(player: Player): Boolean {
        val questKey = Keys.QUEST_MAP[this] ?: return false
        return player.getOrPut(questKey)?.isOrdered ?: false
    }

}
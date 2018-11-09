package click.seichi.gigantic.quest

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.QuestMessages
import click.seichi.gigantic.monster.SoulMonster
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
enum class Quest(
        val id: Int,
        val localizedName: LocalizedText,
        val localizedLore: List<LocalizedText>?,
        vararg monsters: SoulMonster
) {
    PIG_CROWD(0, QuestMessages.PIG, null, SoulMonster.PIG, SoulMonster.PIG_WARRIOR, SoulMonster.MR_PIG),
    LADON(100, QuestMessages.LADON, null, SoulMonster.LADON),
    UNDINE(200, QuestMessages.UNDINE, null, SoulMonster.UNDINE),
    SALAMANDRA(300, QuestMessages.SALAMANDRA, null, SoulMonster.SALAMANDRA),
    SYLPHID(400, QuestMessages.SYLPHID, null, SoulMonster.SYLPHID),
    NOMOS(500, QuestMessages.NOMOS, null, SoulMonster.NOMOS),
    LOA(600, QuestMessages.LOA, null, SoulMonster.LOA),
    ;

    companion object {
        val COLOR = ChatColor.LIGHT_PURPLE

        fun getOrderedList(player: Player) =
                values().filter { it.isOrdered(player) }
    }

    val monsterList = monsters.toList()

    // クエスト発注
    fun order(player: Player) {
        getClient(player)?.run {
            if (isOrdered) return
            isOrdered = true
            orderedAt = DateTime.now()
        }
    }

    fun isOrdered(player: Player): Boolean {
        return getClient(player)?.isOrdered ?: false
    }

    // クエスト進行
    fun process(player: Player, degree: Int) {
        getClient(player)?.run {
            isProcessed = true
            processedDegree = degree
        }
    }

    // クエスト完了
    fun complete(player: Player) {
        getClient(player)?.run {
            isOrdered = false
            isProcessed = false
            processedDegree = 0
        }
    }

    fun getClient(player: Player): QuestClient? {
        val questKey = Keys.QUEST_MAP[this] ?: return null
        return player.getOrPut(questKey)
    }
}
package click.seichi.gigantic.quest

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.QuestMessages
import click.seichi.gigantic.monster.SoulMonster
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
enum class Quest(
        val id: Int,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText>?,
        vararg monsters: SoulMonster
) {
    PIG(
            0,
            QuestMessages.PIG,
            null,
            SoulMonster.PIG,
            SoulMonster.PIG_WARRIOR,
            SoulMonster.MR_PIG
    ),
    BLAZE(
            1,
            QuestMessages.BLAZE,
            null,
            SoulMonster.BLAZE,
            SoulMonster.BLAZE_WARRIOR,
            SoulMonster.BLUE_BLAZE
    ),
    CHICKEN(
            2,
            QuestMessages.CHICKEN,
            null,
            SoulMonster.CHICKEN,
            SoulMonster.CHICKEN_KING
    ),
    WITHER(
            3,
            QuestMessages.WITHER,
            null,
            SoulMonster.WITHER_SKELETON,
            SoulMonster.WITHER_SKELETON,
            SoulMonster.WITHER_SKELETON,
            SoulMonster.WITHER
    ),
    BEGIN(
            4,
            QuestMessages.BEGIN,
            null,
            SoulMonster.VILLAGER,
            SoulMonster.ZOMBIE_VILLAGER
    ),
    TURTLE(
            5,
            QuestMessages.TURTLE,
            null,
            SoulMonster.TURTLE,
            SoulMonster.TURTLE_SOLDIER,
            SoulMonster.TURTLE_KING
    ),
    SPIDER(
            6,
            QuestMessages.SPIDER,
            null,
            SoulMonster.SPIDER,
            SoulMonster.CAVE_SPIDER,
            SoulMonster.SPIDER_KING
    ),
    ZOMBIE(
            7,
            QuestMessages.ZOMBIE,
            null,
            SoulMonster.ZOMBIE,
            SoulMonster.ZOMBIE_SOLDIER,
            SoulMonster.ZOMBIE_KING
    ),
    SKELETON(
            8,
            QuestMessages.SKELETON,
            null,
            SoulMonster.SKELETON,
            SoulMonster.SKELETON_SOLDIER,
            SoulMonster.SKELETON_KING
    ),
    ORC(
            9,
            QuestMessages.ORC,
            null,
            SoulMonster.ORC,
            SoulMonster.ORC_SOLDIER,
            SoulMonster.ORC_KING
    ),
    GHOST(
            10,
            QuestMessages.GHOST,
            null,
            SoulMonster.GHOST,
            SoulMonster.WHITE_GHOST,
            SoulMonster.GHOST_KING
    ),
    LADON(
            100,
            QuestMessages.LADON,
            null,
            SoulMonster.LADON
    ),
    UNDINE(
            200,
            QuestMessages.UNDINE,
            null,
            SoulMonster.UNDINE
    ),
    SALAMANDRA(
            300,
            QuestMessages.SALAMANDRA,
            null,
            SoulMonster.SALAMANDRA
    ),
    SYLPHID(
            400,
            QuestMessages.SYLPHID,
            null,
            SoulMonster.SYLPHID
    ),
    NOMOS(
            500,
            QuestMessages.NOMOS,
            null,
            SoulMonster.NOMOS
    ),
    LOA(
            600,
            QuestMessages.LOA,
            null,
            SoulMonster.LOA
    ),
    ;

    companion object {
        val COLOR = ChatColor.LIGHT_PURPLE

        fun getOrderedList(player: Player) =
                values().filter { it.isOrdered(player) }

        fun getProcessedList(player: Player) =
                values().filter { it.isProcessed(player) }
    }

    val monsterList = monsters.toList()

    val maxDegree = monsterList.size

    fun getTitle(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore?.map { it.asSafety(locale) }

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

    fun isProcessed(player: Player): Boolean {
        return getClient(player)?.run {
            isOrdered && isProcessed
        } ?: false
    }

    fun isCleared(player: Player): Boolean {
        return getClient(player)?.run {
            clearNum > 0
        } ?: false
    }

    // クエスト進行
    fun process(player: Player) {
        val degree = getClient(player)?.processedDegree?.plus(1) ?: return
        if (degree == monsterList.size) {
            complete(player)
        } else {
            getClient(player)?.run {
                isProcessed = true
                processedDegree = degree
            }
            QuestMessages.QUEST_PROCEED(this, degree).sendTo(player)
        }
    }

    // クエスト完了
    fun complete(player: Player) {
        getClient(player)?.run {
            isOrdered = false
            isProcessed = false
            processedDegree = 0
            clearNum++
        }
        QuestMessages.QUEST_COMPLETE(this).sendTo(player)
    }

    fun getClient(player: Player): QuestClient? {
        val questKey = Keys.QUEST_MAP[this] ?: return null
        return player.getOrPut(questKey)
    }
}
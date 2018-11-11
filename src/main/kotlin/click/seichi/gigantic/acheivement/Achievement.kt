package click.seichi.gigantic.acheivement

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.messages.AchievementMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.entity.Player

/**
 * @author tar0ss
 *
 *
 */
enum class Achievement(
        val id: Int,
        private val canGranting: (Player) -> Boolean,
        // 毎Login時とアンロック時に処理される
        val action: (Player) -> Unit = {},
        val grantMessage: ChatMessage? = null,
        private val priority: UpdatePriority = UpdatePriority.NORMAL
) {
    // messages
    JOIN_SERVER(0, { true }, action = { player ->
        player.manipulate(CatalogPlayerCache.BELT_SWITCHER) {
            it.unlock(click.seichi.gigantic.belt.Belt.DIG)
            it.unlock(click.seichi.gigantic.belt.Belt.MINE)
            it.unlock(click.seichi.gigantic.belt.Belt.CUT)
            it.setCanSwitch(click.seichi.gigantic.belt.Belt.DIG, true)
            it.setCanSwitch(click.seichi.gigantic.belt.Belt.MINE, true)
            it.setCanSwitch(click.seichi.gigantic.belt.Belt.CUT, true)
        }
    }, grantMessage = AchievementMessages.FIRST_JOIN),
    FIRST_LEVEL_UP(1, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 1
    }, grantMessage = AchievementMessages.FIRST_LEVEL_UP),

    // systems
    MANA_STONE(100, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, grantMessage = AchievementMessages.MANA_STONE,
            priority = UpdatePriority.HIGHEST),
    TELEPORT(101, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 15
    }, grantMessage = AchievementMessages.TELEPORT),
    QUEST(102, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 3
    }, grantMessage = AchievementMessages.QUEST,
            priority = UpdatePriority.HIGHEST),


    // wills
    WILL_BASIC_1(150, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 14
    }, action = { player ->
        player.manipulate(CatalogPlayerCache.APTITUDE) { willAptitude ->
            willAptitude.addRandomIfNeeded(WillGrade.BASIC, 1)?.let {
                PlayerMessages.OBTAIN_WILL_APTITUDE(it).sendTo(player)
            }
        }
    }, grantMessage = AchievementMessages.UNLOCK_WILL_BASIC_1,
            priority = UpdatePriority.HIGHEST),

    // skills
    SKILL_FLASH(200, {
        // TODO revase to this
//        it.hasRelic(Relic.PIGS_FEATHER)
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 3

    }, grantMessage = AchievementMessages.UNLOCK_FLASH),
    SKILL_MINE_BURST(201, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 3
    }, grantMessage = AchievementMessages.SKILL_MINE_BURST),

    // spells
    SPELL_STELLA_CLAIR(300, {
        MANA_STONE.isGranted(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, grantMessage = AchievementMessages.UNLOCK_STELLA_CLAIR),
    SPELL_TERRA_DRAIN(301, {
        MANA_STONE.isGranted(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, grantMessage = AchievementMessages.UNLOCK_TERRA_DRAIN),
    SPELL_GRAND_NATURA(302, {
        MANA_STONE.isGranted(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 14
    }, grantMessage = AchievementMessages.UNLOCK_GRAND_NATURA),

    SPELL_AQUA_LINEA(303, {
        MANA_STONE.isGranted(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 18
    }, grantMessage = AchievementMessages.UNLOCK_AQUA_LINEA),

    // quest order
    QUEST_LADON_ORDER(400, {
        MANA_STONE.isGranted(it)
    }, action = {
        Quest.LADON.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_UNDINE_ORDER(401, {
        MANA_STONE.isGranted(it) &&
                Will.AQUA.isAptitude(it)
    }, action = {
        Quest.UNDINE.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_SALAMANDRA_ORDER(402, {
        MANA_STONE.isGranted(it) &&
                Will.IGNIS.isAptitude(it)
    }, action = {
        Quest.SALAMANDRA.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_SYLPHID_ORDER(403, {
        MANA_STONE.isGranted(it) &&
                Will.AER.isAptitude(it)
    }, action = {
        Quest.SYLPHID.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_NOMOS_ORDER(404, {
        MANA_STONE.isGranted(it) &&
                Will.TERRA.isAptitude(it)
    }, action = {
        Quest.NOMOS.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_LOA_ORDER(405, {
        MANA_STONE.isGranted(it) &&
                Will.NATURA.isAptitude(it)
    }, action = {
        Quest.LOA.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_PIG_ORDER(406, {
        QUEST.isGranted(it)
    }, action = {
        Quest.PIG_CROWD.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    ;

    /**1から順に [update] される**/
    enum class UpdatePriority(val amount: Int) {
        HIGHEST(1), HIGH(2), NORMAL(3), LOW(4), LOWEST(5)
    }

    companion object {
        fun update(player: Player, isAction: Boolean = false) {
            values().sortedBy { it -> it.priority.amount }
                    .forEach {
                        it.update(player, isAction)
                    }
        }
    }


    private fun update(player: Player, isAction: Boolean) {
        if (canGrant(player)) {
            if (isGranted(player)) {
                // 現在も解除可能で既に解除済みの時
                if (isAction) {
                    action
                }
            } else {
                // 現在も解除可能で解除していない時
                grant(player)
            }
        } else {
            if (isGranted(player)) {
                // 現在解除できないがすでに解除しているとき
                revoke(player)
            }
        }
    }

    // 与える
    private fun grant(player: Player) {
        // 解除処理
        player.transform(Keys.ACHIEVEMENT_MAP[this] ?: return) { hasUnlocked ->
            if (!hasUnlocked) {
                action(player)
                grantMessage?.sendTo(player)
            }
            true
        }
    }

    // はく奪する
    private fun revoke(player: Player) {
        // ロック処理
        player.transform(Keys.ACHIEVEMENT_MAP[this] ?: return) {
            false
        }
    }

    private fun canGrant(player: Player) = canGranting(player)

    fun isGranted(player: Player) = canGrant(player) && player.getOrPut(Keys.ACHIEVEMENT_MAP[this]!!)
}
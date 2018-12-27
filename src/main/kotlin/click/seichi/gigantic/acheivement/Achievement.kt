package click.seichi.gigantic.acheivement

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.messages.AchievementMessages
import click.seichi.gigantic.tool.Tool
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
        Tool.PICKEL.grant(player)
        Tool.SHOVEL.grant(player)
        Tool.AXE.grant(player)
        Belt.DEFAULT.grant(player)
    }, grantMessage = AchievementMessages.FIRST_JOIN),
    FIRST_LEVEL_UP(1, {
        it.wrappedLevel >= 2
    }, grantMessage = AchievementMessages.FIRST_LEVEL_UP),


    //TODO 一度すべてのクエストを隠蔽しているので実装時は一気にやる
    // systems
    MANA_STONE(100, {
        it.wrappedLevel >= 10
    }, grantMessage = AchievementMessages.MANA_STONE,
            priority = UpdatePriority.HIGHEST),
    TELEPORT_PLAYER(101, {
        it.wrappedLevel >= 4
    }, grantMessage = AchievementMessages.TELEPORT_PLAYER),
    QUEST(102, {
        false /*it.wrappedLevel >= 5*/
    }, priority = UpdatePriority.HIGHEST),
    TELEPORT_LAST_DEATH(103, {
        it.wrappedLevel >= 7
    }, grantMessage = AchievementMessages.TELEPORT_LAST_DEATH),
    EFFECT(104, {
        it.wrappedLevel >= 2
    }, grantMessage = AchievementMessages.EFFECT),

    // skills
    SKILL_FLASH(200, {
        it.wrappedLevel >= 5/*Quest.PIG.isCleared(it)*/
    }, grantMessage = AchievementMessages.UNLOCK_FLASH),
    SKILL_MINE_BURST(201, {
        it.wrappedLevel >= 8/*Quest.BLAZE.isCleared(it)*/
    }, grantMessage = AchievementMessages.UNLOCK_SKILL_MINE_BURST),
    SKILL_MINE_COMBO(202, {
        it.wrappedLevel >= 3
    }, grantMessage = AchievementMessages.UNLOCK_SKILL_MINE_COMBO),

    // spells
    SPELL_STELLA_CLAIR(300, {
        MANA_STONE.isGranted(it)
    }, grantMessage = AchievementMessages.UNLOCK_SPELL_STELLA_CLAIR),
    SPELL_APOSTOL(301, {
        MANA_STONE.isGranted(it)
    }, grantMessage = AchievementMessages.UNLOCK_SPELL_APOSTOLUS),

    // quest order

    // MAIN QUEST
    /*QUEST_BEGINS_ORDER(401, {
        QUEST.isGranted(it)
    }, action = {
        Quest.BEGIN.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER_FIRST),
    QUEST_PIG_ORDER(402, {
        Quest.BEGIN.isCleared(it) &&
                it.wrappedLevel >= 7
    }, action = {
        Quest.PIG.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_BLAZE_ORDER(403, {
        Quest.PIG.isCleared(it) &&
                it.wrappedLevel >= 9
    }, action = {
        Quest.BLAZE.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),*/
    /*QUEST_LADON_ORDER(400, {
        false
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
        Quest.BEGIN.isCleared(it) &&
                it.wrappedLevel >= 7
    }, action = {
        Quest.PIG.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_BLAZE_ORDER(407, {
        Quest.PIG.isCleared(it) &&
                it.wrappedLevel >= 9
    }, action = {
        Quest.BLAZE.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_CHICKEN_ORDER(408, {
        false
    }, action = {
        Quest.CHICKEN.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_WITHER_ORDER(409, {
        false
    }, action = {
        Quest.WITHER.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_BEGINS_ORDER(410, {
        QUEST.isGranted(it)
    }, action = {
        Quest.BEGIN.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER_FIRST),
    QUEST_TURTLE_ORDER(411, {
        false
    }, action = {
        Quest.TURTLE.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_SPIDER_ORDER(412, {
        false
    }, action = {
        Quest.SPIDER.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_ZOMBIE_ORDER(413, {
        false
    }, action = {
        Quest.ZOMBIE.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_SKELETON_ORDER(414, {
        false
    }, action = {
        Quest.SKELETON.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_ORC_ORDER(415, {
        false
    }, action = {
        Quest.ORC.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_GHOST_ORDER(416, {
        false
    }, action = {
        Quest.GHOST.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_PARROT_ORDER(417, {
        false
    }, action = {
        Quest.PARROT.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_SLIME_ORDER(418, {
        false
    }, action = {
        Quest.SLIME.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),
    QUEST_ENDER_MAN_ORDER(419, {
        false
    }, action = {
        Quest.ENDER_MAN.order(it)
    }, grantMessage = AchievementMessages.QUEST_ORDER),*/
    ;

    /**1から順に [update] される**/
    enum class UpdatePriority(val amount: Int) {
        HIGHEST(1), HIGH(2), NORMAL(3), LOW(4), LOWEST(5)
    }

    companion object {
        // 強制的にプレイヤー表示部分を更新したい場合は[isForced]をtrueに設定
        fun update(player: Player, isForced: Boolean = false) {
            var isChanged = false
            values().sortedBy { it -> it.priority.amount }
                    .forEach {
                        if (it.update(player)) {
                            isChanged = true
                        }
                    }
            if (!isChanged && !isForced) return
            player.updateDisplay(true, true)
        }
    }


    private fun update(player: Player): Boolean {
        var isChanged = false
        if (canGrant(player)) {
            if (!isGranted(player)) {
                // 現在も解除可能で解除していない時
                grant(player)
                isChanged = true
            }
        } else {
            if (isGranted(player)) {
                // 現在解除できないがすでに解除しているとき
                revoke(player)
                isChanged = true
            }
        }
        return isChanged
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

    private fun canGrant(player: Player) =
            if (DebugConfig.ACHIEVEMENT_UNLOCK) true
            else canGranting(player)

    fun isGranted(player: Player) =
            if (DebugConfig.ACHIEVEMENT_UNLOCK) true
            else canGrant(player) && Keys.ACHIEVEMENT_MAP[this]?.let { player.getOrPut(it) } ?: false


}
package click.seichi.gigantic.database

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.database.table.*
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.player.Display
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.tool.Tool
import click.seichi.gigantic.will.Will
import org.jetbrains.exposed.sql.and
import java.util.*

/**
 * [UUID]に紐づけられたエンティティデータクラス
 *
 * @author tar0ss
 */
class UserEntity(uniqueId: UUID, playerName: String) {

    val user = User.findById(uniqueId)?.apply {
    } ?: User.new(uniqueId) {
        name = playerName.toLowerCase()
    }

    val userWillMap = Will.values().map { will ->
        will to (UserWill
                .find { (UserWillTable.userId eq uniqueId) and (UserWillTable.willId eq will.id) }
                .firstOrNull() ?: UserWill.new {
            user = this@UserEntity.user
            willId = will.id
        })
    }.toMap()

    val userExpMap = ExpReason.values().map { reason ->
        reason to (UserExp
                .find { (UserExpTable.userId eq uniqueId) and (UserExpTable.reasonId eq reason.id) }
                .firstOrNull() ?: UserExp.new {
            user = this@UserEntity.user
            reasonId = reason.id
        })
    }.toMap()

    val userMonsterMap = SoulMonster.values().map { monster ->
        monster to (UserMonster
                .find { (UserMonsterTable.userId eq uniqueId) and (UserMonsterTable.monsterId eq monster.id) }
                .firstOrNull() ?: UserMonster.new {
            user = this@UserEntity.user
            monsterId = monster.id
        })
    }.toMap()

    val userRelicMap = Relic.values().map { relic ->
        relic to (UserRelic
                .find { (UserRelicTable.userId eq uniqueId) and (UserRelicTable.relicId eq relic.id) }
                .firstOrNull() ?: UserRelic.new {
            user = this@UserEntity.user
            relicId = relic.id
        })
    }.toMap()

    val userAchievementMap = Achievement.values().map { func ->
        func to (UserAchievement
                .find { (UserAchievementTable.userId eq uniqueId) and (UserAchievementTable.achievementId eq func.id) }
                .firstOrNull() ?: UserAchievement.new {
            user = this@UserEntity.user
            achievementId = func.id
        })
    }.toMap()

    val userToolMap = Tool.values().map { tool ->
        tool to (UserTool
                .find { (UserToolTable.userId eq uniqueId) and (UserToolTable.toolId eq tool.id) }
                .firstOrNull() ?: UserTool.new {
            user = this@UserEntity.user
            toolId = tool.id
        })
    }.toMap()

    val userBeltMap = Belt.values().map { belt ->
        belt to (UserBelt
                .find { (UserBeltTable.userId eq uniqueId) and (UserBeltTable.beltId eq belt.id) }
                .firstOrNull() ?: UserBelt.new {
            user = this@UserEntity.user
            beltId = belt.id
        })
    }.toMap()

    val userQuestMap = Quest.values().map { quest ->
        quest to (UserQuest
                .find { (UserQuestTable.userId eq uniqueId) and (UserQuestTable.questId eq quest.id) }
                .firstOrNull() ?: UserQuest.new {
            user = this@UserEntity.user
            questId = quest.id
        })
    }.toMap()

    val userEffectMap = GiganticEffect.values().map { effect ->
        effect to (UserEffect
                .find { (UserEffectTable.userId eq uniqueId) and (UserEffectTable.effectId eq effect.id) }
                .firstOrNull() ?: UserEffect.new {
            user = this@UserEntity.user
            effectId = effect.id
        })
    }.toMap()

    val userDisplayMap = Display.values().map { display ->
        display to (UserDisplay
                .find { (UserDisplayTable.userId eq uniqueId) and (UserDisplayTable.displayId eq display.id) }
                .firstOrNull() ?: UserDisplay.new {
            user = this@UserEntity.user
            displayId = display.id
        })
    }.toMap()

    val userFollowList = UserFollow
            .find { UserFollowTable.userId eq uniqueId }
            .toList()

    val userFollowerList = UserFollow
            .find { UserFollowTable.followId eq uniqueId }
            .toList()

    val userHomeList = UserHome
            .find { UserHomeTable.userId eq uniqueId }
            .toList()

    val userMuteList = UserMute
            .find { UserMuteTable.userId eq uniqueId }
            .toList()
}
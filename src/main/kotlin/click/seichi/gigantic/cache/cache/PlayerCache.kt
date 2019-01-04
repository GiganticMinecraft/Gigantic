package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.database.UserEntity
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
class PlayerCache(private val uniqueId: UUID, private val playerName: String) : Cache<PlayerCache>() {

    override fun read() {
        transaction {
            val entity = UserEntity(uniqueId, playerName)
            Keys.MAX_COMBO.let {
                offer(it, it.read(entity))
            }
            Keys.LOCALE.let {
                offer(it, it.read(entity))
            }
            Keys.MANA.let {
                offer(it, it.read(entity))
            }
            Keys.TOOL.let {
                offer(it, it.read(entity))
            }
            Keys.BELT.let {
                offer(it, it.read(entity))
            }
            Keys.SPELL_TOGGLE.let {
                offer(it, it.read(entity))
            }
            Keys.TELEPORT_TOGGLE.let {
                offer(it, it.read(entity))
            }
            Keys.SPELL_APOSTOL_BREAK_AREA.let {
                offer(it, it.read(entity))
            }
            Keys.EFFECT.let {
                offer(it, it.read(entity))
            }
            Keys.VOTE.let {
                force(it, it.read(entity))
            }
            Keys.POMME.let {
                force(it, it.read(entity))
            }
            Keys.DONATION.let {
                force(it, it.read(entity))
            }
            Keys.FOLLOW_SET.let {
                offer(it, it.read(entity))
            }
            Keys.EXP_MAP.forEach { reason, key ->
                offer(key, key.read(entity))
            }
            Keys.MEMORY_MAP.forEach { will, key ->
                offer(key, key.read(entity))
            }
            Keys.APTITUDE_MAP.forEach { will, key ->
                offer(key, key.read(entity))
            }
            Keys.SOUL_MONSTER.forEach { boss, key ->
                offer(key, key.read(entity))
            }
            Keys.RELIC_MAP.forEach { relic, key ->
                offer(key, key.read(entity))
            }
            Keys.ACHIEVEMENT_MAP.forEach { func, key ->
                offer(key, key.read(entity))
            }
            Keys.BELT_TOGGLE_MAP.forEach { belt, key ->
                offer(key, key.read(entity))
            }
            Keys.BELT_UNLOCK_MAP.forEach { belt, key ->
                offer(key, key.read(entity))
            }
            Keys.TOOL_TOGGLE_MAP.forEach { tool, key ->
                offer(key, key.read(entity))
            }
            Keys.TOOL_UNLOCK_MAP.forEach { tool, key ->
                offer(key, key.read(entity))
            }
            Keys.QUEST_MAP.forEach { quest, key ->
                offer(key, key.read(entity))
            }
            Keys.EFFECT_BOUGHT_MAP.forEach { effect, key ->
                offer(key, key.read(entity))
            }
            Keys.EFFECT_BOUGHT_TIME_MAP.forEach { effect, key ->
                offer(key, key.read(entity))
            }
        }
    }

    override fun write() {
        transaction {
            val entity = UserEntity(uniqueId, playerName)
            // 更新時間を記録
            entity.user.updatedDate = DateTime.now()
            Keys.MAX_COMBO.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.LOCALE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.MANA.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.TOOL.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.BELT.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.SPELL_TOGGLE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.TELEPORT_TOGGLE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.SPELL_APOSTOL_BREAK_AREA.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.EFFECT.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.FOLLOW_SET.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.EXP_MAP.forEach { reason, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.MEMORY_MAP.forEach { will, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.APTITUDE_MAP.forEach { will, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.SOUL_MONSTER.forEach { boss, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.RELIC_MAP.forEach { relic, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.ACHIEVEMENT_MAP.forEach { func, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.BELT_TOGGLE_MAP.forEach { belt, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.BELT_UNLOCK_MAP.forEach { belt, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.TOOL_TOGGLE_MAP.forEach { tool, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.TOOL_UNLOCK_MAP.forEach { tool, key ->
                key.store(entity, getOrDefault(key))
            }
            Keys.QUEST_MAP.forEach { quest, key ->
                key.store(entity, getOrDefault(key))
            }

        }
    }


}
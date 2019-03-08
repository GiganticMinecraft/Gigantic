package click.seichi.gigantic.extension

import black.bracken.drainage.dsl.InventoryUI
import black.bracken.drainage.extension.openInventory
import click.seichi.gigantic.Currency
import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Key
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.cache.manipulator.manipulators.MineCombo
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.message.messages.SideBarMessages
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.tool.Tool
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillRelationship
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.chat.ComponentSerializer
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta
import java.math.BigDecimal
import java.util.*
import kotlin.math.roundToInt

/**
 * @author tar0ss
 */

fun Player.getHead() = itemStackOf(Material.PLAYER_HEAD) {
    itemMeta = (itemMeta as SkullMeta).also { meta ->
        meta.owningPlayer = this@getHead
    }
}

fun <V : Any?> Player.getOrPut(key: Key<PlayerCache, V>, value: V = key.default) = PlayerCacheMemory.get(uniqueId).getOrPut(key, value)

fun <V : Any?> Player.remove(key: Key<PlayerCache, V>) = PlayerCacheMemory.get(uniqueId).remove(key)

fun <V : Any?> Player.offer(key: Key<PlayerCache, V>, value: V) = PlayerCacheMemory.get(uniqueId).offer(key, value)

fun <V : Any?> Player.force(key: Key<PlayerCache, V>, value: V) = PlayerCacheMemory.get(uniqueId).force(key, value)

fun <V : Any?> Player.replace(key: Key<PlayerCache, V>, value: V) = PlayerCacheMemory.get(uniqueId).replace(key, value)

fun <V : Any?> Player.transform(key: Key<PlayerCache, V>, transforming: (V) -> V) = PlayerCacheMemory.get(uniqueId).transform(key, transforming)

fun <M : Manipulator<M, PlayerCache>> Player.manipulate(clazz: Class<M>, manipulating: (M) -> Unit) = PlayerCacheMemory.get(uniqueId).manipulate(clazz, manipulating)

val Player.wrappedLocale: Locale
    get() = getOrPut(Keys.LOCALE)

val Player.wrappedLevel: Int
    get() = getOrPut(Keys.LEVEL)

val Player.wrappedExp: BigDecimal
    get() = ExpReason.values().fold(BigDecimal.ZERO) { source, reason ->
        source + getOrPut(Keys.EXP_MAP[reason]!!)
    }

val Player.mana: BigDecimal
    get() = getOrPut(Keys.MANA)

val Player.maxMana: BigDecimal
    get() = getOrPut(Keys.MAX_MANA)

val Player.combo: Long
    get() = getOrPut(Keys.COMBO)

val Player.maxCombo: Long
    get() = getOrPut(Keys.MAX_COMBO)

val Player.comboRank: Int
    get() = MineCombo.calcComboRank(combo)

// 累計投票数
val Player.vote: Int
    get() = getOrPut(Keys.VOTE)

// 累計投票ポイント
val Player.votePoint: Int
    get() = Currency.VOTE_POINT.getAmount(this)
// 累計
val Player.pomme: Int
    get() = Currency.POMME.getAmount(this)
// 累計寄付額
val Player.donation: Int
    get() = getOrPut(Keys.DONATION)
// 累計寄付p
val Player.donatePoint: Int
    get() = Currency.DONATE_POINT.getAmount(this)

val Player.effect: GiganticEffect
    get() = getOrPut(Keys.EFFECT)

fun Player.isMaxMana() = mana >= maxMana

fun Player.hasMana() = mana > BigDecimal.ZERO

fun Player.hasAptitude(will: Will) = getOrPut(Keys.APTITUDE_MAP[will]!!)

fun Player.isProcessed(will: Will) = getOrPut(Keys.WILL_SECRET_MAP.getValue(will)) > 0

fun Player.ethel(will: Will) = getOrPut(Keys.ETHEL_MAP[will]!!)

fun Player.isFollow(uniqueId: UUID) = getOrPut(Keys.FOLLOW_SET).contains(uniqueId)

fun Player.follow(uniqueId: UUID) = transform(Keys.FOLLOW_SET) {
    setOf(*it.toTypedArray(), uniqueId)
}

fun Player.unFollow(uniqueId: UUID) = transform(Keys.FOLLOW_SET) {
    it.toMutableSet().apply { remove(uniqueId) }
}

val Player.follows: Int
    get() = getOrPut(Keys.FOLLOW_SET).size

fun Player.isMute(uniqueId: UUID) = getOrPut(Keys.MUTE_SET).contains(uniqueId)

fun Player.mute(uniqueId: UUID) = transform(Keys.MUTE_SET) {
    setOf(*it.toTypedArray(), uniqueId)
}

fun Player.unMute(uniqueId: UUID) = transform(Keys.MUTE_SET) {
    it.toMutableSet().apply { remove(uniqueId) }
}

val Player.mutes: Int
    get() = getOrPut(Keys.MUTE_SET).size

fun Player.relationship(will: Will) = getOrPut(Keys.WILL_RELATIONSHIP_MAP[will]!!)

fun Player.hasRelic(relic: Relic) = relic.getDroppedNum(this) > 0

fun Player.teleportSafely(location: Location) {
    teleport(location)
    offer(Keys.PREVIOUS_LOCATION, location)
}

// レリック合計数
val Player.relics: Long
    get() = Relic.values()
            .map { it.getDroppedNum(this) }
            .sum()

// レリックの獲得種類数
val Player.relicTypes: Int
    get() = Relic.values()
            .count { hasRelic(it) }

val Player.isNormalTexture: Boolean
    get() = getOrPut(Keys.IS_NORMAL_TEXTURE)


/**
 * プレイヤーが向いている方向の[BlockFace]を取得する
 */
private const val UP_PITCH_MAX = -60
private const val DOWN_PITCH_MIN = 60

fun Player.calcFace(ignorePitch: Boolean = false): BlockFace {
    if (!ignorePitch) {
        when (location.pitch.roundToInt()) {
            in -90..UP_PITCH_MAX -> return BlockFace.UP
            in DOWN_PITCH_MIN..90 -> return BlockFace.DOWN
        }
    }
    var rot = (location.yaw + 180) % 360
    if (rot < 0) rot += 360
    return when (rot.roundToInt()) {
        in 0 until 45 -> BlockFace.NORTH
        in 45 until 135 -> BlockFace.EAST
        in 135 until 225 -> BlockFace.SOUTH
        in 225 until 315 -> BlockFace.WEST
        else -> BlockFace.NORTH
    }
}

fun Player.sendActionBar(message: String) = spigot().sendMessage(ChatMessageType.ACTION_BAR, ComponentSerializer.parse("{\"text\": \"$message\"}")[0])

fun Player.spawnColoredParticle(
        location: Location,
        color: Color,
        count: Int = 1,
        noiseData: NoiseData = NoiseData()
) = (0 until count).forEach { _ ->
    player.spawnParticle(
            Particle.REDSTONE,
            location.noised(noiseData),
            0,
            Particle.DustOptions(color, 1.0F)
    )
}

fun Player.spawnColoredParticleSpherically(
        location: Location,
        color: Color,
        count: Int = 1,
        radius: Double
) = spawnColoredParticle(location, color, count, NoiseData(radius, { Random.nextGaussian(variance = it / 2) }))

fun Player.findBattle() = BattleManager.findBattle(this)

fun Player.updateLevel(isOnLogin: Boolean = false) {

    // ログイン時のデバッグモード処理
    if (isOnLogin) {
        offer(Keys.EXP_MAP[ExpReason.DEBUG]!!, BigDecimal.ZERO)
        if (Gigantic.IS_DEBUG) {
            val level = DebugConfig.LEVEL
            val nextExp = (PlayerLevelConfig.LEVEL_MAP[level + 1] ?: BigDecimal.ZERO).minus(BigDecimal.ONE)
            val currentExp = wrappedExp

            offer(Keys.EXP_MAP[ExpReason.DEBUG]!!, nextExp - currentExp)
        }
    }

    // レベル計算
    val currentLevel = player.wrappedLevel
    manipulate(CatalogPlayerCache.LEVEL) {
        it.calculate(wrappedExp)
    }

    PlayerMessages.EXP_BAR_DISPLAY(player.wrappedLevel, player.wrappedExp).sendTo(this)

    // 上昇レベルに応じてEvent call
    if (player.wrappedLevel > currentLevel && !isOnLogin) {
        ((currentLevel + 1)..player.wrappedLevel).forEach { level ->
            Bukkit.getPluginManager().callEvent(LevelUpEvent(level, this))
        }
    }
}


fun Player.updateDisplay(applyMainHand: Boolean, applyOffHand: Boolean) {
    updateBelt(applyMainHand, applyOffHand)
    updateBag()
    updateSideBar()
    val ui = player.inventory.holder as? InventoryUI ?: return
    player.openInventory(ui)
}

// ツールだけ更新したいときはこれを使う
fun Player.updateTool() {
    getOrPut(Keys.TOOL).update(this)
}

// ベルト全体を更新したいときはこれを使う
fun Player.updateBelt(applyMainHand: Boolean, applyOffHand: Boolean) {
    getOrPut(Keys.BELT).wear(this, applyMainHand, applyOffHand)
}

fun Player.updateBag() {
    // TODO implements
//    getOrPut(Keys.BAG).carry(this)
}

fun Player.updateSideBar() {
    SideBarMessages.ETHEL(this, true).sendTo(this)
}

fun Player.fixHandToTool() {
    inventory.heldItemSlot = getOrPut(Keys.BELT).toolSlot
}

fun Player.switchTool(): Tool? {
    var tool: Tool? = null
    manipulate(CatalogPlayerCache.TOOL_SWITCHER) {
        tool = it.switch()
    }
    return tool
}

fun Player.updateWillRelationship(isOnLogin: Boolean = false) {
    Will.values().forEach { will ->
        this.transform(Keys.WILL_RELATIONSHIP_MAP[will]!!) { prev ->
            val next = WillRelationship.calcRelationship(this, will)
            if (prev != next && !isOnLogin) {
                WillMessages.NEXT_RELATIONSHIP(will, next.getName(wrappedLocale)).sendTo(this)
                PlayerSounds.LEVEL_UP.playOnly(this)
            }
            return@transform next
        }
    }
}

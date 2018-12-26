package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Key
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.tool.Tool
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.chat.ComponentSerializer
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.math.BigDecimal
import java.util.*
import kotlin.math.roundToInt

/**
 * @author tar0ss
 */

fun Player.getHead() = ItemStack(Material.PLAYER_HEAD).apply {
    itemMeta = (itemMeta as SkullMeta).also { meta ->
        meta.owningPlayer = this@getHead
    }
}

fun <V : Any?> Player.getOrPut(key: Key<PlayerCache, V>, value: V = key.default) = PlayerCacheMemory.get(uniqueId).getOrPut(key, value)

fun <V : Any?> Player.remove(key: Key<PlayerCache, V>) = PlayerCacheMemory.get(uniqueId).remove(key)

fun <V : Any?> Player.offer(key: Key<PlayerCache, V>, value: V) = PlayerCacheMemory.get(uniqueId).offer(key, value)

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

val Player.wrappedHealth: Long
    get() = getOrPut(Keys.HEALTH)

val Player.wrappedMaxHealth: Long
    get() = getOrPut(Keys.MAX_HEALTH)

val Player.mana: BigDecimal
    get() = getOrPut(Keys.MANA)

val Player.maxMana: BigDecimal
    get() = getOrPut(Keys.MAX_MANA)

val Player.combo: Long
    get() = getOrPut(Keys.MINE_COMBO)

val Player.maxCombo: Long
    get() = getOrPut(Keys.MAX_COMBO)


fun Player.isMaxMana() = mana >= maxMana

fun Player.hasMana(other: BigDecimal) = mana >= other

fun Player.hasAptitude(will: Will) = getOrPut(Keys.APTITUDE_MAP[will]!!)

fun Player.memory(will: Will) = getOrPut(Keys.MEMORY_MAP[will]!!)

/**
 * プレイヤーが向いている方向の[BlockFace]を取得する
 */
private const val UP_PITCH_MAX = -60
private const val DOWN_PITCH_MIN = 60

fun Player.calcBreakFace(): BlockFace {
    when (location.pitch.roundToInt()) {
        in -90..UP_PITCH_MAX -> return BlockFace.UP
        in DOWN_PITCH_MIN..90 -> return BlockFace.DOWN
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


fun Player.updateInventory(applyMainHand: Boolean, applyOffHand: Boolean) {
    updateBelt(applyMainHand, applyOffHand)
    updateBag()
}

fun Player.updateBelt(applyMainHand: Boolean, applyOffHand: Boolean) {
    getOrPut(Keys.BELT).wear(this, applyMainHand, applyOffHand)
}

fun Player.updateBag() {
    getOrPut(Keys.BAG).carry(this)
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

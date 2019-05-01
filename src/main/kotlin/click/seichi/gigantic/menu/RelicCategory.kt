package click.seichi.gigantic.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
enum class RelicCategory(val menuTitle: LocalizedText) {
    ALL(LocalizedText(
            Locale.JAPANESE to "レリック一覧"
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return true
        }
    },
    AQUA(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.AQUA.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.AQUA
        }
    },
    IGNIS(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.IGNIS.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.IGNIS
        }
    },
    AER(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.AER.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.AER
        }
    },
    TERRA(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.TERRA.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.TERRA
        }
    },
    NATURA(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.NATURA.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.NATURA
        }
    },
    GLACIES(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.GLACIES.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.GLACIES
        }
    },
    LUX(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.LUX.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.LUX
        }
    },
    SOLUM(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.SOLUM.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.SOLUM
        }
    },
    UMBRA(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.UMBRA.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.UMBRA
        }
    },
    VENTUS(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.VENTUS.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.VENTUS
        }
    },
    SAKURA(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.SAKURA.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.SAKURA
        }
    },
    MIO(LocalizedText(
            Locale.JAPANESE.let {
                it to Will.MIO.getName(it) +
                        "のレリック一覧"
            }
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == Will.MIO
        }
    },
    SPECIAL(LocalizedText(
            Locale.JAPANESE to "特殊レリック一覧"
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return Will.findByRelic(relic) == null
        }
    },
    ACTIVE(LocalizedText(
            Locale.JAPANESE to "レリック一覧"
    )) {
        override fun isContain(player: Player, relic: Relic): Boolean {
            return player.getOrPut(Keys.ACTIVE_RELICS).contains(relic)
        }
    },
    ;

    abstract fun isContain(player: Player, relic: Relic): Boolean

    companion object {
        fun getByWill(will: Will) = when (will) {
            Will.AQUA -> AQUA
            Will.IGNIS -> IGNIS
            Will.AER -> AER
            Will.TERRA -> TERRA
            Will.NATURA -> NATURA
            Will.GLACIES -> GLACIES
            Will.LUX -> LUX
            Will.SOLUM -> SOLUM
            Will.UMBRA -> UMBRA
            Will.VENTUS -> VENTUS
            Will.SAKURA -> SAKURA
            Will.MIO -> MIO
        }
    }
}
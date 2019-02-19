package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.RelicButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.RelicMenuMessages
import click.seichi.gigantic.relic.WillRelic
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object RelicMenu : Menu() {

    val relicMenu = this

    override val size: Int
        get() = 3 * 9

    override fun getTitle(player: Player): String {
        return RelicMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, RelicButtons.WILL(Will.TERRA, TerraRelicMenu))
        registerButton(1, RelicButtons.WILL(Will.AQUA, AquaRelicMenu))
        registerButton(2, RelicButtons.WILL(Will.IGNIS, IgnisRelicMenu))
        registerButton(3, RelicButtons.WILL(Will.NATURA, NaturaRelicMenu))
        registerButton(4, RelicButtons.WILL(Will.AER, AerRelicMenu))
        registerButton(10, RelicButtons.WILL(Will.GLACIES, GlaciesRelicMenu))
        registerButton(11, RelicButtons.WILL(Will.SOLUM, SolumRelicMenu))
        registerButton(12, RelicButtons.WILL(Will.VENTUS, VentusRelicMenu))
        registerButton(13, RelicButtons.WILL(Will.LUX, LuxRelicMenu))
        registerButton(14, RelicButtons.WILL(Will.UMBRA, UmbraRelicMenu))
        registerButton(20, RelicButtons.WILL(Will.SAKURA, SakuraRelicMenu))
    }

    object TerraRelicMenu : WillRelicMenu(Will.TERRA, 3 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(2, RelicButtons.WILL_RELIC(WillRelic.USED_COIN))
            registerButton(4, RelicButtons.WILL_RELIC(WillRelic.ADVENTURER_SOLE))
            registerButton(6, RelicButtons.WILL_RELIC(WillRelic.SUPER_MUSHROOM))
            registerButton(10, RelicButtons.WILL_RELIC(WillRelic.RAINBOW_CLAY))
            registerButton(12, RelicButtons.WILL_RELIC(WillRelic.CLAY_IMAGE))
            registerButton(14, RelicButtons.WILL_RELIC(WillRelic.MAMMMOTH_RAW_MEET))
            registerButton(16, RelicButtons.WILL_RELIC(WillRelic.CAT_SAND))
            registerButton(18, RelicButtons.WILL_RELIC(WillRelic.ULURU_SCRAP))
            registerButton(26, RelicButtons.WILL_RELIC(WillRelic.SPHINX))
        }
    }

    object AquaRelicMenu : WillRelicMenu(Will.AQUA, 6 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(5, RelicButtons.WILL_RELIC(WillRelic.SHELL))
            registerButton(21, RelicButtons.WILL_RELIC(WillRelic.SAIL))
            registerButton(24, RelicButtons.WILL_RELIC(WillRelic.DEEP_SEA_FISH_DIODE))
            registerButton(29, RelicButtons.WILL_RELIC(WillRelic.SEICHI_MACKEREL))
            registerButton(33, RelicButtons.WILL_RELIC(WillRelic.MUSH_FISH))
            registerButton(38, RelicButtons.WILL_RELIC(WillRelic.STEERING_WHEEL))
            registerButton(42, RelicButtons.WILL_RELIC(WillRelic.WOOD_SLAB))
            registerButton(48, RelicButtons.WILL_RELIC(WillRelic.BROKEN_WATERMELON))
            registerButton(50, RelicButtons.WILL_RELIC(WillRelic.CUTE_WATERING_POT))
        }
    }

    object IgnisRelicMenu : WillRelicMenu(Will.IGNIS, 5 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(4, RelicButtons.WILL_RELIC(WillRelic.BEAST_BONE))
            registerButton(12, RelicButtons.WILL_RELIC(WillRelic.THIN_TOOTH))
            registerButton(14, RelicButtons.WILL_RELIC(WillRelic.BROKEN_IVORY))
            registerButton(20, RelicButtons.WILL_RELIC(WillRelic.WILL_O_WISP))
            registerButton(24, RelicButtons.WILL_RELIC(WillRelic.BIG_FUNG))
            registerButton(29, RelicButtons.WILL_RELIC(WillRelic.CAMP_FIRE_TRACE))
            registerButton(33, RelicButtons.WILL_RELIC(WillRelic.DESERT_CRYSTAL))
            registerButton(39, RelicButtons.WILL_RELIC(WillRelic.CARNIVORE_BONE))
            registerButton(41, RelicButtons.WILL_RELIC(WillRelic.FRIED_POTATO))
        }
    }

    object AerRelicMenu : WillRelicMenu(Will.AER, 4 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(2, RelicButtons.WILL_RELIC(WillRelic.BROKEN_BOW))
            registerButton(4, RelicButtons.WILL_RELIC(WillRelic.TIME_CAPSEL))
            registerButton(6, RelicButtons.WILL_RELIC(WillRelic.BROKEN_FORCE_FLAG))
            registerButton(12, RelicButtons.WILL_RELIC(WillRelic.BITTEN_LEATHER_BOOT))
            registerButton(14, RelicButtons.WILL_RELIC(WillRelic.BROKEN_LEAD))
            registerButton(19, RelicButtons.WILL_RELIC(WillRelic.OLD_AXE))
            registerButton(22, RelicButtons.WILL_RELIC(WillRelic.VODKA_BOTTLE))
            registerButton(25, RelicButtons.WILL_RELIC(WillRelic.ACID_GEAR))
            registerButton(30, RelicButtons.WILL_RELIC(WillRelic.SLICED_ROPE))
            registerButton(32, RelicButtons.WILL_RELIC(WillRelic.WEB_SCRAP))
        }
    }

    object NaturaRelicMenu : WillRelicMenu(Will.NATURA, 6 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(3, RelicButtons.WILL_RELIC(WillRelic.CACAO_WATERMELON))
            registerButton(5, RelicButtons.WILL_RELIC(WillRelic.BIRCH_MUSHROOM))
            registerButton(11, RelicButtons.WILL_RELIC(WillRelic.EGGPLANT))
            registerButton(15, RelicButtons.WILL_RELIC(WillRelic.WHITE_FLOWER))
            registerButton(21, RelicButtons.WILL_RELIC(WillRelic.SUMMER_DAY))
            registerButton(23, RelicButtons.WILL_RELIC(WillRelic.BANANA_SKIN))
            registerButton(31, RelicButtons.WILL_RELIC(WillRelic.INSECT_HORN))
            registerButton(40, RelicButtons.WILL_RELIC(WillRelic.BROWN_SAP))
            registerButton(49, RelicButtons.WILL_RELIC(WillRelic.DOWN_TREE))
        }
    }

    object GlaciesRelicMenu : WillRelicMenu(Will.GLACIES, 6 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(4, RelicButtons.WILL_RELIC(WillRelic.CRYSTAL_OF_SNOW))
            registerButton(11, RelicButtons.WILL_RELIC(WillRelic.FROSTED_FISH))
            registerButton(15, RelicButtons.WILL_RELIC(WillRelic.NOT_MELTTED_ICE))
            registerButton(22, RelicButtons.WILL_RELIC(WillRelic.FROSTED_CRAFTBOX))
            registerButton(28, RelicButtons.WILL_RELIC(WillRelic.FROSTED_PINECONE))
            registerButton(34, RelicButtons.WILL_RELIC(WillRelic.ICICLE))
            registerButton(40, RelicButtons.WILL_RELIC(WillRelic.FROSTED_WHEEL))
            registerButton(47, RelicButtons.WILL_RELIC(WillRelic.SOFT_RIME))
            registerButton(51, RelicButtons.WILL_RELIC(WillRelic.CRAMPONS))
        }
    }

    object SolumRelicMenu : WillRelicMenu(Will.SOLUM, 5 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(2, RelicButtons.WILL_RELIC(WillRelic.A_PIECE_OF_CHALK))
            registerButton(6, RelicButtons.WILL_RELIC(WillRelic.BROKEN_TRAP))
            registerButton(19, RelicButtons.WILL_RELIC(WillRelic.FLUX_FOSSIL))
            registerButton(21, RelicButtons.WILL_RELIC(WillRelic.FROSTED_ORE))
            registerButton(23, RelicButtons.WILL_RELIC(WillRelic.MYCELIUM_PICKAXE))
            registerButton(25, RelicButtons.WILL_RELIC(WillRelic.BEAUTIFUL_ORE))
            registerButton(36, RelicButtons.WILL_RELIC(WillRelic.IRON_ARMOR))
            registerButton(40, RelicButtons.WILL_RELIC(WillRelic.INDIGO))
            registerButton(44, RelicButtons.WILL_RELIC(WillRelic.DIAMOND_STONE))
        }
    }

    object VentusRelicMenu : WillRelicMenu(Will.VENTUS, 6 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(6, RelicButtons.WILL_RELIC(WillRelic.RUSTED_COMPASS))
            registerButton(11, RelicButtons.WILL_RELIC(WillRelic.BEAUTIFUL_WING))
            registerButton(13, RelicButtons.WILL_RELIC(WillRelic.TUMBLEWEED))
            registerButton(15, RelicButtons.WILL_RELIC(WillRelic.HORN))
            registerButton(23, RelicButtons.WILL_RELIC(WillRelic.SYLPH_LEAFE))
            registerButton(29, RelicButtons.WILL_RELIC(WillRelic.TENT_CLOTH))
            registerButton(31, RelicButtons.WILL_RELIC(WillRelic.PRICKLE))
            registerButton(39, RelicButtons.WILL_RELIC(WillRelic.WING))
            registerButton(46, RelicButtons.WILL_RELIC(WillRelic.NIDUS_AVIS))
        }
    }

    object LuxRelicMenu : WillRelicMenu(Will.LUX, 6 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(4, RelicButtons.WILL_RELIC(WillRelic.ELIXIR))
            registerButton(12, RelicButtons.WILL_RELIC(WillRelic.OLD_MESSAGE_BOTTLE))
            registerButton(14, RelicButtons.WILL_RELIC(WillRelic.WHITE_APPLE))
            registerButton(19, RelicButtons.WILL_RELIC(WillRelic.BUDDHIST_STATUE))
            registerButton(22, RelicButtons.WILL_RELIC(WillRelic.TREASURE_CASKET))
            registerButton(25, RelicButtons.WILL_RELIC(WillRelic.JIZO))
            registerButton(30, RelicButtons.WILL_RELIC(WillRelic.LIGHTNING_MOSS))
            registerButton(32, RelicButtons.WILL_RELIC(WillRelic.RED_DUST))
            registerButton(49, RelicButtons.WILL_RELIC(WillRelic.BLUE_DUST))
        }
    }

    object UmbraRelicMenu : WillRelicMenu(Will.UMBRA, 6 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(4, RelicButtons.WILL_RELIC(WillRelic.BEAST_HORN))
            registerButton(11, RelicButtons.WILL_RELIC(WillRelic.BOTTLED_LIQUID))
            registerButton(15, RelicButtons.WILL_RELIC(WillRelic.SLIME_LEES))
            registerButton(22, RelicButtons.WILL_RELIC(WillRelic.PURPLE_CHEESE))
            registerButton(28, RelicButtons.WILL_RELIC(WillRelic.SHADE_ARMOR))
            registerButton(30, RelicButtons.WILL_RELIC(WillRelic.ORICHALCUM))
            registerButton(32, RelicButtons.WILL_RELIC(WillRelic.DARK_MATTER))
            registerButton(34, RelicButtons.WILL_RELIC(WillRelic.BLACK_CLOTH))
            registerButton(48, RelicButtons.WILL_RELIC(WillRelic.BLOODSTAINED_SWORD))
            registerButton(50, RelicButtons.WILL_RELIC(WillRelic.RLYEH_TEXT))
        }
    }

    object SakuraRelicMenu : WillRelicMenu(Will.SAKURA, 5 * 9) {
        init {
            registerButton(0, BackButton(this, relicMenu))
            registerButton(3, RelicButtons.WILL_RELIC(WillRelic.ALSTROMERIA_SEED))
            registerButton(5, RelicButtons.WILL_RELIC(WillRelic.NIGHTINGALE_FEATHER))
            registerButton(11, RelicButtons.WILL_RELIC(WillRelic.OBOROZUKI_SWORD))
            registerButton(15, RelicButtons.WILL_RELIC(WillRelic.SAKURA_RACE_CAKE))
            registerButton(22, RelicButtons.WILL_RELIC(WillRelic.CERTIFICATE))
            registerButton(29, RelicButtons.WILL_RELIC(WillRelic.SCHOOL_BAG))
            registerButton(33, RelicButtons.WILL_RELIC(WillRelic.KATSUO_SASHIMI))
            registerButton(39, RelicButtons.WILL_RELIC(WillRelic.BOTAMOCHI))
            registerButton(41, RelicButtons.WILL_RELIC(WillRelic.PEACH_CORE))
        }
    }

}
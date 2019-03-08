package click.seichi.gigantic.item

import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author unicroak
 */
class Armor(private val material: Material,
            private val localizedDisplayName: LocalizedText,
            private val localizedLore: List<LocalizedText>
) : Icon {

    override fun toShownItemStack(player: Player) = itemStackOf(material) {
        setDisplayName(player, localizedDisplayName)
        addLore(player, localizedLore)
        sublime()
        setEnchanted(true)
    }

}
package click.seichi.gigantic.metadata

import click.seichi.gigantic.Gigantic
import org.bukkit.block.Block
import org.bukkit.metadata.FixedMetadataValue

/**
 * @author tar0ss
 */
/**************************************
 * メタデータを使用する場合は付与している理由と共に記載すること
 *
 *************************************/
enum class GiganticMetadata(val identifier: String) {
    //スキルで使用中のブロックに付与される
    SKILLED("skilled")
    ;

    fun setMetadata(block: Block) =
            block.setMetadata(identifier, FixedMetadataValue(Gigantic.PLUGIN, true))

    fun removeMetadata(block: Block) =
            block.removeMetadata(identifier, Gigantic.PLUGIN)
}
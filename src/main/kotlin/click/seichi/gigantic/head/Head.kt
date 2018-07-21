package click.seichi.gigantic.head

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*


/**
 * @author tar0ss
 */
enum class Head(
        private val urlString: String
) {
    // raid boss heads
    PIG("http://textures.minecraft.net/texture/621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4"),
    // menu heads
    LEFT("http://textures.minecraft.net/texture/3ebf907494a935e955bfcadab81beafb90fb9be49c7026ba97d798d5f1a23"),
    ;

    private val baseItemStack: ItemStack by lazy {
        val skull = ItemStack(Material.SKULL_ITEM, 1, 3)

        skull.itemMeta = skull.itemMeta.apply {
            val profile = GameProfile(UUID.randomUUID(), null)

            val encodedData = Base64.getEncoder()
                    .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", urlString)
                            .toByteArray())
            profile.properties.put("textures", Property("textures", String(encodedData)))

            val profileField = javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField.set(this, profile)
        }

        skull
    }

    fun toItemStack() = baseItemStack.clone()
}
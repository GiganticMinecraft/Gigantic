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
    MOLE("http://textures.minecraft.net/texture/b19f3d688a64af5ce837f74e73cd98838fa819c017fce49f57498c6e4dae7ac"),
    FROG("http://textures.minecraft.net/texture/d1ef9f1ae4d2bcbbdf77845f9cb3d355cdcadd4dfbbe9e7cc3b5298de26da6f"),
    THE_EARTH("http://textures.minecraft.net/texture/b1dd4fe4a429abd665dfdb3e21321d6efa6a6b5e7b956db9c5d59c9efab25"),
    STEEL("http://textures.minecraft.net/texture/a97adb4f69a93f576c8f79648ef3dc259af5c6dfd023c236e06f8924c69291"),
    BIRD("http://textures.minecraft.net/texture/64d53333f18ccd7a7fae59f8415a438ec9b6535998962695cc4f68ed2d31"),
    GUARDIAN_OF_THE_FOREST("http://textures.minecraft.net/texture/2dfe709da2cf32e1e273f5d72c66fc36dc64366fecd980aa6b1480d79bc64cd7"),
    TURKEY("http://textures.minecraft.net/texture/d4faa6771b5de536ebed6c85366ab343f2ef53269ead1173a84dae42d6cf2"),
    PINK_MUSHROOM("http://textures.minecraft.net/texture/167f1225d1ebb96ac85355c949e8ce23f6e1222aa66c09566285c6c27435163"),
    RAINBOW("http://textures.minecraft.net/texture/3386f9b0b1d9879c3da33c7a8ca2440c1e411fe93c27c9dbbff56bd697bb7375"),
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
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
    MOLE("http://textures.minecraft.net/texture/7d2e9feca25c973ef63c982dcc37d3cf7671ceca521034db6923d9f018ff7fa8"),
    RESET_SAN("http://textures.minecraft.net/texture/b19f3d688a64af5ce837f74e73cd98838fa819c017fce49f57498c6e4dae7ac"),
    FROG("http://textures.minecraft.net/texture/d1ef9f1ae4d2bcbbdf77845f9cb3d355cdcadd4dfbbe9e7cc3b5298de26da6f"),
    THE_EARTH("http://textures.minecraft.net/texture/b1dd4fe4a429abd665dfdb3e21321d6efa6a6b5e7b956db9c5d59c9efab25"),
    STEEL("http://textures.minecraft.net/texture/a97adb4f69a93f576c8f79648ef3dc259af5c6dfd023c236e06f8924c69291"),
    BIRD("http://textures.minecraft.net/texture/64d53333f18ccd7a7fae59f8415a438ec9b6535998962695cc4f68ed2d31"),
    GUARDIAN_OF_THE_FOREST("http://textures.minecraft.net/texture/2dfe709da2cf32e1e273f5d72c66fc36dc64366fecd980aa6b1480d79bc64cd7"),
    TURKEY("http://textures.minecraft.net/texture/d4faa6771b5de536ebed6c85366ab343f2ef53269ead1173a84dae42d6cf2"),
    PINK_MUSHROOM("http://textures.minecraft.net/texture/167f1225d1ebb96ac85355c949e8ce23f6e1222aa66c09566285c6c27435163"),
    RAINBOW("http://textures.minecraft.net/texture/3386f9b0b1d9879c3da33c7a8ca2440c1e411fe93c27c9dbbff56bd697bb7375"),
    MERMAID("http://textures.minecraft.net/texture/52eb6f41adb8803f2b6a3ce1d7df253cb73131607216a9ff3161cf4af619636"),
    MERMAN("http://textures.minecraft.net/texture/7f44e58c2794f4ab8599dfc671b649bf775402a4dcbf26fe780f954963797b"),
    TRITON("http://textures.minecraft.net/texture/a158ab215a93f5c456378935e6a9fe4aeb1f92ffe41a2739e51e8742c1d"),
    BISMARCK("http://textures.minecraft.net/texture/6d8a76ac45b84f50b6660ed1d6756f3f82e6662e073a569ee2507f35ba25b914"),
    STONE("http://textures.minecraft.net/texture/41269584f629227713107b4e0a02dd65ddfe780e7c7118cb1ec2275c514cc95d"),
    MOTHER("http://textures.minecraft.net/texture/b58677cf4429696d34e0fb790821091e5f58acf2698f7d57ee03b75491c264b6"),
    // menu heads
    LEFT("http://textures.minecraft.net/texture/3ebf907494a935e955bfcadab81beafb90fb9be49c7026ba97d798d5f1a23"),
    PUMPKIN_RIGHT_ARROW("http://textures.minecraft.net/texture/c9895d58a04626d5cce18769d54198d0355fd770683bdfb7176d08c7547de"),
    PUMPKIN_LEFT_ARROW("http://textures.minecraft.net/texture/28a86bb4fcac3862aad3d51393faa7a1cf3553e4934103ed45ac2a9a888eb9a"),
    JEWELLERY_BOX("http://textures.minecraft.net/texture/3de2984bd5645f9d865a9e8c69352ad4e0109a5b38f3a4243e4d6cb92c8333"),
    DIAMOND_JEWELLERY("http://textures.minecraft.net/texture/fbeade3a333063f0c0ca8426636a71344dc278e561a72f57a39735b097719f85"),
    RUBY_JEWELLERY("http://textures.minecraft.net/texture/2aba74d812f3c5e97ad0f1e6cb1d24fc9e13788196cf1bc473211ff142beab"),
    EMERALD_JEWELLERY("http://textures.minecraft.net/texture/84ab77eefad0b0cdbdf3261a7a4729d5504d6f96d3c162832197443ebe346e6"),
    SAPPHIRE_JEWELLERY("http://textures.minecraft.net/texture/6183c88db98426c64c37e6d789d4ec1e3de43efaafe4be161961ef943dbe83"),
    ;

    private val baseItemStack: ItemStack by lazy {
        val skull = ItemStack(Material.PLAYER_HEAD)

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
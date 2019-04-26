package click.seichi.gigantic.head

import click.seichi.gigantic.extension.itemStackOf
import click.seichi.gigantic.util.Random
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*


/**
 * @author tar0ss
 */
enum class Head(
        private val urlString: String
) {
    // soul monster heads
    VILLAGER("http://textures.minecraft.net/texture/822d8e751c8f2fd4c8942c44bdb2f5ca4d8ae8e575ed3eb34c18a86e93b"),
    ZOMBIE_VILLAGER("http://textures.minecraft.net/texture/e5e08a8776c1764c3fe6a6ddd412dfcb87f41331dad479ac96c21df4bf3ac89c"),
    PIG("http://textures.minecraft.net/texture/621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4"),
    PIG_WARRIOR("http://textures.minecraft.net/texture/c343b0f771dbbe4a53a3b8fe323ec0a3fef35da3ddaccda56b706a6f5491d796"),
    MR_PIG("http://textures.minecraft.net/texture/a1a15a9e301c3dd18ff1464bbd10972ab9e9df164533c33822de79898ae2cd"),
    BLAZE("http://textures.minecraft.net/texture/b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0"),
    BLAZE_WARRIOR("http://textures.minecraft.net/texture/b96ff145ea18d61601518c646bae678f5fde096cf0360ec36cadac41425534e"),
    BLUE_BLAZE("http://textures.minecraft.net/texture/55a13bb48e3595b55de8dd6943fc38db5235371278c695bd453e49a0999"),
    CHICKEN("http://textures.minecraft.net/texture/1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893"),
    CHICKEN_KING("http://textures.minecraft.net/texture/384c0a970e291c5e877ed790ff429d57592eceb29cbbf955786e92cf13d9f701"),
    KING_CROWN("http://textures.minecraft.net/texture/45587da7fe7336e8ab9f791ea5e2cfc8a827ca959567eb9d53a647babf948d5"),
    WITHER_SKELETON("http://textures.minecraft.net/texture/7953b6c68448e7e6b6bf8fb273d7203acd8e1be19e81481ead51f45de59a8"),
    WITHER("http://textures.minecraft.net/texture/cdf74e323ed41436965f5c57ddf2815d5332fe999e68fbb9d6cf5c8bd4139f"),
    LADON("http://textures.minecraft.net/texture/f7cba5b68ee7ac9b93dc4f684c528c5efd7e78c6ac77cefd1111bae6cdc84f"),
    UNDINE("http://textures.minecraft.net/texture/20014da813f52b384ad631e6811ee88c3d832f821319f60f7b21360d402f5bba"),
    SALAMANDRA("http://textures.minecraft.net/texture/125522e5f235011d71bce3832ac1560d94b346e773dae187e2b3d4a0b5561a"),
    SYLPHID("http://textures.minecraft.net/texture/0158d4889105f6a63cd7d3b16433f683d21329f771af69859c363206ff84332"),
    NOMOS("http://textures.minecraft.net/texture/c92c157083adcd2c94d4bfbea2e4cee7f47bf7c9a525674bce9923df0e751"),
    LOA("http://textures.minecraft.net/texture/d8da68d4d692ce36e9d2a151f6d3fa9c8a2a533169647a818d8c643d7030ed"),
    TURTLE("http://textures.minecraft.net/texture/0a4050e7aacc4539202658fdc339dd182d7e322f9fbcc4d5f99b5718a"),
    TURTLE_SOLDIER("http://textures.minecraft.net/texture/99d6712582d60a0058e4fbee0e89dd089fbabb80f07e4a0874904c91bc48f08a"),
    TURTLE_KING("http://textures.minecraft.net/texture/5b78f7674d1063255070fffb2edc1f2b9314fe6813a9eab8444c0353a723d3d2"),
    SKELETON("http://textures.minecraft.net/texture/301268e9c492da1f0d88271cb492a4b302395f515a7bbf77f4a20b95fc02eb2"),
    SKELETON_SOLDIER("http://textures.minecraft.net/texture/8807df8a2cdb58500c7d266433c3aa3593d23dd2e4c79f48dadfa86bc9bf5d1d"),
    SKELETON_KING("http://textures.minecraft.net/texture/8c78d2102db75f1b3744a5e7e9baccf88fda4cc4979ebc0a81b7d9eb5721c0"),
    SPIDER("http://textures.minecraft.net/texture/cd541541daaff50896cd258bdbdd4cf80c3ba816735726078bfe393927e57f1"),
    CAVE_SPIDER("http://textures.minecraft.net/texture/41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224"),
    SPIDER_KING("http://textures.minecraft.net/texture/cf0622b3998d42b34d5bc760bb2c83fdbc6e68fab05b7ea17b35097ed81190d6"),
    ZOMBIE("http://textures.minecraft.net/texture/56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11"),
    ZOMBIE_SOLDIER("http://textures.minecraft.net/texture/1cc37027e56fabdd04bd3574fe0f413bc165cdf9f8df96abf7fc053a7d2e96cb"),
    ZOMBIE_KING("http://textures.minecraft.net/texture/15582ed512a8cea9b17ffc31ca12b86f2abccf83a622fb7d15b5f171f51bb64"),
    ORC("http://textures.minecraft.net/texture/f9f238cf76e42caf5b6d4dfdb502212e4d9cc6ada916af3cd3eeb8103efac54"),
    ORC_SOLDIER("http://textures.minecraft.net/texture/f50d62765c22701bd4b92e5537ef959a721a0ad736e7b0803b4f11131fd521d8"),
    ORC_KING("http://textures.minecraft.net/texture/ebc70392d68143293a0429aeec268fa5f33ed5b43aadef792bd2e815f594811f"),
    GHOST("http://textures.minecraft.net/texture/ef7a4f95e5fe99b45be61bb33882c12a93b22d297fd1765ab21e7748dc6b8cf3"),
    WHITE_GHOST("http://textures.minecraft.net/texture/abedb8d4b06eeb979ee515f778f31b3deef92fb5817f3452f51fc58d48134"),
    GHOST_KING("http://textures.minecraft.net/texture/dffd9ee22dfaf44935ff470835893288a52baadb63fd494bf53a232835b5523e"),
    GRAY_PARROT("http://textures.minecraft.net/texture/1bb292942eb2417a234c375fe38af8a25e22e4b8b9bf1a8824012c901eaedbec"),
    RED_PARROT("http://textures.minecraft.net/texture/f0bfa850f5de4b2981cce78f52fc2cc7cd7b5c62caefeddeb9cf311e83d9097"),
    ELDER_PARROT("http://textures.minecraft.net/texture/1bb292942eb2417a234c375fe38af8a25e22e4b8b9bf1a8824012c901eaedbec"),
    // no
    TIGER("http://textures.minecraft.net/texture/a38137ea31286bb0a24b8a6db91fc01ee0bbad851d5e18af0ebe29a97e7"),
    WHITE_TIGER("http://textures.minecraft.net/texture/28fa2b355e9064371028cb6c9d49c90aaadf6ac11fad748c24ba8db067acd"),
    LION("http://textures.minecraft.net/texture/94cbc4e76cacb8e6c6651ee09948e8999cc7cf3de8a0fdd91f4275bf3f9961"),
    BLACK_LION("http://textures.minecraft.net/texture/f83ccbf003188f3592cf077891b1bd19ffb2478644cb095c33bfb6940ac908d"),
    BEAR("http://textures.minecraft.net/texture/83c8e74255ac3a475125f5379ee8ddc76e24c9d3cf1af3614359493a6ffb42b"),
    // ホッキョクグマ
    POLAR_BEAR("http://textures.minecraft.net/texture/b117bbe4b9e86cbfc2a28126ec798c33f5cfb8123f533cb9f61f488d85156"),
    // リス
    SQUIRREL("http://textures.minecraft.net/texture/b196f8f74b2b84df66bb033c1575b83db728ad1dc4d58fdcd4d6c6cf52a6d"),
    // わし
    EAGLE("http://textures.minecraft.net/texture/7c19bdf5ec16fd93825d738c689c1967548574ce7d62ffcd74b32e9bfd5ae9"),

    ENDER_MAN("http://textures.minecraft.net/texture/7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf"),
    WHITE_ENDER_MAN("http://textures.minecraft.net/texture/14144bd4ac16c4eb753b8e855f31f8fcb41b66333352e2b9f748f6ec411"),
    ELITE_ENDER_MAN("http://textures.minecraft.net/texture/eb07594e2df273921a77c101d0bfdfa1115abed5b9b2029eb496ceba9bdbb4b3"),
    SLIME("http://textures.minecraft.net/texture/895aeec6b842ada8669f846d65bc49762597824ab944f22f45bf3bbb941abe6c"),
    RAINBOW_SLIME("http://textures.minecraft.net/texture/b8be1e54625bb1c477832ed4e5f8d7261ee38f180b0883d1060c212b5c8b71"),
    MOISTENED_SLIME("http://textures.minecraft.net/texture/ad2010b89b83bb21913f41ece3a1736d98c9955e8de5d3e35f5b6d19c8c5ea1"),
    DOLPHIN("http://textures.minecraft.net/texture/8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3"),
    PINK_DOLPHIN("http://textures.minecraft.net/texture/a4208fa0fa6901eb23fe0de4a02b57cb661ac9ed9d4f01dfdf2d073397105ec1"),
    PHANTOM("http://textures.minecraft.net/texture/7e95153ec23284b283f00d19d29756f244313a061b70ac03b97d236ee57bd982"),
    BLACK_PHANTOM("http://textures.minecraft.net/texture/40b9189c3713f0dacac9b2bb6065090c52b0c90f108208e0a86be5885e99579a"),

    ELEPHANT("http://textures.minecraft.net/texture/7071a76f669db5ed6d32b48bb2dba55d5317d7f45225cb3267ec435cfa514"),
    AFRICA_ELEPHANT("http://textures.minecraft.net/texture/315de7ec204ef5d819862a111a799fe5155940affe8d6b29f1bc6c4242a48c"),
    // raid boss heads
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
    // effect heads
    RAINBOW_WOOL("http://textures.minecraft.net/texture/44b03794b9b3e3b5d07e3be68b96af87df215c3752e54736c80f7d50bd3437a4"),
    ;

    companion object {
        fun getOfflinePlayerHead(uniqueId: UUID): ItemStack {
            val offlinePlayer = Bukkit.getOfflinePlayer(uniqueId)
            if (offlinePlayer.name == null) {
                return itemStackOf(Random.nextEgg())
            }
            val itemStack = ItemStack(Material.PLAYER_HEAD, 1)
            val skullMeta = itemStack.itemMeta as SkullMeta
            skullMeta.owningPlayer = offlinePlayer
            skullMeta.setDisplayName("${ChatColor.YELLOW}${ChatColor.BOLD}${offlinePlayer.name}")
            itemStack.itemMeta = skullMeta
            return itemStack
        }
    }

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
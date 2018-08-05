package click.seichi.gigantic.listener

import org.bukkit.event.Listener

/**
 * @author tar0ss
 */
class PlayerMonitor : Listener {
//
//    @EventHandler(priority = EventPriority.MONITOR)
//    fun onBlockBreak(event: BlockBreakEvent) {
//        if (event.isCancelled) return
//        if (event.player.gameMode != GameMode.SURVIVAL) return
//
//        // Gravity process
//        fallUpperBlock(event.block)
//
//        // Player process
//        val player = event.player ?: return
//        val gPlayer = player.gPlayer ?: return
//        val level = player.gPlayer?.level ?: return
//        val location = event.block.location.central
//
//        // carry player data
//        gPlayer.mineBlock.add(1L)
//        gPlayer.mineCombo.combo(1L)
//        val isLevelUp = level.updateLevel(ExpProducer.calcExp(player)) {
//            Bukkit.getPluginManager().callEvent(LevelUpEvent(it, player))
//        }
//
//        // raid battle process
//        RaidManager
//                .getBattleList()
//                .firstOrNull { it.isJoined(player) }
//                ?.run {
//                    val attackDamage = 1.0.times(when (gPlayer.mineCombo.currentCombo) {
//                        in 0..9 -> 1.0
//                        in 10..29 -> 1.1
//                        in 30..69 -> 1.2
//                        in 70..149 -> 1.3
//                        in 150..349 -> 1.4
//                        in 350..799 -> 1.5
//                        in 800..1199 -> 1.6
//                        else -> 1.7
//                    })
//                    raidBoss.damage(player, attackDamage)
//                    if (raidBoss.isDead()) {
//
//                        getJoinedPlayerSet().forEach {
//                            BattleSounds.WIN.playOnly(player)
//                            raidBoss.getDrop(player)?.run {
//                                gPlayer.raidData.addRelic(this)
//                                BattleMessages.GET_RELIC(this).sendTo(player)
//                            }
//                            if (raidBoss.isDefeated(player)) {
//                                gPlayer.raidData.defeat(boss)
//                                BattleMessages.DEFEAT_BOSS(boss).sendTo(player)
//                            }
//                        }
//                        RaidManager.endBattle(this)
//                        RaidManager.newBattle()
//                    }
//                    carry()
//                }
//
//
//        // Displays
//        PlayerMessages.EXP_BAR_DISPLAY(level).sendTo(player)
//        SkillPops.MINE_COMBO(gPlayer.mineCombo).pop(event.block.centralLocation.add(0.0, 0.0, 0.0))
//
//
//        // Animations
//        if (gPlayer.mineBurst.duringFire()) {
//            SkillAnimations.MINE_BURST_ON_BREAK.start(location)
//        }
//
//        // Sounds
//        when {
//            isLevelUp -> PlayerSounds.LEVEL_UP.play(location)
//            gPlayer.mineBurst.duringFire() -> SkillSounds.MINE_BURST_ON_BREAK.play(location)
//            else -> PlayerSounds.OBTAIN_EXP.playOnly(player)
//        }
//    }
//
//    @Suppress("DEPRECATION")
//    private fun fallUpperBlock(block: Block?) {
//        var count = 0
//
//        val fallTask = object : Runnable {
//            override fun run() {
//                val target = block?.getRelative(0, count + 1, 0) ?: return
//                if (target.isCrust) {
//                    target.location.world.spawnFallingBlock(
//                            target.location.central.subtract(0.0, 0.5, 0.0),
//                            target.type,
//                            target.data
//                    )
//                    target.type = Material.AIR
//                    count++
//                    Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, this, 2L)
//                }
//            }
//        }
//        Bukkit.getScheduler().runTask(Gigantic.PLUGIN, fallTask)
//    }

}
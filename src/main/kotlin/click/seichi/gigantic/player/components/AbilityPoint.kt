package click.seichi.gigantic.player.components

import click.seichi.gigantic.player.GiganticPlayer

/**
 * @author tar0ss
 */
class AbilityPoint {

    enum class AbilityPointConsumer(private val consuming: (GiganticPlayer) -> Int) {
        EXPLOSION_LEVEL(
                // TODO implements
                { it.status.explosionLevel }
        )
        ;

        fun consume(gPlayer: GiganticPlayer) = consuming(gPlayer)
    }

    enum class AbilityPointProducer(private val producing: (GiganticPlayer) -> Int) {
        SEICHI_LEVEL(
                { it.status.seichiLevel.current * 3 }
        )
        ;

        fun produce(gPlayer: GiganticPlayer) = producing(gPlayer)
    }

    private val producedPoint: (GiganticPlayer) -> Int = { gPlayer ->
        AbilityPointProducer.values().sumBy { it.produce(gPlayer) }
    }

    private val consumedPoint: (GiganticPlayer) -> Int = { gPlayer ->
        AbilityPointConsumer.values().sumBy { it.consume(gPlayer) }
    }

    var current: (GiganticPlayer) -> Int = { gPlayer ->
        (producedPoint(gPlayer) - consumedPoint(gPlayer)).coerceAtLeast(0)
    }

}
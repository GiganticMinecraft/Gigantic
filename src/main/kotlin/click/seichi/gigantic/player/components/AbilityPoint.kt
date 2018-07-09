package click.seichi.gigantic.player.components

import click.seichi.gigantic.player.GiganticPlayer

/**
 * @author tar0ss
 */
class AbilityPoint {

    enum class AbilityPointConsumer(private val consuming: (GiganticPlayer) -> Int) {
        EXPLOSION_LEVEL(
                // TODO implements
                { it.explosionLevel }
        )
        ;

        fun consume(gPlayer: GiganticPlayer) = consuming(gPlayer)
    }

    enum class AbilityPointProducer(private val producing: (GiganticPlayer) -> Int) {
        SEICHI_LEVEL(
                { it.level.current * 3 }
        )
        ;

        fun produce(gPlayer: GiganticPlayer) = producing(gPlayer)
    }

    private val produceCalculating: (GiganticPlayer) -> Int = { gPlayer ->
        AbilityPointProducer.values().sumBy { it.produce(gPlayer) }
    }

    private val consumeCalculating: (GiganticPlayer) -> Int = { gPlayer ->
        AbilityPointConsumer.values().sumBy { it.consume(gPlayer) }
    }

    private val currentCalculating: (GiganticPlayer) -> Int = { gPlayer ->
        (produceCalculating(gPlayer) - consumeCalculating(gPlayer)).coerceAtLeast(0)
    }

}
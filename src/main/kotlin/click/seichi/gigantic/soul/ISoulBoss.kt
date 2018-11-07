package click.seichi.gigantic.soul

import click.seichi.gigantic.head.Head
import click.seichi.gigantic.relic.Relic

/**
 * @author tar0ss
 */
interface ISoulBoss : ISoulMonster {
    val head: Head
    val relicSet: Set<Relic>
}
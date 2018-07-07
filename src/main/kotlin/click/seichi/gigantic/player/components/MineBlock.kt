package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.User

class MineBlock(user: User) {
    var current = user.mineBlock
}

package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.User

class Mana(user: User) {

    var current = user.mana
}

package click.seichi.gigantic.menu.menus.shop

import click.seichi.gigantic.item.items.menu.ShopButtons
import click.seichi.gigantic.menu.BookMenu

/**
 * @author tar0ss
 */
abstract class GiftShopMenu : BookMenu() {

    init {
        // 購入方法を選択 (2..6)
        registerButton(2, ShopButtons.VOTE)
        registerButton(3, ShopButtons.DONATION)

        // エフェクトメニューへ
        // バッグから直接飛べるので無し
//        registerButton(7, ShopButtons.EFFECT_MENU)
    }

}
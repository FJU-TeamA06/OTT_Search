package com.fjuTeam15.ottsearch

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Item (
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int )

class Datalist() {

    fun itemList(): MutableList<Item> {
        return mutableListOf<Item>(
            Item(R.string.text1, R.drawable.img),
            Item(R.string.text2, R.drawable.img_1),
            Item(R.string.text3, R.drawable.pos2),
            Item(R.string.text4, R.drawable.pos3),
            Item(R.string.text5, R.drawable.pos4),
            Item(R.string.text6, R.drawable.ilinetv),
            Item(R.string.text7, R.drawable.ikktv),
            Item(R.string.text8, R.drawable.ibilib),
            Item(R.string.text9, R.drawable.iamazon1),
            Item(R.string.text14, R.drawable.iq)
        )
    }
}

data class Item2 (
    @StringRes val stringResourceId: Int)
class Datalist2() {

    fun hitList(): MutableList<Item2> {
        return mutableListOf<Item2>(
            Item2(R.string.hit1),
            Item2(R.string.hit2),
            Item2(R.string.hit3),
            Item2(R.string.hit4),
            Item2(R.string.hit5),
            Item2(R.string.hit6),
            Item2(R.string.hit7),
            Item2(R.string.hit8),
            Item2(R.string.hit9),
            Item2(R.string.hit10),
        )
    }
}

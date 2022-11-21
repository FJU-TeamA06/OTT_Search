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
            Item(R.string.text5, R.drawable.pos4)
        )
    }
}

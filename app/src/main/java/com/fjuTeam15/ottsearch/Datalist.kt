package com.fjuTeam15.ottsearch

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Item (
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int )

class Datalist() {

    fun itemList(): MutableList<Item> {
        return mutableListOf<Item>(
            Item(R.string.text1, R.drawable.abyssinian),
            Item(R.string.text2, R.drawable.ac),
            Item(R.string.text3, R.drawable.cs)
        )
    }
}

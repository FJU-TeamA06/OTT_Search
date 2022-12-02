package com.fjuTeam15.ottsearch

import java.net.URL

public class Model{
    lateinit var title:String
    lateinit var platform:String
    lateinit var url:String

    constructor(title: String,platform:String,url:String) {
        this.title = title
        this.platform = platform
        this.url = url
    }

    constructor()
}

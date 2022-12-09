package com.fjuTeam15.ottsearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class AboutActivity() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
    fun alertAppInfo(view: View){
        val string: String = getString(R.string.aboutContent)
        AlertDialog.Builder(this)
            .setMessage(string)
            .setTitle(getString(R.string.about_1))
            .setPositiveButton("OK", null)
            .show()
    }
    fun alertHowToUse(view: View){
        val string: String = getString(R.string.explainContent)
        AlertDialog.Builder(this)
            .setMessage(string)
            .setTitle(getString(R.string.about_2))
            .setPositiveButton("OK", null)
            .show()
    }
    fun sendEmail(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:andyching168@gmail.com"))
        startActivity(browserIntent)
    }
    fun alertDev(view: View){
        val string: String = getString(R.string.dev)
        AlertDialog.Builder(this)
            .setMessage(string)
            .setTitle(getString(R.string.about_4))
            .setPositiveButton("OK", null)
            .show()
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AboutActivity> {
        override fun createFromParcel(parcel: Parcel): AboutActivity {
            return AboutActivity(parcel)
        }

        override fun newArray(size: Int): Array<AboutActivity?> {
            return arrayOfNulls(size)
        }
    }
}
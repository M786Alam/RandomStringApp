package com.example.randomstringapp

import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.randomstringapp.model.RandomString
import org.json.JSONObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RandomStringRepository(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getRandomString(length: Int): RandomString? {
        return try {
            val uri = "content://com.iav.considerateness/text".toUri()
            val bundle = Bundle().apply {
                putInt(ContentResolver.QUERY_ARG_LIMIT, length)
            }
            val cursor = context.contentResolver.query(uri, null, bundle, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val dataJson = it.getString(it.getColumnIndexOrThrow("data"))
                    val json = JSONObject(dataJson).getJSONObject("randomText")
                    val value = json.getString("value")
                    val created = ZonedDateTime.parse(json.getString("created"))
                    val formatted = created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    RandomString(value, length, formatted)
                } else null
            }
        } catch (e: Exception) {
            null
        }
    }
}

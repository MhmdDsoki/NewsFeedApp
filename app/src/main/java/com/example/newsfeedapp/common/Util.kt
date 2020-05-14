package com.example.newsfeedapp.common

import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale





class Util {

    companion object {

       // ex : convert time to  2 hour ago
        fun dateToTimeFormat(oldstringDate: String?): String? {
            val p = PrettyTime(Locale(getCountry()))
            var isTime: String? = null
            try {
                val sdf = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH
                )
                val date: Date = sdf.parse(oldstringDate)
                isTime = p.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return isTime
        }

        fun dateFormat(oldstringDate: String?): String? {
            val newDate: String?
            val dateFormat =
                SimpleDateFormat("E, d MMM yyyy", Locale(getCountry()))
            newDate = try {
                val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate)
                dateFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                oldstringDate
            }
            return newDate
        }

       private fun getCountry(): String? {
            val locale: Locale = Locale.getDefault()
            val country: String = java.lang.String.valueOf(locale.getCountry())
            return country.toLowerCase()
        }

        fun getLanguage(): String? {
            val locale: Locale = Locale.getDefault()
            val country: String = java.lang.String.valueOf(locale.getLanguage())
            return country.toLowerCase()
        }


    }
}
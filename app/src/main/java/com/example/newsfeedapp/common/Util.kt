package com.example.newsfeedapp.common

import android.annotation.SuppressLint
import com.example.newsfeedapp.data.model.Article
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


    // ex : convert time to  2 hour ago
    fun dateToTimeFormat(oldStringDate: String?): String? {
            val p = PrettyTime(Locale(getCountry()))
            var isTime: String? = null
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
                val date: Date = sdf.parse(oldStringDate)
                isTime = p.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return isTime
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(oldStringDate: String?): String? {
        if (oldStringDate == null || oldStringDate == "")
            return ""
        val newDate: String?
        val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale(getCountry()))
        newDate = try {
            val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate)
            dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            oldStringDate
        }
        return newDate
    }

    private fun getCountry(): String? =
        Locale.getDefault().country.toLowerCase(Locale.ROOT)


    fun getLanguage(): String? = Locale.getDefault().language


    fun searchQuery(newText: String?, responseList: List<Article>?): MutableList<Article> {
        val responses: MutableList<Article> = ArrayList()
        if (responseList == null || responseList.isEmpty())
            return responses
        for (response in responseList) {
            /*
        Useful constant for the root locale. The root locale is the locale whose language, country, and variant are empty ("") strings.
        This is regarded as the base locale of all locales, and is used as the language/country neutral locale for the locale sensitive operations.
         */
            val name: String? = response.title?.toLowerCase(Locale.ROOT)
            if (newText?.toLowerCase(Locale.ROOT)?.let { name?.contains(it) }!!)
                responses.add(response)
        }

        return responses
    }
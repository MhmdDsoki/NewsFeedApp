package com.example.newsfeedapp.common

import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.junit.Assert.*

class UtilTest {

    @Test
    fun dateFormat_theDateIsEmpty_returnEmptyString() {
        val result = dateFormat("")
        assertThat(result, `is`(""))
    }
    @Test
    fun dateFormat_theDateIsNull_returnEmptyString() {

        val result = dateFormat(null)
        assertThat(result, `is`(""))
    }
    @Test
    fun dateFormat_returnTheNewDate() {
        val date = "2020-06-08T22:56:04Z"
        val result = dateFormat(date)
        assertThat(result, `is`("Mon, 8 Jun 2020"))
    }

}
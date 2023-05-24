package com.ibaevzz.sportsquiz.db

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromListToString(list: List<String>): String{
        var str: String = ""
        list.forEach { str+= "$it;;;" }
        return str
    }

    @TypeConverter
    fun fromStringToList(str: String): List<String>{
        return str.split(";;;").filter { it!="" }
    }
}
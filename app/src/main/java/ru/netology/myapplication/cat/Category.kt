package ru.netology.myapplication.cat

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

enum class KitchenType(val i: Int) {
    EUROPEAN(1), ASIAN(2), PAN_ASIAN(3), ORIENTAL(4), AMERICAN(5), RUSSIAN(6), MEDITERRANEAN(7);
    companion object {
        fun getByValue(value: Int) = KitchenType.values().find { it.i == value }
    }
}
@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromKitchenType(value: KitchenType?): Int? {
        return value?.let { value.ordinal }
    }

    @TypeConverter
    fun toKitchenType(value: Int?): KitchenType? {
        return KitchenType.getByValue(value ?: 1)
    }
}
package com.familyapps.cashflow.infraestructure

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun convertInstantToLong(incomingInstant: Instant): Long {
            return incomingInstant.toEpochMilli()
        }

        @TypeConverter
        @JvmStatic
        fun convertLongToInstant(incomingString: Long): Instant {
            return Instant.ofEpochMilli(incomingString)
        }
    }

}
package com.familyapps.cashflow.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "USER")
class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "USER_ID")
    var id: Int? = null

    @ColumnInfo(name = "FIRST_NAME")
    var firstName: String = ""

    @ColumnInfo(name = "LAST_NAME")
    var lastName: String = ""

    @ColumnInfo(name = "SECOND_LAST_NAME")
    var secondLastName: String? = null

    @ColumnInfo(name = "EMAIL")
    var email: String = ""

    @ColumnInfo(name = "AGE")
    var age: Int? = null

    @ColumnInfo(name = "ADDRESS")
    var address: String? = null

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false

        if(javaClass != other.javaClass)
            return false

        val otherUser = other as User

        return id == otherUser.id
                && firstName == otherUser.firstName
                && lastName == otherUser.lastName
                && secondLastName == otherUser.secondLastName
                && email == otherUser.email
                && age == otherUser.age
                && address == otherUser.address
    }

    override fun hashCode(): Int {
        return Objects.hash(id, firstName, lastName, secondLastName, email, age, address)
    }
}
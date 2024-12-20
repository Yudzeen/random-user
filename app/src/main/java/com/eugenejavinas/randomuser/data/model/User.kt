package com.eugenejavinas.randomuser.data.model

/**
 * POJO
 */
data class User(
    val name: Name,
    val address: Address,
    val gender: String,
    val email: String,
    val landline: String,
    val mobile: String,
    val picture: Picture
) {
    data class Name(
        val first: String,
        val last: String
    )

    data class Address(
        val street: Street,
        val city: String,
        val state: String,
        val country: String
    ) {
        data class Street(
            val number: Int,
            val name: String
        )
    }

    data class Picture(
        val large: String,
        val medium: String,
        val thumbnail: String
    )
}

package com.eugenejavinas.randomuser.data.model

import java.io.Serializable

data class User(
    val name: Name,
    val address: Address,
    val gender: String,
    val email: String,
    val picture: Picture
): Serializable

data class Name(
    val first: String,
    val last: String
): Serializable

data class Address(
    val street: Street,
    val city: String,
    val state: String,
    val country: String
): Serializable

data class Street(
    val number: Int,
    val name: String
): Serializable

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
): Serializable
package com.eugenejavinas.randomuser.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FetchUsersResponse(
    @field:Json(name = "results") var results: List<UserResponse>? = null
)

@JsonClass(generateAdapter = true)
class UserResponse(
    @field:Json(name = "name") val name: Name? = null,
    @field:Json(name = "location") val location: Location? = null,
    @field:Json(name = "gender") val gender: String? = null,
    @field:Json(name = "email") val email: String? = null,
    @field:Json(name = "landline") val landline: String? = null,
    @field:Json(name = "mobile") val mobile: String? = null,
    @field:Json(name = "picture") val picture: Picture? = null,
)

@JsonClass(generateAdapter = true)
class Name(
    @field:Json(name = "first") val first: String? = null,
    @field:Json(name = "last") val last: String? = null
)

@JsonClass(generateAdapter = true)
class Location(
    @field:Json(name = "street") val street: Street? = null,
    @field:Json(name = "city") val city: String? = null,
    @field:Json(name = "state") val state: String? = null,
    @field:Json(name = "country") val country: String? = null
)

@JsonClass(generateAdapter = true)
class Street(
    @field:Json(name = "number") val number: Int? = null,
    @field:Json(name = "name") val name: String? = null
)

@JsonClass(generateAdapter = true)
data class Picture(
    @field:Json(name = "large") val large: String,
    @field:Json(name = "medium") val medium: String,
    @field:Json(name = "thumbnail") val thumbnail: String
)
package com.moviles.streaming.features.user.data.dataresources.remote.mapper

import com.moviles.streaming.features.user.data.dataresources.remote.model.UserDto
import com.moviles.streaming.features.user.domain.entities.User

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        username = this.username,
        rol = this.rol
    )
}
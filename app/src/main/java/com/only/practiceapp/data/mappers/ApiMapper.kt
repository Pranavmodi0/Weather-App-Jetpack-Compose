package com.only.practiceapp.data.mappers

interface ApiMapper<Domain, Entity>{
    fun mapToDomain(entity: Entity): Domain
}
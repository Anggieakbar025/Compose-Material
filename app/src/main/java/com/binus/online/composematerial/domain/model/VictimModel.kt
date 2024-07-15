package com.binus.online.composematerial.domain.model

class VictimModel(
    val id: Int,
    val name: String,
    val gender: Gender,
    val age: Int,
    val status: Status,
    val desc: String,
    val createdAt: String
) {
    enum class Gender(val value: String) {
        LAKI("Laki - Laki"),
        PEREMPUAN("Perempuan"),
    }
    enum class Status(val value: String) {
        REAKTIF("Reaktif"),
        DIRAWAT("Dirawat"),
        MENINGGAL("Meninggal"),
        SEMBUH("Sembuh")
    }
}
package com.example.myapplication.ui.model

import com.example.myapplication.data.model.YuGiOhEntity

sealed interface ItemUi {
    data class Header(
        val type: String,
    ) : ItemUi

    data class YuGiOhObject(
        val title: String,
        val type: String,
        val level: Int,
        val atk: Int,
        val def: Int,
        val current_timestamp: String,
        val url: String?,
    ) : ItemUi

    data class Footer(
        val numberOfElements: Int,
    ) : ItemUi
}

fun List<YuGiOhEntity>.toUi(): List<ItemUi.YuGiOhObject> {
    return map { eachEntity ->
        ItemUi.YuGiOhObject(
            title = eachEntity.name,
            type = eachEntity.type,
            level = eachEntity.level,
            atk = eachEntity.atk,
            def = eachEntity.def,
            current_timestamp = eachEntity.current_timestamp,
            url = eachEntity.cardImageUrl,
        )
    }
}

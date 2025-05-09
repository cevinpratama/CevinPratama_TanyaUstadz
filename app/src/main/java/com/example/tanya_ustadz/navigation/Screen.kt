package com.example.tanya_ustadz.navigation

import com.example.tanya_ustadz.KEY_ID_DOA

sealed class Screen (val route: String) {
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_DOA}"){
        fun withId(id: Long) = "detailScreen/$id"

    }
}
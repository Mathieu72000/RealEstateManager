package com.example.real_estate_manager

sealed class Constants {

    companion object {

        const val HOUSE_ID = "houseId"
        const val HOUSE = "house"
        const val SEARCH_RESULT_ID = "resultId"
        const val PICTURE_INTENT_FILTER = "pictureClick"
        const val PICTURE_POSITION = "position"
        const val HOUSEID_BROADCAST = "HouseClick"
        const val IS_SEARCH_CONTEXT = "search"
        const val AUTOCOMPLETE_REQUEST_CODE = 1
        const val PICTURE_REQUEST_CODE = 2
        const val RESULT_REQUEST_CODE = 3
        const val AUTHORITY = "com.example.real_estate_manager.provider"
        const val ALL_PROPERTIES = 4
        const val ALL_REAL_ESTATE_AGENTS = 5
        const val ALL_INTEREST_POINTS = 6
        const val ALL_TYPE = 7
        const val ALL_PICTURES = 8
    }
}
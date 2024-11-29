package com.example.lorempicsum.data.datasource

const val UNSPLASH_BASE_URL: String = "https://picsum.photos"
const val PICTURE_LIST_ENDPOINT: String = "/v2/list"
const val ID_PATH_PARAM: String = "id"
const val PICTURE_DETAILS_ENDPOINT: String = "/id/{$ID_PATH_PARAM}/info"
const val PAGE_QUERY_PARAM: String = "page"
const val LIMIT_QUERY_PARAM: String = "limit"
const val DEFAULT_PAGE_SIZE: Int = 20
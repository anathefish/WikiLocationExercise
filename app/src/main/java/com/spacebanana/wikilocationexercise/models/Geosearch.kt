package com.spacebanana.wikilocationexercise.models


object Geosearch {
    data class Result(val query: Query)
    data class Query(val geosearch: List<Article>)

    data class Article(
            val pageid: Long?,
            val ns: Int?,
            val title: String?,
            val lat: Double?,
            val lon: Double?,
            val dist: Double?,
            val primary: String?
    )
}

package com.spacebanana.wikilocationexercise.models

/**
 * Created by spacebanana on 9/19/17.
 */
object Pages {
    data class Result(val query: Query)
    data class Query(val pages: List<Page>)

    class Page(
            val pageid: Long?,
            val ns: Int?,
            val title: String?,
            val images: List<Image>
    )

    class Image(
            val ns: Int?,
            val title: String?
    )
}
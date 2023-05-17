package com.example.storyapp.view.data

import com.example.storyapp.view.network.DetailResponse
import com.example.storyapp.view.network.DetailStoriesResponse

object DataDummy {

    fun generateDummyDetail(): List<DetailResponse> {
        val detail = ArrayList<DetailResponse>()
        for (i in 0..100) {
            val detailData = DetailResponse(
                i.toString(),
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                "Dimas",
                "Lorem Ipsum",
                "-16.002",
                "-10.212",
                )
            detail.add(detailData)
        }
        return detail
    }
}
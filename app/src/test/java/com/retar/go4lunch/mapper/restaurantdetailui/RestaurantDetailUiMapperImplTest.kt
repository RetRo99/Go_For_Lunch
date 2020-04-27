package com.retar.go4lunch.mapper.restaurantdetailui

import com.retar.go4lunch.api.response.restaurantdetails.*
import com.retar.go4lunch.ui.resturants.adapter.RestaurantAdapter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class RestaurantDetailUiMapperImplTest {

    @Test
    fun mapDetailsPickedToUi() {
        val geometry = Geometry(Location(46.09009849999999, 14.8114336))

        val openingHours = Opening_hours(
            false, listOf(
                "Monday: Closed",
                "Tuesday: Closed",
                "Wednesday: 10:00 am – 5:00 pm",
                "Thursday: 10:00 am – 5:00 pm",
                "Friday: 10:00 am – 5:00 pm",
                "Saturday: 10:00 am – 5:00 pm",
                "Sunday: 10:00 am – 4:00 pm"
            )
        )


        val photos = listOf(
            Photos("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"),
            Photos("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ"),
            Photos("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw")

        )

        val result = Result(
            "Zgornji Hotič 15, 1270 Litija, Slovenia",
            "(01) 898 90 28",
            geometry,
            "Gostišče Prenočišča Kimovec",
            openingHours,
            photos,
            "ChIJkzFFg-I9ZUcRT_IOiIDkvVQ",
            "Zgornji Hotič 15",
            "http://kimovec.eu/"
        )


        val response = RestaurantDetailResponse(result)

        val mapper = RestaurantDetailUiMapperImpl()

        val mapperResult = mapper.mapToUi(response, true)


        assertThat(mapperResult.phoneNumber, `is`("(01) 898 90 28"))


        assertThat(
            mapperResult.photoReferences?.get(0),
            `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA")
        )
        assertThat(
            mapperResult.photoReferences?.get(1),
            `is`("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ")
        )
        assertThat(
            mapperResult.photoReferences?.get(2),
            `is`("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw")
        )

        assertThat(mapperResult.webPage, `is`("http://kimovec.eu/"))

        assertThat(mapperResult.isPicked, `is`(true))
        assertThat(mapperResult.name, `is`("Gostišče Prenočišča Kimovec"))

    }

    @Test
    fun mapDetailsNotPickedToUi() {
        val geometry = Geometry(Location(46.09009849999999, 14.8114336))

        val openingHours = Opening_hours(
            false, listOf(
                "Monday: Closed",
                "Tuesday: Closed",
                "Wednesday: 10:00 am – 5:00 pm",
                "Thursday: 10:00 am – 5:00 pm",
                "Friday: 10:00 am – 5:00 pm",
                "Saturday: 10:00 am – 5:00 pm",
                "Sunday: 10:00 am – 4:00 pm"
            )
        )


        val photos = listOf(
            Photos("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"),
            Photos("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ"),
            Photos("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw")

        )

        val result = Result(
            "Zgornji Hotič 15, 1270 Litija, Slovenia",
            "(01) 898 90 28",
            geometry,
            "Gostišče Prenočišča Kimovec",
            openingHours,
            photos,
            "ChIJkzFFg-I9ZUcRT_IOiIDkvVQ",
            "Zgornji Hotič 15",
            "http://kimovec.eu/"
        )


        val response = RestaurantDetailResponse(result)

        val mapper = RestaurantDetailUiMapperImpl()

        val mapperResult = mapper.mapToUi(response, false)


        assertThat(mapperResult.phoneNumber, `is`("(01) 898 90 28"))


        assertThat(
            mapperResult.photoReferences?.get(0),
            `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA")
        )
        assertThat(
            mapperResult.photoReferences?.get(1),
            `is`("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ")
        )
        assertThat(
            mapperResult.photoReferences?.get(2),
            `is`("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw")
        )

        assertThat(mapperResult.webPage, `is`("http://kimovec.eu/"))

        assertThat(mapperResult.isPicked, `is`(false))
        assertThat(mapperResult.name, `is`("Gostišče Prenočišča Kimovec"))

    }
}

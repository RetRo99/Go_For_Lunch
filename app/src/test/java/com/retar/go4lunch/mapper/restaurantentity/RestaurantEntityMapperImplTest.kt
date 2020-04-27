package com.retar.go4lunch.mapper.restaurantentity

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.response.restaurantdetails.*
import com.retar.go4lunch.ui.resturants.adapter.RestaurantAdapter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.mockito.Mockito


class RestaurantEntityMapperTest {

    @Test
    fun mapOneResultToOneEntity() {

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

        val mapper = RestaurantEntityMapperImpl()

        val mapperResult = mapper.mapToEntity(listOf(response), LatLng(46.090996, 14.8137409 ))

        assertThat(mapperResult.size, `is`(1))

    }

    @Test
    fun mapZeroResultToZeroEntity() {
        val mapper = RestaurantEntityMapperImpl()

        val mapperResult = mapper.mapToEntity(listOf(), LatLng(46.090996, 14.8137409 ))

        assertThat(mapperResult.size, `is`(0))
    }

    @Test
    fun mapToClosedToCorrectEntity() {

        val geometry = Geometry(Location(46.09009849999999, 14.8114336))

        // is currenly closed
        val openingHours = Opening_hours(
            false,
            listOf(
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


        val moodKeeperMock = Mockito.spy(RestaurantEntityMapperImpl())

        Mockito.doReturn("Sunday").`when`(moodKeeperMock).getCurrentDay()
        Mockito.doReturn(1801).`when`(moodKeeperMock).getOpenedUntilInSeconds("")

        val mapperResult = moodKeeperMock.mapToEntity(listOf(response), LatLng(46.090996, 14.8137409 ))



        val entity = mapperResult[0]

        assertThat(entity.latLng.longitude, `is`(geometry.location.lng))
        assertThat(entity.latLng.latitude, `is`(geometry.location.lat))

        assertThat(entity.name, `is`("Gostišče Prenočišča Kimovec"))

        assertThat(entity.id, `is`("ChIJkzFFg-I9ZUcRT_IOiIDkvVQ"))

        assertThat(entity.phoneNumber, `is`("(01) 898 90 28"))


         assertThat(entity.photoReferences?.get(0), `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))
         assertThat(entity.photoReferences?.get(1), `is`("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ"))
         assertThat(entity.photoReferences?.get(2), `is`("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw"))

        assertThat(entity.webPage, `is`("http://kimovec.eu/"))

        assertThat(entity.address(), `is`("Zgornji Hotič 15"))
        assertThat(entity.photoUrl, `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))

        assertThat(entity.openedText.first, `is`(RestaurantAdapter.TYPE_CLOSED))
        assertThat(entity.openedText.second, `is`(""))


        assertThat(entity.timesPicked, `is`(0))
        assertThat(entity.rating, `is`(0f))

    }

    @Test
    fun mapOpenUntilToCorrectEntity() {

        val geometry = Geometry(Location(46.09009849999999, 14.8114336))

        // is opened until 4:00
        val openingHours = Opening_hours(
            true,
            listOf(
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


        val mapper = Mockito.spy(RestaurantEntityMapperImpl())

        Mockito.doReturn("Sunday").`when`(mapper).getCurrentDay()
        Mockito.doReturn(1801).`when`(mapper).getOpenedUntilInSeconds("")

        val mapperResult = mapper.mapToEntity(listOf(response), LatLng(46.090996, 14.8137409 ))



        val entity = mapperResult[0]

        assertThat(entity.latLng.longitude, `is`(geometry.location.lng))
        assertThat(entity.latLng.latitude, `is`(geometry.location.lat))

        assertThat(entity.name, `is`("Gostišče Prenočišča Kimovec"))

        assertThat(entity.id, `is`("ChIJkzFFg-I9ZUcRT_IOiIDkvVQ"))

        assertThat(entity.phoneNumber, `is`("(01) 898 90 28"))


        assertThat(entity.photoReferences?.get(0), `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))
        assertThat(entity.photoReferences?.get(1), `is`("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ"))
        assertThat(entity.photoReferences?.get(2), `is`("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw"))

        assertThat(entity.webPage, `is`("http://kimovec.eu/"))

        assertThat(entity.address(), `is`("Zgornji Hotič 15"))
        assertThat(entity.photoUrl, `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))

        assertThat(entity.openedText.first, `is`(RestaurantAdapter.TYPE_OPEN_UNTIL))
        assertThat(entity.openedText.second, `is`("4:00 pm"))


        assertThat(entity.timesPicked, `is`(0))
        assertThat(entity.rating, `is`(0f))

    }

    @Test
    fun mapOpen247ToCorrectEntity() {

        val geometry = Geometry(Location(46.09009849999999, 14.8114336))

        val openingHours = Opening_hours(
            true,
            listOf(
                "Monday: Closed",
                "Tuesday: Closed",
                "Wednesday: 10:00 am – 5:00 pm",
                "Thursday: 10:00 am – 5:00 pm",
                "Friday: 10:00 am – 5:00 pm",
                "Saturday: 10:00 am – 5:00 pm",
                "Sunday: Open 24 hours"
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


        val mapper = Mockito.spy(RestaurantEntityMapperImpl())

        Mockito.doReturn("Sunday").`when`(mapper).getCurrentDay()
        Mockito.doReturn(1801).`when`(mapper).getOpenedUntilInSeconds("")

        val mapperResult = mapper.mapToEntity(listOf(response), LatLng(46.090996, 14.8137409 ))


        Log.d("čič", mapper.getOpenedUntilInSeconds("").toString())

        val entity = mapperResult[0]

        assertThat(entity.latLng.longitude, `is`(geometry.location.lng))
        assertThat(entity.latLng.latitude, `is`(geometry.location.lat))

        assertThat(entity.name, `is`("Gostišče Prenočišča Kimovec"))

        assertThat(entity.id, `is`("ChIJkzFFg-I9ZUcRT_IOiIDkvVQ"))

        assertThat(entity.phoneNumber, `is`("(01) 898 90 28"))


        assertThat(entity.photoReferences?.get(0), `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))
        assertThat(entity.photoReferences?.get(1), `is`("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ"))
        assertThat(entity.photoReferences?.get(2), `is`("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw"))

        assertThat(entity.webPage, `is`("http://kimovec.eu/"))

        assertThat(entity.address(), `is`("Zgornji Hotič 15"))
        assertThat(entity.photoUrl, `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))

        assertThat(entity.openedText.first, `is`(RestaurantAdapter.TYPE_OPEN_24))
        assertThat(entity.openedText.second, `is`(""))


        assertThat(entity.timesPicked, `is`(0))
        assertThat(entity.rating, `is`(0f))

    }

    @Test
    fun mapOpenClosingSoonToCorrectEntity() {

        val geometry = Geometry(Location(46.09009849999999, 14.8114336))

        val openingHours = Opening_hours(
            true,
            listOf(
                "Monday: Closed",
                "Tuesday: Closed",
                "Wednesday: 10:00 am – 5:00 pm",
                "Thursday: 10:00 am – 5:00 pm",
                "Friday: 10:00 am – 5:00 pm",
                "Saturday: 10:00 am – 5:00 pm",
                "Sunday: 10:00 am – 5:00 pm"
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


        val mapper = Mockito.spy(RestaurantEntityMapperImpl())

        Mockito.doReturn("Sunday").`when`(mapper).getCurrentDay()

        //it is closing soon
        Mockito.doReturn(5).`when`(mapper).getOpenedUntilInSeconds( "5:00 pm")

        val mapperResult = mapper.mapToEntity(listOf(response), LatLng(46.090996, 14.8137409 ))



        val entity = mapperResult[0]

        assertThat(entity.latLng.longitude, `is`(geometry.location.lng))
        assertThat(entity.latLng.latitude, `is`(geometry.location.lat))

        assertThat(entity.name, `is`("Gostišče Prenočišča Kimovec"))

        assertThat(entity.id, `is`("ChIJkzFFg-I9ZUcRT_IOiIDkvVQ"))

        assertThat(entity.phoneNumber, `is`("(01) 898 90 28"))


        assertThat(entity.photoReferences?.get(0), `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))
        assertThat(entity.photoReferences?.get(1), `is`("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ"))
        assertThat(entity.photoReferences?.get(2), `is`("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw"))

        assertThat(entity.webPage, `is`("http://kimovec.eu/"))

        assertThat(entity.address(), `is`("Zgornji Hotič 15"))
        assertThat(entity.photoUrl, `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))

        assertThat(entity.openedText.first, `is`(RestaurantAdapter.TYPE_CLOSING_SOON))
        assertThat(entity.openedText.second, `is`(""))


        assertThat(entity.timesPicked, `is`(0))
        assertThat(entity.rating, `is`(0f))

    }

    @Test
    fun mapNullOpeningTimeToCorrectEntity() {

        val geometry = Geometry(Location(46.09009849999999, 14.8114336))

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
            null,
            photos,
            "ChIJkzFFg-I9ZUcRT_IOiIDkvVQ",
            "Zgornji Hotič 15",
            "http://kimovec.eu/"
        )


        val response = RestaurantDetailResponse(result)


        val mapper = Mockito.spy(RestaurantEntityMapperImpl())

        Mockito.doReturn("Sunday").`when`(mapper).getCurrentDay()

        //it is closing soon
        Mockito.doReturn(5).`when`(mapper).getOpenedUntilInSeconds( "5:00 pm")

        val mapperResult = mapper.mapToEntity(listOf(response), LatLng(46.090996, 14.8137409 ))



        val entity = mapperResult[0]

        assertThat(entity.latLng.longitude, `is`(geometry.location.lng))
        assertThat(entity.latLng.latitude, `is`(geometry.location.lat))

        assertThat(entity.name, `is`("Gostišče Prenočišča Kimovec"))

        assertThat(entity.id, `is`("ChIJkzFFg-I9ZUcRT_IOiIDkvVQ"))

        assertThat(entity.phoneNumber, `is`("(01) 898 90 28"))


        assertThat(entity.photoReferences?.get(0), `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))
        assertThat(entity.photoReferences?.get(1), `is`("CmRaAAAAErV6nsHBZg5xzFv0QzF_MEwbHEAztGNG06A3BC2S4oMPOOd5bUbMcYZTSOSn5ppZlKiFh87eYwbPwE6SuhGx7Os6bfcMvNZDTfttmybp0kRS8pV2VdR9fLjNCjAYkaq1EhC9IcEfp4d2ZcQmj9wwTctkGhRZvFomWdHoFoCad-mFjr75lVtwbQ"))
        assertThat(entity.photoReferences?.get(2), `is`("CmRaAAAAzqv4Pp1bYf4_9IiXX4hzTZeXV_1tJLpqzzJPm2wgSM9Etlhs6XOwZIoywNK2kERTIUIMQ3qBm56bnJ3SLc0wAMPViFTlqBQr78u7AQsN6sWo-Rm-LrMbwuH1jPceu7BuEhCtyKsCiUdkPbw9hsck2RdEGhRDbPWahBbBB8_M1JFDSyk37cuRIw"))

        assertThat(entity.webPage, `is`("http://kimovec.eu/"))

        assertThat(entity.address(), `is`("Zgornji Hotič 15"))
        assertThat(entity.photoUrl, `is`("CmRaAAAAHI9pOlOt59QBrCLC2F-5HTxhaprVNsm0QLxBw6bpbcBMzxVOwlqs8_VcGcnDL2sqtesvDGitUA4G1PHCRnqJqgVmCu6L2aKt7m4ug50Tkw8kALyAE57Gv1pSqURarTgVEhDjo0oLV8Qoizrr0AbquB_oGhS7Ciwd0oHQh-y70lX144rbHHfvEA"))

        assertThat(entity.openedText.first, `is`(RestaurantAdapter.TYPE_NO_INFORMATION))
        assertThat(entity.openedText.second, `is`(""))


        assertThat(entity.timesPicked, `is`(0))
        assertThat(entity.rating, `is`(0f))

    }

}

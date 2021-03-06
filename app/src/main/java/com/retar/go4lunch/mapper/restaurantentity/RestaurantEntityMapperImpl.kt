package com.retar.go4lunch.mapper.restaurantentity

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.api.response.restaurantdetails.Result
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.ui.resturants.adapter.RestaurantAdapter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

class RestaurantEntityMapperImpl : RestaurantEntityMapper {

    private lateinit var result: Result

    override fun mapToEntity(
        restaurantDetails: List<RestaurantDetailResponse>,
        currentLatLatLng: LatLng
    ): List<RestaurantEntity> {

        val entityList = restaurantDetails.map {
            this.result = it.result

            RestaurantEntity(
                getRestaurantLatLang(),
                result.name,
                result.place_id,
                result.formatted_phone_number,
                result.photos?.map { it.photo_reference },
                result.website,
                getDistanceToCurrentLocation(currentLatLatLng),
                result.vicinity,
                result.photos?.get(0)?.photo_reference,
                getOpenedString()
            )
        }

        return entityList.sortedBy {
            it.distance.substringBefore(" ").toFloat()
        }
    }


    override fun getRestaurantLatLang(): LatLng {
        return result.geometry.location.getLatLng()
    }

    override fun getDistanceToCurrentLocation(currentLatLatLng: LatLng): String {
        val locationStart = Location("").apply {
            latitude = getRestaurantLatLang().latitude
            longitude = getRestaurantLatLang().longitude
        }

        val locationEnd = Location("").apply {
            latitude = currentLatLatLng.latitude
            longitude = currentLatLatLng.longitude
        }
        return locationStart.distanceTo(locationEnd).toInt().toString().plus(" m")
    }


    override fun getOpenedString(): Pair<Int, String> {
        val isOpened = getIsOpened()

        return if (!isOpened) Pair(RestaurantAdapter.TYPE_CLOSED, "") else getOpenedText()
    }

    override fun getIsOpened(): Boolean {
        return result.opening_hours?.open_now ?: true
    }

    override fun getOpenedText(): Pair<Int, String> {
        val currentDay = getCurrentDay()

        val openedText = result.opening_hours?.weekday_text?.let {
            it.find { openedStrings ->
                openedStrings.contains(currentDay, true)
            }
        } ?: return Pair(RestaurantAdapter.TYPE_NO_INFORMATION, "")

        if ("Open 24 hours" in openedText) return Pair(RestaurantAdapter.TYPE_OPEN_24, "")

        val formattedOpenedString = openedText.substringAfter("– ")
        val currentTimeInSeconds = LocalDateTime.now().toLocalTime().toSecondOfDay()


        val openUntilInSeconds = getOpenedUntilInSeconds(formattedOpenedString)
            ?: return Pair(RestaurantAdapter.TYPE_NO_INFORMATION, "")


        val remainingTimeOpened = openUntilInSeconds - currentTimeInSeconds

        return if (remainingTimeOpened < THIRTY_MINUTES_IN_SECONDS) {
            Pair(RestaurantAdapter.TYPE_CLOSING_SOON, "")
        } else {
            Pair(RestaurantAdapter.TYPE_OPEN_UNTIL, formattedOpenedString)
        }

    }

    override fun getCurrentDay(): String {
        return LocalDateTime.now().dayOfWeek.toString()
    }

    override fun getOpenedUntilInSeconds(formattedOpenedString: String): Int? {
        var openedInSeconds: Int? = try {
            (LocalTime.parse(
                formattedOpenedString, DateTimeFormatter.ofPattern("hh:mm a")
            ).toSecondOfDay()
                    )

        } catch (e: Exception) {
            ERROR_TIME_REMAINING
        }

        if (openedInSeconds == ERROR_TIME_REMAINING) {
            openedInSeconds =
                try {
                    (LocalTime.parse(
                        formattedOpenedString, DateTimeFormatter.ofPattern("h:mm a")
                    ).toSecondOfDay()
                            )

                } catch (e: Exception) {
                    ERROR_TIME_REMAINING
                }
        }

        if (openedInSeconds == ERROR_TIME_REMAINING) {
            openedInSeconds =
                try {
                    (LocalTime.parse(
                        formattedOpenedString.toUpperCase(), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

                    ).toSecondOfDay()
                            )

                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
        }

        return openedInSeconds


    }

    companion object {
        private const val THIRTY_MINUTES_IN_SECONDS = 1800
        private const val ERROR_TIME_REMAINING = -69
    }

}

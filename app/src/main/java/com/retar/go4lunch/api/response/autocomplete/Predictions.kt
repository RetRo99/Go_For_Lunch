package com.retar.go4lunch.api.response.autocomplete


data class Predictions (
	val place_id : String,
	val types : List<String>
)
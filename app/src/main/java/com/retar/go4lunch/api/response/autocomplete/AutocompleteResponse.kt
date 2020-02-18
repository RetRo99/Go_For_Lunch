package com.retar.go4lunch.api.response.autocomplete

data class AutocompleteResponse (

	val predictions : List<Predictions>,
	val status : String
)
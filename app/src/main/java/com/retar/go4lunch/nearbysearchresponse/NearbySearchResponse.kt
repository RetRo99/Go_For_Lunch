package com.retar.go4lunch.nearbysearchresponse

data class NearbySearchResponse (

	val html_attributions : List<String>,
	val results : List<Results>,
	val status : String
)
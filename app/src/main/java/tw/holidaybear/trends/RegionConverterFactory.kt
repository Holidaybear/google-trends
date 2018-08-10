package tw.holidaybear.trends

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory

class RegionConverterFactory {

    class RegionAdapter {

        @FromJson
        fun fromJson(map: Map<String, List<String>>): List<Region> {
            val regionList = mutableListOf<Region>()
            map.forEach { (key, value) ->
                regionList.add(Region(getRegionName(key), value))
            }
            regionList.sortWith(compareBy { it.name })
            return regionList
        }

        private fun getRegionName(key: String): String {
            return when(key) {
                "30" -> "Argentina"
                "8" -> "Australia"
                "44" -> "Austria"
                "41" -> "Belgium"
                "18" -> "Brazil"
                "13" -> "Canada"
                "38" -> "Chile"
                "32" -> "Colombia"
                "43" -> "Czech Republic"
                "49" -> "Denmark"
                "29" -> "Egypt"
                "50" -> "Finland"
                "16" -> "France"
                "15" -> "Germany"
                "48" -> "Greece"
                "10" -> "Hong Kong"
                "45" -> "Hungary"
                "3" -> "India"
                "19" -> "Indonesia"
                "54" -> "Ireland"
                "6" -> "Israel"
                "27" -> "Italy"
                "4" -> "Japan"
                "37" -> "Kenya"
                "34" -> "Malaysia"
                "21" -> "Mexico"
                "17" -> "Netherlands"
                "52" -> "Nigeria"
                "51" -> "Norway"
                "25" -> "Philippines"
                "31" -> "Poland"
                "47" -> "Portugal"
                "39" -> "Romania"
                "14" -> "Russia"
                "36" -> "Saudi Arabia"
                "5" -> "Singapore"
                "40" -> "South Africa"
                "23" -> "South Korea"
                "26" -> "Spain"
                "42" -> "Sweden"
                "46" -> "Switzerland"
                "12" -> "Taiwan"
                "33" -> "Thailand"
                "24" -> "Turkey"
                "35" -> "Ukraine"
                "9" -> "United Kingdom"
                "1" -> "United States"
                "28" -> "Vietnam"
                else -> throw JsonDataException("Unknown region code: $key")
            }
        }
    }

    companion object {
        fun create(): MoshiConverterFactory {
            val moshi = Moshi.Builder().add(RegionAdapter()).build()
            return MoshiConverterFactory.create(moshi)
        }
    }
}
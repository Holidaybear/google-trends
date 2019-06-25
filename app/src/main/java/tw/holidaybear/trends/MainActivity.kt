package tw.holidaybear.trends

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import tw.holidaybear.trends.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        load()
    }

    private fun load() {
        applicationContext.assets.open("trends.json").use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val trendType = object : TypeToken<Map<String, List<String>>>() {}.type
                val trendMap: Map<String, List<String>> = Gson().fromJson(jsonReader, trendType)
                val regionList = mutableListOf<Region>()
                trendMap.forEach { (key, value) ->
                    regionList.add(Region(getRegionName(key), value))
                }
                regionList.sortWith(compareBy { it.name })
                binding.trendView.playTrendList(regionList[40].keywords)
            }
        }
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
            else -> throw Exception("Unknown region code: $key")
        }
    }
}

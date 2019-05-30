package cyril.cieslak.mymynews.Parsers

import android.content.Context
import android.widget.Toast
import cyril.cieslak.mymynews.Fragments.FragmentTopStories
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream

class parseDatasResultSearchActivity () {


    val YEAR_MONTH_DAY = 10
    val YEAR_MONTH = 7
    val FIRST_FOUR = 4
    val LAST_TWO = 2
    val FIRST_ELEMENT_OF_THE_INDEX = 0
    val PRE_IMAGE_URL = "https://static01.nyt.com/"
    val DUMB_PICTURE_WHEN_NO_PIC_TO_DOWNLOAD = "https://i5.photobucket.com/albums/y152/courtney210/wave-bashful_zps5ab77563.jpg"


    var datas = mutableListOf(
        mutableListOf<String>(
            "un",
            "deux",
            "trois",
            "quatre",
            "cinq",
            "six",
            "sept",
            "huit",
            "neuf",
            "dix",
            "onze",
            "douze",
            "treize",
            "quatorze",
            "quinze"
        )
    )

    lateinit var subsection : String


    fun parseDatasFromApi(jsonDataPreview: String) : MutableList<MutableList<String>> {

//        val inputStream: InputStream = this.assets.open("dataFake.json")
//        var json = inputStream.bufferedReader().use { it.readText() }

        var json = jsonDataPreview


        try {

            var jo: JSONObject
            var job : JSONObject
            datas.clear()


            jo = JSONObject(json)

            job = jo.getJSONObject("response")

            val ja = job.getJSONArray("docs")


            for (i in 0 until ja.length()) {
                jo = ja.getJSONObject(i)


                val title = jo.getString("snippet")
                val section = jo.getString("section_name")
                try { // Try Catch because often there is no subsection in the Search JSON Results from NYTimes
                     subsection = jo.getString("subsection_name")
                } catch (e: JSONException ){ subsection = "" }
                val updated_date = jo.getString("pub_date")
                val urlArticle = jo.getString("web_url")


                //***--- PREPARATION OF THE SUBSECTION TO PRINT with a " > " before the texte to print---***//
                var subsectionReadyToPrint: String
                when (subsection) {
                    "" -> subsectionReadyToPrint = subsection
                    else -> subsectionReadyToPrint = " > $subsection"
                }
                //***--------------------------------***//

                //***--- FORMATTING THE DATE ---***//
                var date10char = updated_date.take(YEAR_MONTH_DAY)
                var date7char = updated_date.take(YEAR_MONTH)
                var dateYear = date10char.take(FIRST_FOUR)
                var dateMonth = date7char.takeLast(LAST_TWO)
                var dateDay = date10char.takeLast(LAST_TWO)
                var dateToPrint = "$dateDay/$dateMonth/$dateYear"
                //***--------------------------------***//


                val jam = jo.getJSONArray("multimedia")
                //***--- GET AN IMAGE EVEN WHEN MULTIMEDIA IS EMPTY  ---**//
                var urlToPrint: String
                if (jam.length() == 0) { urlToPrint = "$DUMB_PICTURE_WHEN_NO_PIC_TO_DOWNLOAD"
                } else {

                    var jom = jam[FIRST_ELEMENT_OF_THE_INDEX] as JSONObject
                    var url = jom.getString("url")

                    //***--- GET AN IMAGE EVEN WHEN MULTIMEDIA IS EMPTY  ---**//

                    when (url) {
                        "" -> urlToPrint = "$DUMB_PICTURE_WHEN_NO_PIC_TO_DOWNLOAD"
                        else -> urlToPrint = "$PRE_IMAGE_URL$url"
                    }

                }
                val data =
                    mutableListOf<String>(section, subsectionReadyToPrint, title, dateToPrint, urlToPrint, urlArticle)



                datas.add(data)


            }

            return datas

        } catch (e: JSONException) {
            e.printStackTrace()

        }
        return datas
    }
}
@file:Suppress("DEPRECATION")

package com.example.top10downloaderapp

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

import java.net.URL
import kotlin.properties.Delegates
import kotlin.properties.Delegates.notNull

class FeedEntry {
    var name :String=""
    var artist:String=""
    var releaseDate:String=""
    var summary: String=""
    var imageUrl:String=""
    override fun toString(): String {
        return """""
            name= $name
            artist= $artist
            releaseDate= $releaseDate
            imageUrl= $imageUrl
            """.trimIndent()
    }
}

class MainActivity : AppCompatActivity() {
    private val TAG1="MainActivity"
//    private val downloadData by lazy { DownloadData(this, xmlListView)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG1, "onCreateCalled")
        val downloadData = DownloadData(this, xmlListView)
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(TAG1, "OnCreate: Done")
    }

//    override fun onDestroy() {
//        super.onDestroy()
//            downloadData.cancel(true)
//    }

    companion object {
        private class DownloadData(context : Context, listView: ListView) : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"
            var propContext: Context by notNull()
            var propListView : ListView by notNull()

            init {
                propContext = context
                propListView = listView
            }
            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
//                Log.d(TAG, "onPostExecute: Parametre is $result")
                val parseApplication =ParseApplication()
                parseApplication.parse(result)

                val arrayAdapter = ArrayAdapter<FeedEntry>(propContext, R.layout.list_items, parseApplication.applications)
                propListView.adapter= arrayAdapter
            }


            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "DoInBackground: Starts with ${url[0]}")
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: Error Downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?):String{
//                val xmlResult= StringBuilder()
//                try {
//                    val url = URL(urlPath)
//
//                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//                    val response = connection.responseCode
//                    Log.d(TAG, "downloandXML : The response code was $response")
////                   val reader = BufferedReader(InputStreamReader(connection.inputStream))
////            *************************Read The Data **** Creating Character Array*****************************************
////                    val inputBuffer = CharArray(500)
////                    var charRead = 0
////                    while (charRead >= 0) {
////                        charRead = reader.read(inputBuffer)
////                        if (charRead > 0) {
////                            xmlResult.append(String(inputBuffer, 0, charRead))
////                        }
////                    }
//////            ************Closing Buffer Reader*************************************************************************************************
////                    reader.close()
//                    connection.inputStream.buffered().reader().use { xmlResult.append(it.readText()) }
//                    Log.d(TAG, "Recived :${xmlResult.length} Bytes")
//                    return xmlResult.toString()
//                }catch (e:Exception){
//                    val errorMessage: String = when (e){
//                        is MalformedURLException -> "downloadXML : Invalid URL ${e.message}"
//                        is IOException -> "downloadXML :IO Exception reading data : ${e.message}"
//                        is SecurityException-> "downloadXML:Security Exception: Needs Permission${e.message}"
//                        else-> " Unknown error : ${e.message}"
//                    }
//                }
////                }catch (e:MalformedURLException){
////                    Log.e(TAG, "downloadXML : Invalid URL ${e.message}")
////                }catch (e:IOException){
////                    Log.e(TAG, "downloadXML :IO Exception reading data : ${e.message}")
////                }catch (e:SecurityException){
////                    Log.e(TAG, "downloadXML:Security Exception: Needs Permission${e.message}")
////                } catch (e:Exception){
////                    Log.e(TAG, " Unknown error : ${e.message}")
////                }
//                return ""   // If Its Gets Here, There's Been A Problem. Return An Empty String
////************************************Above Commented code in one line***************************************
                return URL(urlPath).readText()
            }
        }
    }

}
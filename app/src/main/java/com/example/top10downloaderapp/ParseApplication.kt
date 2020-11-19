package com.example.top10downloaderapp

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

import kotlin.collections.ArrayList

class ParseApplication {
    private val TAG = "ParseApplications"
    val applications= ArrayList<FeedEntry>()
     fun parse (xmlData:String):Boolean{
         Log.d(TAG, "parse called with $xmlData")
         var status= true
         var inEntry = true
         var textValue= ""

         try {
             val factory= XmlPullParserFactory.newInstance()
             factory.isNamespaceAware=true
             val xpp= factory.newPullParser()
             xpp.setInput(xmlData.reader())
             var eventType= xpp.eventType
             var currentRecord =FeedEntry()
             while (eventType !=XmlPullParser.END_DOCUMENT) {
                 val TagName= xpp.name     //TODO: We Should Use The Safe Call Operator?
                 when (eventType){
                     XmlPullParser.START_TAG->{
                         Log.d(TAG, "parse : Starting Tag For $TagName")
                         if (TagName== "entry") inEntry=true
                     }

                     XmlPullParser.TEXT -> textValue=xpp.text
                     XmlPullParser.END_TAG-> {
                         Log.d(TAG, "Parse: Ending Tag For $TagName")
                         if (inEntry){
                             when(TagName){
                                 "entry"->{
                                     applications.add(currentRecord)
                                     inEntry=false
                                     currentRecord= FeedEntry() //Create New Entry

                                 }

                                 "name" -> currentRecord.name= textValue
                                 "artist" -> currentRecord.artist=textValue
                                 "releasedate" -> currentRecord.releaseDate= textValue
                                 "summary" -> currentRecord.summary= textValue
                                 "image"-> currentRecord.imageUrl= textValue

                             }
                         }
                     }

                 }
                 // Nothing Else To Do.
                 eventType= xpp.next()
             }
             for (app in applications){
                 Log.d(TAG, "****************************")
                 Log.d(TAG, app.toString())
             }


         }catch (e:Exception){
             e.printStackTrace()
             status=false

         }
         return status
     }
}
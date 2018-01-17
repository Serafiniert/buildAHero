package de.build_a_hero.app

import android.os.AsyncTask

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpGetRequest : AsyncTask<String, Void, String>() {


    override fun doInBackground(vararg params: String): String? {
        val url = params[0]
        var result: String?
        var inputLine: String


        try {
            val myUrl = URL(url)

            val connection = myUrl.openConnection() as HttpURLConnection

            connection.requestMethod = REQUEST_METHOD
            connection.readTimeout = READ_TIMEOUT
            connection.connectTimeout = CONNECTION_TIMEOUT

            connection.connect()

            val streamReader = InputStreamReader(connection.inputStream)

            val reader = BufferedReader(streamReader)
            val stringBuilder = StringBuilder()


            while (true) {
                inputLine = reader.readLine() ?: break

                stringBuilder.append(inputLine + "\n")

            }

            reader.close()
            streamReader.close()

            result = stringBuilder.toString()

        } catch (e: IOException) {
            e.printStackTrace()
            result = null
        }

        return result
    }

    companion object {

        val REQUEST_METHOD = "GET"
        val READ_TIMEOUT = 15000
        val CONNECTION_TIMEOUT = 15000
    }

}


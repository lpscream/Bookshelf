package net.test.bookshelf.viewmodel

import android.util.Log
import net.test.bookshelf.models.Book
import net.test.bookshelf.models.Category
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class DataLoader {
        val BASE_URL: String = "https://www.ebooksbilliger.de/api/request.php?cmd=getArticles&category=10&start=0&limit=20"
        val TAG = "DataLoader_log"

    private val client = OkHttpClient()


    fun loadString(host: String): String {
        var result : String
        val request = Request.Builder()
            .url(host)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$TAG $name: $value")
            }
            result = response.body?.string().toString()

        }
        return result
    }
    fun getBookList(host: String): ArrayList<Book>{
        var arrayList: ArrayList<Book> = ArrayList()
        var jsonRoot: JSONObject
        var book: Book
        try {
            jsonRoot = JSONObject(loadString(host))
            var jsonArray: JSONArray = jsonRoot.getJSONArray("data")
            var jsonObjectArrayList: ArrayList<JSONObject> = ArrayList()
            Log.d(TAG, "getBookList: length of array " + jsonArray.length())
            for (i in 0 until jsonArray.length()){
                jsonObjectArrayList.add(JSONObject(jsonArray.getString(i)))
            }
            for (k in jsonObjectArrayList){
                book = Book(k.getString("id"))
                book.ASIN = k.getString("ASIN")
                book.title = k.getString("title")
                book.image_url = k.getString("image_url")
                book.image_url_hd = k.getString("image_url_hd")
                book.details_url = k.getString("details_url")
                book.author = k.getString("author")
                book.old_price = k.getString("old_price")
                book.new_price = k.getString("new_price")
                book.old_price_time = k.getString("old_price_time")
                book.new_price_time = k.getString("new_price_time")
                book.discount = k.getString("discount")
                book.info_text = k.getString("info_text")
                book.expired = k.getString("expired")
                arrayList.add(book)
            }
            for (l in arrayList){
                Log.d(TAG, "parseString: " + l.toString())
            }
        }catch (e: JSONException){
            println(e.toString())
        }
        return arrayList
    }


    fun getCategories(host: String):ArrayList<Category>{
        var arrayList: ArrayList<Category> = ArrayList()
        var jsonRoot: JSONObject
        var category: Category
        try {
            jsonRoot = JSONObject(loadString(host))
            var jsonArray: JSONArray = jsonRoot.getJSONArray("data")
            var jsonObjectArrayList: ArrayList<JSONObject> = ArrayList()
            Log.d(TAG, "getBookList: length of array " + jsonArray.length())
            for (i in 0 until jsonArray.length()){
                jsonObjectArrayList.add(JSONObject(jsonArray.getString(i)))
            }
            for (k in jsonObjectArrayList){
                category = Category(k.getString("id"), k.getString("name"), false)
                arrayList.add(category)
            }
            for (l in arrayList){
                Log.d(TAG, "parseString: category " + l.name)
            }
        }catch (e: JSONException){
            println(e.toString())
        }
        return arrayList
    }
}
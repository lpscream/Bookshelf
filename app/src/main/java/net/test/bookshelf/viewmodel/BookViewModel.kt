package net.test.bookshelf.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.test.bookshelf.models.Book
import net.test.bookshelf.models.Category

class BookViewModel(application: Application): AndroidViewModel(application) {
    val TAG = "BookViewModel_log"
    var bookList: MutableLiveData<ArrayList<Book>> = MutableLiveData<ArrayList<Book>>()
    var categories = MutableLiveData<ArrayList<Category>>()
    val coroutineThread = CoroutineScope(Dispatchers.IO)
    val dataLoader = DataLoader()
    val state: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var limit = MutableLiveData<Int>()
    var start = "&start=0&limit="
    var offset = MutableLiveData<Int>()
    var type = ""
    var categoriesTypes = ""
    //val BASE_URL: String = "https://www.ebooksbilliger.de/api/request.php?cmd=getArticles&start=0&limit="
    val BASE_URL: String = "https://www.ebooksbilliger.de/api/request.php?cmd=getArticles"
    val CATEGORIES_URL: String = "https://www.ebooksbilliger.de/api/request.php?cmd=getCategories"

    init {
        state.value = true
        limit.value = 10
        offset.value = 0
        coroutineThread.launch {
            bookList.postValue(dataLoader.getBookList(BASE_URL + type + categoriesTypes + start  +  limit.value.toString()))
        }
    }

    fun sortBookList(){
        offset.value = bookList.value?.size
        coroutineThread.launch {
            bookList.postValue(dataLoader.getBookList(BASE_URL + type + categoriesTypes + start  + limit.value.toString()))
        }
    }

    fun getCategories(){
        coroutineThread.launch {
            categories.postValue(dataLoader.getCategories(CATEGORIES_URL))
        }
    }
}
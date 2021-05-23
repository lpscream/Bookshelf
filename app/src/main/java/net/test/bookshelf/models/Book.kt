package net.test.bookshelf.models

data class Book(var id: String){
    var ASIN: String? = null
    var title: String? = null
    var image_url: String? = null
    var image_url_hd: String? = null
    var details_url: String? = null
    var author: String? = null
    var old_price: String? = null
    var new_price: String? = null
    var old_price_time: String? = null
    var new_price_time: String? = null
    var discount: String? = null
    var info_text: String? = null
    var expired: String? = null



}

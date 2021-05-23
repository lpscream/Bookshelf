package net.test.bookshelf.ui.objects

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.test.bookshelf.R
import net.test.bookshelf.models.Book
import net.test.bookshelf.viewmodel.BookViewModel

class BookAdapter(viewModel: BookViewModel,
    var bookListArray: ArrayList<Book>
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    private val TAG = "BookAdapter_log"
    private var viewModel: BookViewModel = viewModel
    private var bookList = bookListArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        Log.d(TAG, "onCreateViewHolder: ")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = bookList.get(position).title
        holder.author.text = bookList.get(position).author
        Picasso.get()
            .load("https://www.ebooksbilliger.de/" + bookList.get(position).image_url)
            .resize(120, 160)
            .centerCrop()
            .into(holder.poster)
        if (position == (bookList.size - 5)){
            viewModel.limit.value = viewModel.limit.value?.plus(10)
            viewModel.sortBookList()
        }
        Log.d(TAG, "onBindViewHolder: bind position " + position)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: size is " + bookList.size)
        return bookList.size
    }

    fun addNewItems(list: ArrayList<Book>){
        var lastAmount = itemCount
        bookList.clear()
        bookList.addAll(list)
        notifyItemRangeChanged(itemCount, 10)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameTextView)
        val author: TextView = itemView.findViewById(R.id.authorTextView)
        val poster: ImageView = itemView.findViewById(R.id.poster)
    }




}
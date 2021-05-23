package net.test.bookshelf.ui.objects

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.test.bookshelf.R
import net.test.bookshelf.databinding.FragmentBookShelfBinding
import net.test.bookshelf.viewmodel.BookViewModel


class BookShelfFragment(viewModel: BookViewModel) : Fragment() {

    private var viewModel = viewModel
    private lateinit var mBinding: FragmentBookShelfBinding
    private lateinit var allPricesBtn: Button
    private lateinit var withDiscountBtn: Button
    private lateinit var freeBtn: Button
    private lateinit var bookList: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    val TAG = "BookShelfFragment_log"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentBookShelfBinding.inflate(layoutInflater)
        var view = inflater!!.inflate(R.layout.fragment_book_shelf, container, false)
        bookList = view.findViewById(R.id.bookShelfRecyclerView) as RecyclerView
        bookList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bookList.setHasFixedSize(false)
        viewModel.bookList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: size " + viewModel.bookList.value!!.size)
            if (viewModel.state.value!!) {
                bookAdapter = BookAdapter(viewModel, viewModel.bookList.value!!)
                bookList.adapter = bookAdapter
                bookAdapter.notifyDataSetChanged()
                Log.d(TAG, "onCreateView: create adapter")
            }else{
                bookAdapter.addNewItems(viewModel.bookList.value!!)
            }
            Log.d(TAG, "onCreateView: " + viewModel.offset.value!!)

            viewModel.state.value = false
        })

        allPricesBtn = view.findViewById(R.id.buttonAllPrices)
        withDiscountBtn = view.findViewById(R.id.buttonWithDiscount)
        freeBtn = view.findViewById(R.id.buttonFree)


        allPricesBtn.setOnClickListener {
            viewModel.state.value = true
            viewModel.type = ""
            viewModel.sortBookList()
        }

        withDiscountBtn.setOnClickListener {
            viewModel.state.value = true
            viewModel.type = "&type=2"
            viewModel.sortBookList()
        }

        freeBtn.setOnClickListener {
            viewModel.state.value = true
            viewModel.type = "&type=1"
            viewModel.sortBookList()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onStop() {
        super.onStop()
        viewModel.state.value = true
        Log.d(TAG, "onStop: ")
    }
}
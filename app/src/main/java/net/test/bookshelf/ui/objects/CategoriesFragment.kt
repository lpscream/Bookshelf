package net.test.bookshelf.ui.objects

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
import net.test.bookshelf.databinding.FragmentCategoriesBinding
import net.test.bookshelf.viewmodel.BookViewModel


class CategoriesFragment(viewModel: BookViewModel) : Fragment() {

    private var viewModel = viewModel
    private lateinit var mBinding: FragmentCategoriesBinding
    private lateinit var backBtn: Button
    private lateinit var mainBtn: Button
    private lateinit var categoriesList: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private val TAG = "CategoriesFragment_log"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentCategoriesBinding.inflate(layoutInflater)
        var view = inflater!!.inflate(R.layout.fragment_categories, container, false)
        categoriesList = view.findViewById(R.id.categoryList) as RecyclerView
        if (viewModel.categories.value == null) {
            viewModel.getCategories()
        }
        categoriesList.layoutManager  = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            categoryAdapter = CategoryAdapter(viewModel.categories.value!!, viewModel)
            categoriesList.adapter = categoryAdapter
            categoryAdapter.notifyDataSetChanged()
        })
        backBtn = view.findViewById(R.id.buttonBack)
        mainBtn = view.findViewById(R.id.buttonMain)

        backBtn.setOnClickListener {
            //clear sorted categories
            if (viewModel.categoriesTypes.startsWith("&category=")){
                viewModel.categoriesTypes = ""
                for (k in viewModel.categories.value!!){
                    k.isChecked = false
                }
            }

            var fragmentManager = activity?.supportFragmentManager
            fragmentManager?.popBackStack()
        }

        mainBtn.setOnClickListener {
            Log.d(TAG, "onCreateView: " + viewModel.categoriesTypes)
            viewModel.sortBookList()
            var fragmentManager = activity?.supportFragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.BoookShelfContainer, BookShelfFragment(viewModel))
                ?.addToBackStack(null)
                ?.commit()

        }
        return view
    }



}
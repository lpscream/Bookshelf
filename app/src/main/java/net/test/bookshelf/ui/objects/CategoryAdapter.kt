package net.test.bookshelf.ui.objects

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import net.test.bookshelf.R
import net.test.bookshelf.models.Category
import net.test.bookshelf.viewmodel.BookViewModel

class CategoryAdapter(var categoryList: ArrayList<Category>, viewModel: BookViewModel) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var viewModelCategory = viewModel
    private val TAG = "CategoryAdapter_log"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = categoryList.get(position).name
        holder.name.setOnCheckedChangeListener(null)
        holder.name.isChecked = viewModelCategory.categories.value!![position].isChecked
        Log.d(TAG, "onBindViewHolder: " + position)
        holder.name.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                viewModelCategory.categories.value!![position].isChecked = true
            } else {
                viewModelCategory.categories.value!![position].isChecked = false
            }
            if (viewModelCategory.categoriesTypes.contains("&category=")){
                viewModelCategory.categoriesTypes = viewModelCategory.categoriesTypes + viewModelCategory.categories.value!![position].id + ","
            }else{
                viewModelCategory.categoriesTypes = viewModelCategory.categoriesTypes + "&category=" + viewModelCategory.categories.value!![position].id + ","
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: CheckBox = itemView.findViewById(R.id.categoryName)
    }
}
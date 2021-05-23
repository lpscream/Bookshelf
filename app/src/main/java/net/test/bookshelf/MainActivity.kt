package net.test.bookshelf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import net.test.bookshelf.databinding.ActivityMainBinding
import net.test.bookshelf.ui.objects.AppDrawer
import net.test.bookshelf.ui.objects.BookShelfFragment
import net.test.bookshelf.viewmodel.BookViewModel
import net.test.bookshelf.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mBindeing: ActivityMainBinding
    private lateinit var bookViewModel: BookViewModel



    private lateinit var toolBar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindeing = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBindeing.root)
        bookViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(BookViewModel::class.java)
    }


    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
        var appDrawer = AppDrawer(this, toolBar, bookViewModel)
        appDrawer.create()

    }


    private fun initFields() {
        toolBar = mBindeing.toolbar
    }

    private fun initFunc() {
        //setSupportActionBar(toolBar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.BoookShelfContainer, BookShelfFragment(bookViewModel))
            .commit()
    }



}
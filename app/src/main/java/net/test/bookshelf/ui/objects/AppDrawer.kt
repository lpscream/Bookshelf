package net.test.bookshelf.ui.objects

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import net.test.bookshelf.R
import net.test.bookshelf.viewmodel.BookViewModel

class AppDrawer(val mainActivity: AppCompatActivity, val toolbar: Toolbar, var viewModel: BookViewModel) {

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var fragment: WebViewFragment


    fun create() {
        createHeader()
        createDrawer()
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(mainActivity)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem()
                    .withIcon(R.drawable.ebooksbilliger)
                    .withName("ebooksbilliger.de")
            ).build()


    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem()
                    .withIdentifier(1000)
                    .withIconTintingEnabled(true)
                    .withName("Главная")
                    .withSelectable(false),
                PrimaryDrawerItem()
                    .withIdentifier(1001)
                    .withIconTintingEnabled(true)
                    .withName("Категория")
                    .withSelectable(false),
                DividerDrawerItem(),
                PrimaryDrawerItem()
                    .withIdentifier(1002)
                    .withIconTintingEnabled(true)
                    .withName("rexit.info")
                    .withSelectable(false),
                PrimaryDrawerItem()
                    .withIdentifier(1003)
                    .withIconTintingEnabled(true)
                    .withName("google.com")
                    .withSelectable(false)
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>,
                ): Boolean {
                    when (drawerItem.identifier) {
                        1002.toLong() -> {
                            fragment = WebViewFragment(mainActivity, "https://www.rexit.info")
                            mainActivity.supportFragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.BoookShelfContainer, fragment)
                                .commit()
                        }
                        1003.toLong() -> {
                            fragment = WebViewFragment(mainActivity, "https://www.google.com")
                            mainActivity.supportFragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.BoookShelfContainer, fragment).commit()
                        }
                        1000.toLong() -> {
                            mainActivity.supportFragmentManager.beginTransaction()
                                .replace(R.id.BoookShelfContainer, BookShelfFragment(viewModel))
                                .addToBackStack(null)
                                .commit()
                        }
                        1001.toLong() -> {
                            mainActivity.supportFragmentManager.beginTransaction()
                                .replace(R.id.BoookShelfContainer, CategoriesFragment(viewModel))
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                    return false
                }
            }).build()
    }
}
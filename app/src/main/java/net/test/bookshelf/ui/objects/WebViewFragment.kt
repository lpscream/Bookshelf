package net.test.bookshelf.ui.objects

import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.test.bookshelf.MainActivity
import net.test.bookshelf.R
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException


class WebViewFragment(var activity: AppCompatActivity, val link: String) : Fragment() {

    val TAG = "WebViewFragment_log"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(R.layout.fragment_web_view, container, false)
        val web: WebView = view!!.findViewById(R.id.webview) as WebView
        web.settings.javaScriptEnabled = true
        //web.settings.domStorageEnabled = true

        web.settings.javaScriptCanOpenWindowsAutomatically = true
        web.webViewClient = object: WebViewClient(){
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?,
            ) {
                //super.onReceivedSslError(view, handler, error)
                handler?.proceed()
            }

        }
        Log.d(TAG, "onCreateView: " + link)
        try {
            web.loadUrl(link)
        }catch (e: SSLHandshakeException){
            Log.d(TAG, "onCreateView: " + e.toString())
        }


        Toast.makeText(activity, link, Toast.LENGTH_SHORT).show()
        return view
    }
}
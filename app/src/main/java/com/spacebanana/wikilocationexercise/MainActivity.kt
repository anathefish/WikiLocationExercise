package com.spacebanana.wikilocationexercise

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.spacebanana.wikilocationexercise.databinding.ActivityMainBinding
import com.spacebanana.wikilocationexercise.models.Geosearch
import com.spacebanana.wikilocationexercise.models.Pages
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val  REQUEST_LOCATION: Int = 12
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.allowBtn?.setOnClickListener({ askForPermissions() })

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation()
                } else {
                    Toast.makeText(this, "No fun, no fun", Toast.LENGTH_SHORT).show()
                }

                return
            }
        }
    }

    private fun askForPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    Array(1){Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION)
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        (fusedLocationClient)?.lastLocation?.addOnSuccessListener { location ->
            binding?.message?.setText("Your location is:\n" + location.latitude + "\n" + location.longitude)
            binding?.allowBtn?.setText("Fetch closest wiki articles")

            binding?.allowBtn?.setOnClickListener({ fetchClosestWikiArticles() })
        }
    }

    private fun fetchClosestWikiArticles() {
        WikiApiClient.create().getPagesNearby("", 10, 10, "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (query) -> getImagesFromPages(query.geosearch) },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    private fun getImagesFromPages(articles: List<Geosearch.Article>) {
        val joinedIds: String = getArticlesPagesIds(articles).joinToString { "|" }
        WikiApiClient.create().getPageProperty(
                "images",
                joinedIds,
                "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (query) ->  },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    private fun getArticlesPagesIds(articles: List<Geosearch.Article>): List<String> {
        val result = ArrayList<String>()
        return articles.mapTo(result) { it.pageid.toString() }
    }

    private fun getSimilarImageTitles(pages: List<Pages.Page>): List<String> {
        val result = ArrayList<String>()

        for (page: Pages.Page in pages) {
            val titles = page.images.map { image: Pages.Image -> image.title }

        }

        return result
    }

}


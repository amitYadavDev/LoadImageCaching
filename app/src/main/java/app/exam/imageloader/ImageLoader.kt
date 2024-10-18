package app.exam.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class ImageLoader(context: Context) {
    // In-memory cache using LruCache
    private val memoryCache: LruCache<String, Bitmap>
    private val cacheDir: File = File(context.cacheDir, "images")

    init {
        // Set the max memory to use for the cache
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 4 // Use 1/8th of the available memory for caching

        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024 // Size in KB
            }
        }

        // Create disk cache directory if it doesn't exist
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    // Function to load an image from cache or download it
    suspend fun loadImage(url: String): Bitmap? {
        // Check if image is in memory cache
        memoryCache.get(url)?.let {
            Log.i("ImageLoader_abc", " fetching from memoryCache")
            return it
        }

        // Check if image is in disk cache
        getBitmapFromDiskCache(url)?.let {
            memoryCache.put(url, it)
            Log.i("ImageLoader_abc", " fetching from Disk Cache")
            return it
        }

        Log.i("ImageLoader_abc", "downloading and caching the image")
        // Download image if it's not in cache
        return downloadImage(url)?.also {
            memoryCache.put(url, it)
            saveBitmapToDiskCache(url, it)
        }
    }

    // Download image asynchronously
    private suspend fun downloadImage(url: String): Bitmap?  {
        var connection: HttpURLConnection? = null
        try {
            val urlConnection = URL(url)
            connection = urlConnection.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
        return null
    }

    // Get bitmap from disk cache
    private fun getBitmapFromDiskCache(url: String): Bitmap? {
        val file = File(cacheDir, url.hashCode().toString())
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.absolutePath)
        }
        return null
    }

    // Save bitmap to disk cache
    private fun saveBitmapToDiskCache(url: String, bitmap: Bitmap) {
        val file = File(cacheDir, url.hashCode().toString())
        if (!file.exists()) {
            file.outputStream().use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }
    }
}

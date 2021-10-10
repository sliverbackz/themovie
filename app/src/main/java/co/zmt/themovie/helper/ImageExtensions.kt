package co.zmt.themovie.helper

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import co.zmt.themovie.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.loadFromUrl(url: String, cornerRadius: Int) {
    Glide.with(context)
        .load(url)
        .transform(RoundedCorners(cornerRadius))
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(this)
}

fun ImageView.loadFromUrl(@DrawableRes url: Int) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadFromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadFromUrl(url: String, @DrawableRes placeHolder: Int, @DrawableRes error: Int) {
    Glide.with(context)
        .load(url)
        .placeholder(placeHolder)
        .error(error)
        .into(this)

}

inline fun ImageView.loadFromUrl(
    url: String,
    @DrawableRes drawableInt: Int,
    crossinline loadFailed: () -> Unit,
    crossinline resourceReady: (view: ImageView, resource: Drawable?) -> Unit = { _, _ -> }
) {
    val view: ImageView = this
    Glide.with(context)
        .load(url)
        .placeholder(drawableInt)
        .error(drawableInt)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadFailed()
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                view.setImageDrawable(resource)
                resourceReady(view, resource)
                return true
            }

        })
        .into(this)

}
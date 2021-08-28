package co.jonathanbernal.mercashop.common.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun ImageView.loadMovieImage(path: String) {
    Glide
        .with(this.context)
        .load(path)
        .into(this)
}


@JvmName("addToComposite")
fun Disposable.addTo(disposableComposite: CompositeDisposable) {
    disposableComposite.add(this)
}

fun View.setVisibility(isVisibility: Boolean) {
    this.visibility = if (isVisibility) View.VISIBLE else View.GONE
}

fun Context.toast(message:String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
fun Context.toast(message:Int) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

@BindingAdapter("loadimage")
fun bindingImage(imageView: ImageView, path: String?) {
    if (path != null) {
        imageView.loadMovieImage(path)
    }
}

@BindingAdapter("visibility")
fun setVisibilityBinding(view: View, visible: Boolean) {
    view.setVisibility(visible)
}
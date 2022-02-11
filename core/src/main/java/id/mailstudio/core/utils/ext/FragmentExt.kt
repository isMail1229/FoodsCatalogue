package id.mailstudio.core.utils.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T> Fragment.observe (liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(viewLifecycleOwner, observer)
}

inline fun <reified VM : ViewModel> Fragment.fragmentViewModel(provider: ViewModelProvider.Factory) =
    ViewModelProvider(this, provider).get(VM::class.java)

fun Fragment.navigateToFragment(fragment: Fragment, tag: String) {
    childFragmentManager.beginTransaction()
        .add(fragment, tag)
        .commitAllowingStateLoss()
}
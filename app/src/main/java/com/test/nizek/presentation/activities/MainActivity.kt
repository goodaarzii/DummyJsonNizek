package com.test.nizek.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.test.nizek.presentation.fragments.ProductSearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val frameLayout = FrameLayout(this).apply {
            id = View.generateViewId()
        }
        setContentView(frameLayout)


        if (savedInstanceState == null) {
            addFragment(ProductSearchFragment(),frameLayout)
        }

    }


    private fun addFragment(fragment: Fragment, frameLayout: FrameLayout) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(frameLayout.id, fragment)

        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}

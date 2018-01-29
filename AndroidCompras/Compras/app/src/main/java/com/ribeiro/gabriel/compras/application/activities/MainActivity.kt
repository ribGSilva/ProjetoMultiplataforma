package com.ribeiro.gabriel.compras.application.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.ribeiro.gabriel.compras.R
import com.ribeiro.gabriel.compras.R.id.content
import com.ribeiro.gabriel.compras.application.factories.FragmentFactory
import com.ribeiro.gabriel.compras.application.types.Fragments

class MainActivity : FragmentActivity() {

    private var currentFragment: Fragments = Fragments.FRAGMENT_HOME

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                changeFragment(Fragments.FRAGMENT_HOME)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                changeFragment(Fragments.FRAGMENT_REGISTER)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                changeFragment(Fragments.FRAGMENT_REGISTERED)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(content, FragmentFactory.createFragmentByType(currentFragment), currentFragment.name)
        fragmentTransaction.commit()
    }

    private fun changeFragment(newFragmentType: Fragments) {
        if (currentFragment == newFragmentType) return
        var fragmentThatWillAppear = findFragmentOnBackStack(newFragmentType)
        if (fragmentThatWillAppear == null) {
            fragmentThatWillAppear = FragmentFactory.createFragmentByType(newFragmentType)

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(content, fragmentThatWillAppear, newFragmentType.name)
            fragmentTransaction.commit()
        } else {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.show(fragmentThatWillAppear)
            fragmentTransaction.commit()
        }

        currentFragment = newFragmentType
    }


    private fun findFragmentOnBackStack(newFragmentType: Fragments): Fragment? {
        return supportFragmentManager.findFragmentByTag(newFragmentType.name)
    }
}

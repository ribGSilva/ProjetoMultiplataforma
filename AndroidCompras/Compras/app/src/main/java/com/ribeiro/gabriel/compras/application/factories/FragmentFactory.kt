package com.ribeiro.gabriel.compras.application.factories

import android.support.v4.app.Fragment
import com.ribeiro.gabriel.compras.application.fragments.HomeFragment
import com.ribeiro.gabriel.compras.application.fragments.RegisterFragment
import com.ribeiro.gabriel.compras.application.fragments.RegisteredFragment
import com.ribeiro.gabriel.compras.application.types.Fragments

object FragmentFactory {

    fun createFragmentByType(fragmentType: Fragments): Fragment {
        return when (fragmentType) {
            Fragments.FRAGMENT_HOME -> createInstanceOfHomeFragment()
            Fragments.FRAGMENT_REGISTER -> createInstanceOfRegisterFragment()
            Fragments.FRAGMENT_REGISTERED -> createInstanceOfRegisteredFragment()
        }
    }

    private fun createInstanceOfRegisteredFragment(): RegisteredFragment {
        return RegisteredFragment()
    }

    private fun createInstanceOfRegisterFragment(): RegisterFragment {
        return RegisterFragment()
    }

    private fun createInstanceOfHomeFragment(): HomeFragment {
        return HomeFragment()
    }
}
package stu.ntdat.chatisfun.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        initControl()
        return binding.root
    }

    private fun initControl() {
        if (isLogin()) navigateHome()
        binding.accountFalseBtnLogin.setOnClickListener {
            loginClick()
        }
        binding.accountFalseBtnRegister.setOnClickListener {
            registerClick()
        }
    }

    private fun navigateHome() {
        findNavController().navigate(R.id.messageFragment)
    }

    private fun loginClick() {
        findNavController().navigate(R.id.loginFragment)
    }

    private fun registerClick() {
        findNavController().navigate(R.id.registerFragment)
    }

    private fun isLogin() = FirebaseAuth.getInstance().currentUser != null
}
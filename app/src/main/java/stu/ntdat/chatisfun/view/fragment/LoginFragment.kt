package stu.ntdat.chatisfun.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.FragmentLoginBinding
import stu.ntdat.chatisfun.util.hideKeyboard
import stu.ntdat.chatisfun.viewmodel.AuthViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        initControls()
        initEvents()
        return binding.root
    }

    private fun initEvents() {
        binding.loginBtn.setOnClickListener {
            click()
        }
        viewModel.message.observe(viewLifecycleOwner) {
            if (viewModel.isLogin()) {
                findNavController().navigate(R.id.messageFragment)
                viewModel.resetMessage()
            }
        }
    }

    private fun click() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        this.hideKeyboard()
        viewModel.login(email, password)
    }

    private fun initControls() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}
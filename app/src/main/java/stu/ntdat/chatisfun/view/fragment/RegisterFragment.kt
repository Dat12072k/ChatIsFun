package stu.ntdat.chatisfun.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.FragmentRegisterBinding
import stu.ntdat.chatisfun.util.hideKeyboard
import stu.ntdat.chatisfun.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        initControls()
        intent2FillData()
        binding.registerBtn.setOnClickListener {
            click()
        }

        return binding.root
    }

    private fun click() {
        val email = binding.registerEmail.text.toString()
        val password = binding.registerPassword.text.toString()
        val rePassword = binding.registerRePassword.text.toString()
        this.hideKeyboard()
        viewModel.register(email, password, rePassword)
    }

    private fun intent2FillData() {
        viewModel.message.observe(viewLifecycleOwner) {
            if (viewModel.isLogin()) {
                findNavController().navigate(R.id.messageFragment)
                viewModel.resetMessage()
            }
        }
    }

    private fun initControls() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}
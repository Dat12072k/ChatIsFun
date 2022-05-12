package stu.ntdat.chatisfun.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.FragmentInfoBinding
import stu.ntdat.chatisfun.viewmodel.InfoViewModel

@AndroidEntryPoint
class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding

    private val viewModel by activityViewModels<InfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        initControls()
        initEvents()
        return binding.root
    }

    private fun initEvents() {
        binding.accountTrueBtnSignOut.setOnClickListener {
            signOutClick()
        }
    }

    private fun initControls() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.updateInfo()
    }

    private fun signOutClick() {
        viewModel.signOut()
        findNavController().navigate(R.id.splashFragment)
    }



}
package com.frommetoyou.baseapp.presentation.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.frommetoyou.baseapp.R
import com.frommetoyou.baseapp.databinding.FragmentMainBinding
import com.frommetoyou.baseapp.presentation.ui.uimodel.MainFragmentUiModel
import com.frommetoyou.baseapp.presentation.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {
    //TODO eliminar clase example ↑
    override val mViewModel: MainFragmentViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_main

    private val uiModelObserver = Observer<MainFragmentUiModel> {
        it.onShowMessageEvent?.consume()?.let { message -> showMessage(message) }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        mViewModel.uiState.observe(viewLifecycleOwner, uiModelObserver)
        //TODO eliminar mensaje example
        mViewModel.getMessage("funciona!")
    }
}
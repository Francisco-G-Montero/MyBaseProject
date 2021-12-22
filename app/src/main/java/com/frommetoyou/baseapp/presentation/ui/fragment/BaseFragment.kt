package com.frommetoyou.baseapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.frommetoyou.baseapp.R
import com.frommetoyou.baseapp.presentation.viewmodel.BaseViewModel

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    //TODO reemplazar con un fragment para tener un loading mas personalizado
    private var loadingView: View? = null
    protected abstract val mViewModel: VM
    abstract val layoutId: Int
    private var _binding: T? = null
    protected val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView = activity?.findViewById(R.id.circular_progress_indicator)
        setupUiElements()
        setupViewModel()
    }

    open fun setupUiElements(){}

    protected open fun setupViewModel() {
        mViewModel.showLoading.observe(viewLifecycleOwner, showLoadingObserver)
    }

    private val showLoadingObserver = Observer<Boolean> {
        if (it) showLoading() else hideLoading()
    }

    protected open fun showLoading() {
        loadingView?.visibility = View.VISIBLE
    }

    protected fun hideLoading() {
        loadingView?.visibility = View.GONE
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingView = null
        _binding = null
    }
}
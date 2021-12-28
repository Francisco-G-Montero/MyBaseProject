package com.frommetoyou.baseapp.presentation.ui.fragment

import android.content.ContentResolver
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.frommetoyou.baseapp.R
import com.frommetoyou.baseapp.databinding.FragmentContactsBinding
import com.frommetoyou.baseapp.presentation.ui.uimodel.ContactsUiModel
import com.frommetoyou.baseapp.presentation.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding, ContactsViewModel>() {
    override val mViewModel: ContactsViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_contacts

    private val requestReadContactsPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted)
                mViewModel.onReadContactsPermissionGranted()
        }

    private val uiModelObserver = Observer<ContactsUiModel> {
        it.onReadContactsGrantedEvent?.consume()?.let { readContacts() }
        it.onShowContactsEvent?.consume()?.let { contacts -> showContacts(contacts) }
        it.onShowMessage?.consume()?.let { message -> showMessage(message) }
    }

    private fun showContacts(contacts: String) {
        mBinding.tvContacts.text = contacts
    }

    private fun requestReadContactsPermission() {
        mViewModel.requestPermission(requestReadContactsPermissionLauncher)
    }

    private fun readContacts() {
        Toast.makeText(requireContext(), "asd", Toast.LENGTH_SHORT).show()
        mViewModel.getContactsData()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        mViewModel.uiState.observe(viewLifecycleOwner, uiModelObserver)
        requestReadContactsPermission()
    }
}
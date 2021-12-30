package com.frommetoyou.baseapp.presentation.ui.fragment

import android.app.Activity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.frommetoyou.baseapp.databinding.FragmentMainBinding
import com.frommetoyou.baseapp.presentation.ui.uimodel.MainFragmentUiModel
import com.frommetoyou.baseapp.presentation.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.model.Payment
import android.content.Intent
import com.mercadopago.android.px.model.exceptions.MercadoPagoError
import android.app.Activity.RESULT_CANCELED
import androidx.activity.result.contract.ActivityResultContracts
import com.frommetoyou.baseapp.R
import com.frommetoyou.moviesbuyer.presentation.ui.activity.MoviesBuyerActivity
import com.frommetoyou.moviesbuyer.presentation.util.MoviesBuyer
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {
    //TODO eliminar clase example ↑
    override val mViewModel: MainFragmentViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_main
    val REQUEST_MP = 1111

    private val uiModelObserver = Observer<MainFragmentUiModel> {
        it.onShowMessageEvent?.consume()?.let { message -> showMessage(message) }
        it.onOpenMPCheckout?.consume()?.let { checkoutId -> openCheckout(checkoutId) }
    }

    private fun openCheckout(checkoutId: String) {
        val publicKey = getString(R.string.mercadopago_public_key)
        val checkout = MercadoPagoCheckout.Builder(publicKey, checkoutId)
            .build()
        checkout.startPayment(requireContext(), REQUEST_MP)
    }

    override fun setupViewModel() {
        super.setupViewModel()
        mViewModel.uiState.observe(viewLifecycleOwner, uiModelObserver)
    }

    override fun setupUiElements() {
        super.setupUiElements()
        mBinding.etAmount.setText("0")
        mBinding.btnMercado.setOnClickListener {
            mViewModel.startMPCheckout(
                mBinding.etAmount.text.toString().toInt(),
                mBinding.etItemTitle.text.toString(),
                mBinding.etItemDesc.text.toString(),
                mBinding.npQuantity.value
            )
        }
        mBinding.npQuantity.minValue = 1
        mBinding.npQuantity.maxValue = 10
        mBinding.btnMoviesBuyer.setOnClickListener {
            MoviesBuyer.startPurchase(requireContext(), resultLauncher)
        }
    }

    val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK){
            it.data?.getStringExtra("comic")?.let { it1 -> showMessage(it1) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_MP) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                val payment =
                    data!!.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment?

                showMessage("Resultado del pago: " + payment!!.paymentStatus)
                //Done!
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null && data.extras != null && data.extras!!.containsKey(
                        MercadoPagoCheckout.EXTRA_ERROR
                    )
                ) {
                    val mercadoPagoError =
                        data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR) as MercadoPagoError?

                    showMessage("Error: " + mercadoPagoError!!.message)
                    //Resolve error in checkout
                } else {
                    //Resolve canceled checkout
                    showMessage("Flujo cancelado")
                }
            }
        }
    }
}
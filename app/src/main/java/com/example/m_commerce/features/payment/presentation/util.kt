package com.example.m_commerce.features.payment.presentation

import android.util.Log
import com.stripe.android.paymentsheet.PaymentSheetResult

val paymentResultcallback: (PaymentSheetResult) -> Unit = { result ->
    when (result) {
        is PaymentSheetResult.Canceled -> {
            Log.i("TAG", "payment canceled")
        }
        is PaymentSheetResult.Failed -> {
            Log.i("TAG", " Error: ${result.error.message}")
        }
        is PaymentSheetResult.Completed -> {
            Log.i("TAG", "Payment Successful")
        }
    }
}
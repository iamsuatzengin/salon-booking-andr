package com.zapplications.salonbooking.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.zapplications.salonbooking.core.extensions.parcelable

class SMSReceiver : BroadcastReceiver() {
    var smsReceiverListener: SmsReceiverListener? = null

    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val smsRetrieverStatus = extras?.parcelable<Status>(SmsRetriever.EXTRA_STATUS) ?: return

            when (smsRetrieverStatus.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    runCatching {
                        val consentIntent =
                            extras.parcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        smsReceiverListener?.onReceived(consentIntent)
                    }.onFailure {
                        smsReceiverListener?.onFailure(it)
                    }
                }

                CommonStatusCodes.TIMEOUT -> {
                    smsReceiverListener?.onTimeout()
                }
            }
        }
    }
}

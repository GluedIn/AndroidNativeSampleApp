package com.gluedin.sample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.usecase.constants.GluedInConstants
import com.gluedin.GluedInInitializer
import com.gluedin.config.analytics.GluedInAnalyticsCallback
import com.gluedin.config.login.GluedInSDKCallBack
import com.gluedin.config.login.GluedInSdkException
import com.gluedin.data.persistence.analytics.AnalyticsEvents
import com.gluedin.domain.entities.config.ShareData
import com.gluedin.domain.entities.feed.VideoInfo
import com.gluedin.domain.entities.feed.ads.AdsRequestParams
import com.gluedin.domain.entities.feed.ads.AdsType


class MainActivity : AppCompatActivity(), GluedInAnalyticsCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fullScreen()
        launchGluedIn()
    }

    private fun fullScreen() {
        window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun launchGluedIn() {

        val gluedInSDKcallback = object : GluedInSDKCallBack {
            override fun onSdkInitSuccess(
                isSuccess: Boolean,
                gluedInSdkException: GluedInSdkException?,
            ) {

                if (isSuccess) {
                    Toast.makeText(
                        this@MainActivity,
                        "GluedInSDK Launched Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    gluedInSdkException?.getErrorMessage().let {
                        Toast.makeText(
                            this@MainActivity,
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onAppAuthSuccess(
                isSuccess: Boolean,
                gluedInSdkException: GluedInSdkException?,
            ) = Unit

            override fun onAdsRequest(
                adsType: AdsType,
                adsRequestParams: AdsRequestParams,
            ): Fragment? {
                return null
            }

            override fun onLoginRegistrationRequired(
                currentVideo: VideoInfo?,
                isLoginAction: Boolean,
            ) = Unit

            override fun onSdkLogout() = Unit

            override fun onSdkExit() = Unit

            override fun onGluedInShareAction(shareData: ShareData) = Unit

            override fun onProductCTAClicked(assetId: String, eventRefId: Int) = Unit

            override fun onUserProfileClick(userId: String) = Unit
        }
        val gluedInConfigurations = GluedInInitializer.Configurations.Builder()
            .setSdkCallback(gluedInSDKcallback)
            .setApiKey("put_your_api_key_here")
            .setSecretKey("put_your_secret_key_here")
            .setFeedType(GluedInInitializer.Configurations.FeedType.VERTICAL) // GluedInInitializer.Configurations.FeedType.SQUARE
            .create()

        gluedInConfigurations.validateAndLaunchGluedInSDK(
            this,
            GluedInConstants.LaunchType.APP,
            intent,
        )
    }

    override fun onAnalyticsEvent(event: AnalyticsEvents) {
        Log.d("GluedIn", "SDK Analytics initiated: " + event.eventType.eventName)
    }

}

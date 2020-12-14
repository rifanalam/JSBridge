package com.yourcompany.jsbridge

import com.google.gson.annotations.SerializedName

data class AppDataModel(
    @SerializedName("fcm_token") var fcmToken: String,
    @SerializedName("ads_id") var adsId: String,
    @SerializedName("user_agent") var userAgent: String,
    @SerializedName("language") var language: String,
    @SerializedName("platform") var platform: String
)
package com.example.m_commerce.features.orders.data.model

import com.google.gson.annotations.SerializedName

data class EmailRequest(
    @SerializedName("service_id") val serviceId: String,
    @SerializedName("template_id") val templateId: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("template_params") val templateParams: Map<String, String>
)

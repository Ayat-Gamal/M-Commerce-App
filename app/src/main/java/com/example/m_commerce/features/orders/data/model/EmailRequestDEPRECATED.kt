package com.example.m_commerce.features.orders.data.model

//data class EmailRequestDEPRECATED(
//    @SerializedName("service_id") val serviceId: String,
//    @SerializedName("template_id") val templateId: String,
//    @SerializedName("user_id") val userId: String,
//    @SerializedName("template_params") val templateParams: Map<String, String>
//)

data class EmailPersonalization(
    val email: String,
    val name: String
)

data class EmailContent(
    val type: String = "text/html",
    val value: String
)

data class MailerEmailRequest(
    val from: EmailPersonalization,
    val to: List<EmailPersonalization>,
    val subject: String,
    val html: String
)

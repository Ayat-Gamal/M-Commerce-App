package com.example.m_commerce.features.orders.data.model

data class EmailRequest(
    val to: List<String>,
    val subject: String,
    val content: EmailContent,
    private val text: String = "",
    val html: String = """        
        <h2>Hello ${content.toName},</h2>
        <p>Thank you for your order from <strong>${content.fromName}</strong>!</p>
        <p>Here are your order details:</p>
        <ul>
        <li><strong>Order ID: </strong> ${content.orderId}</li>
        <li><strong>Product: </strong> ${content.productName}</li>
        <li><strong>Total: </strong> ${content.total}<
        /li>
        </ul>
        <br/>
        <p>Best regards,<br/>The ${content.fromName} Team</p>
    """.trimIndent(),
){
    fun toHashMap(): Map<String, Any> = mapOf(
        "to" to to,
        "message" to mapOf(
            "subject" to subject,
            "text" to text,
            "html" to html,
        )
    )
}

data class EmailContent(
    val toName: String,
    val fromName: String,
    val orderId: String,
    val orderDate: String,
    val productName: String,
    val total: String
)

package com.example.m_commerce.features.orders.data.model


val  createDraftOrderQuery = """
mutation CreateDraftOrder(
    ${"$"}email: String!,
    ${"$"}shippingAddress: MailingAddressInput!,
    ${"$"}lineItems: [DraftOrderLineItemInput!]!,
    ${"$"}note: String
) {
  draftOrderCreate(input: {
    email: ${"$"}email,
    shippingAddress: ${"$"}shippingAddress,
    lineItems: ${"$"}lineItems,
    note: ${"$"}note
  }) {
    draftOrder {
      id
      name
      createdAt
      invoiceUrl
    }
    userErrors {
      field
      message
    }
  }
}
""".trimIndent()


val completeDraftOrderQuery = """
mutation CompleteDraftOrder(${"$"}id: ID!) {
  draftOrderComplete(id: ${"$"}id, paymentPending: false) {
    draftOrder {
      id
      name
      status
    }
    userErrors {
      field
      message
    }
  }
}
""".trimIndent()
import com.stripe.Stripe
import com.stripe.model.Price
import com.stripe.param.PriceCreateParams

fun CreatePrice(
    film: String
): String {
    Stripe.apiKey = "sk_test_51ONCV8CAmt1jJUuJwoJqkXtV7oynCR1q26fDrH0DSdg61lSPdYcBm6rtJSw2w1reDwzxI7dAObZSpkIs30bscsiO00kJ9Bdwgd"
    val params = PriceCreateParams.builder()
        .setCurrency("rub")
        .setUnitAmount(100L)
        .setProductData(
            PriceCreateParams.ProductData.builder().setName(film).build()
        )
        .build()

    val price = Price.create(params)
    return price.id
}
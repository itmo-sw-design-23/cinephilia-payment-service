import com.stripe.Stripe
import com.stripe.model.checkout.Session
import com.stripe.net.RequestOptions
import com.stripe.param.checkout.SessionCreateParams
import java.util.*

fun CreatePayment(
    film: String
): Boolean {
    Stripe.apiKey =
        "sk_test_51ONCV8CAmt1jJUuJwoJqkXtV7oynCR1q26fDrH0DSdg61lSPdYcBm6rtJSw2w1reDwzxI7dAObZSpkIs30bscsiO00kJ9Bdwgd"


    val price: String = CreatePrice(film)
    val params = SessionCreateParams.builder()
        .addLineItem(
            SessionCreateParams.LineItem.builder()
                .setPrice(price)
                .setQuantity(1L)
                .build()
        )
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        .setCancelUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        .build()

    val requestOptions: RequestOptions =
        RequestOptions.builder().setStripeAccount("acct_1ONCV8CAmt1jJUuJ").build()
    val session = Session.create(params, requestOptions)
    println(session.getUrl())
    return true
}


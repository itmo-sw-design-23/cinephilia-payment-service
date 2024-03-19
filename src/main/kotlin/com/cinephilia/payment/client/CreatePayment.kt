import com.stripe.Stripe
import com.stripe.model.checkout.Session
import com.stripe.net.RequestOptions
import com.stripe.param.checkout.SessionCreateParams
import java.util.*

fun CreatePayment(
    film: String,
    priceAmount: Long,
    paymentID: UUID
): String {
    Stripe.apiKey = System.getenv("STRIPE_API_KEY")

    val price: String = CreatePrice(film, priceAmount)
    val params = SessionCreateParams.builder()
        .addLineItem(
            SessionCreateParams.LineItem.builder()
                .setPrice(price)
                .setQuantity(1)
                .build()
        )
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        .setCancelUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        .setPaymentIntentData(
            SessionCreateParams.PaymentIntentData.builder()
                .putMetadata("film", film)
                .putMetadata("paymentID", paymentID.toString())
                .build()
        )
        .build()

    val requestOptions: RequestOptions =
        RequestOptions.builder().setStripeAccount("acct_1ONCV8CAmt1jJUuJ").build()
    val session = Session.create(params, requestOptions)

    return session.url
}


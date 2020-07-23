package id.web.adit.core.scope


// XXX Have an annotation @ReadOnly(true) / @ReadOnly(false) (default:@ReadOnly(true)) that says whether BF writes to DB or not.
// Writing BFs are not allowed to be called directly from the UI but must be nested in the BusinessTaskCallStack within a BT call.

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class BusinessFunction(val desc: String = "")

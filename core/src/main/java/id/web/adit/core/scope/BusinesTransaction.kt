package id.web.adit.core.scope

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS
)
annotation class BusinesTransaction(val desc: String = "")


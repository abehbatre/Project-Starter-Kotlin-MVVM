package id.web.adit.core.scope

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Parameters(
    val `in`: String = "",
    val out: String = ""
)

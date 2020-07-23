package id.web.adit.starter.networking


/**
 * @author Aditya Pratama
 * @param [T] : LiveData
 * @reference https://gist.github.com/karntrehan/4d0fac4cb8503e584db62fd45fe44bbd#file-outcome-kt
 *
 * */

data class Outcome<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(data: T?): Outcome<T> = Outcome(Status.LOADING, data, null)

        fun <T> success(data: T?): Outcome<T> = Outcome(Status.SUCCESS, data, null)

        fun <T> error(msg: String, data: T?): Outcome<T> = Outcome(Status.ERROR, data, msg)
    }

}

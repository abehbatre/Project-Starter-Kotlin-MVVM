package id.web.adit.starter.networking

import id.web.adit.starter.BuildConfig

object HttpConfig {

    const val BASE_URL = BuildConfig.BASE_URL


    /** [SSL_FILE]
     * @since Android Nougat (7.0)+
     * digunakan jika Base URL tidak mempunyai proteksi SSL (https)
     * example : http://blabla.com, http://192.168.0.10
     * */
    const val SSL_PATH = ""


    /** [RETRY_TIMES] ->
     *  Interval pengulangan ketika gagal memuat data .
     *  value start from 0
     * */
    const val RETRY_TIMES = 0L



    /** [TOKEN_AUTH] ->
     *  header parameter (token) : Authorization header
     * */
    const val TOKEN_AUTH = "X-Auth-Token"


    /** [PAGE_PER_PAGE] ->
     *  nilai output data yang digunakan untuk pagination.
     * */
    const val PAGE     = "1"                        // default value
    const val PER_PAGE = "100"                      // default value


    // TimeOut
    const val CONNECT_TIME_OUT = 30L                // Second
    const val WRITE_TIME_OUT   = 30L                // Second
    const val READ_TIME_OUT    = 30L                // Second

}

object HttpCode {
    const val NO_CONNECTION         = 0
    const val BAD_REQUEST           = 400
    const val NOT_FOUND             = 404
    const val WRONG_METHOD          = 405
    const val INTERNAL_SERVER_ERROR = 500
    const val SERVER_MAINTENANCE    = 503
}

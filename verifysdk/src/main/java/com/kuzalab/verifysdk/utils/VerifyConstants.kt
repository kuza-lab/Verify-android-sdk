/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 5:41 PM
 *
 */

package com.kuzalab.verifysdk.utils

internal class VerifyConstants {
    internal val VERIFY_DATE_FORMAT = "yyyy-MM-dd"
    internal val VERIFY_GENDER_ARRAY = arrayOf("M", "F")

    internal val consumerKey = "wertQwscvbpothdkplkjhuqswrtycvgt"
    internal val consumerSecret = "lkiutvbQaXtvcplRtbnUWspolkRfhsdu"


}


enum class CALL_TAGS {
    SEARCH_PERSON,
    VERIFY_PERSON,
    SEARCH_NCA_ID,
    SEARCH_NCA_NAME,
    VERIFY_NCA
}
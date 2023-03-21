/*
 * Copyright © 2020 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.kotlin.learn.core.util

object Constants {
    const val DEFAULT_DECIMAL_POINT_SIGN = "."
    const val PLUS = "+"
    const val COMMA_PLUS = ", +"
    const val NO_POSITION = -1
    const val ZERO = 0
    const val ZERO_STRING = "0"
    const val SPACE_STRING = " "
    const val EMPTY_STRING = ""
    const val ONE = 1
    const val TWO = 2
    const val TWO_STRING = "2"
    const val THREE_STRING = "3"
    const val SIX_STRING = "6"
    const val SEVEN_STRING = "7"
    const val NINE_STRING = "9"
    const val THREE = 3
    const val SIX = 6
    const val SEVEN = 7
    const val FIVE = 5
    const val FIVE_FLOAT = 5f
    const val FOUR = 4
    const val EIGHT = 8
    const val NINE = 9
    const val TEN = 10
    const val ONE_HUNDRED_TEN = 110
    const val EIGHTY_FLOAT = 80f
    const val SIXTY_FLOAT = 60f
    const val ELEVEN = 11
    const val THIRTEEN = 13
    const val THIRTEEN_LONG = 13L
    const val FIVE_TEEN_FLOAT = 15F
    const val SIX_TEEN = 16
    const val FIVE_TEEN = 15
    const val TWENTY_FOUR = 24
    const val FIFTY_SIX = 56
    const val ONE_HUNDRED = 100
    const val ONE_MILLION = 1000000
    const val ONE_HUNDRED_FIFTY = 150f
    const val TWO_HUNDRED_FLOAT = 200f
    const val TWO_HUNDRED_FIFTY_FLOAT = 250f
    const val FIVE_HUNDRED = 500
    const val FIVE_HUNDRED_LONG = 500L
    const val TWO_HUNDRED_FIFTY_LONG = 250L
    const val THOUSAND = 1000
    const val ONE_THOUSAND_FOUR_HUNDRED = 1400
    const val HYPHEN_WITH_SPACE = " - "
    const val COLON = " : "
    const val HYPHEN = "-"
    const val UNDER_SCORE = "_"
    const val DEFAULT_CURRENCY_RP = "Rp"
    const val DEFAULT_CURRENCY_IDR = "IDR"
    const val REGEX_CURRENCY_IDR = "[Rp. ]"
    const val PERCENT = "%"
    const val AT_SIGN = "@"
    const val ASTERISK = '*'
    const val COMA = ','
    const val DOT_STRING = '.'
    const val DOT_ZERO_STRING = ".0"
    const val DOT = '\u25CF'
    const val ONE_FLOAT = 1f
    const val THREE_FLOAT = 3f
    const val ALPHANUMERIC = "^[a-zA-Z0-9\\s]*$"
    const val NUMERIC_PATTERN = "^[0-9]+\$"
    const val ALPHABETS = "^[a-zA-Z]*\$"
    const val INDONESIA_LOCALE_TAG = "in-ID"
    const val USA_LOCALE_TAG = "en-US"
    const val SLASH = "/"
    const val ZERO_FLOAT = 0f
    const val TEN_FLOAT = 10.0f
    const val ZERO_POINT_NINE_FIVE_FLOAT = 0.95f
    const val ZERO_POINT_FIVETEEN_FLOAT = 0.15f
    const val ZERO_POINT_TWO_FIVE_FLOAT = 0.25f
    const val ZERO_POINT_SEVEN_ZERO_FLOAT = 0.70f
    const val ZERO_POINT_TWO_ZERO_FLOAT = 0.20f
    const val ZERO_POINT_SIX_ZERO_FLOAT = 0.60f
    const val ZERO_POINT_EIGHT_FIVE_FLOAT = 0.85f
    const val ZERO_POINT_FIVETY_FIVE_FLOAT = 0.55f
    const val TIMES = "x"
    const val NINETY_NINE = 99
    const val PROD = "production"
    const val VERTICAL_SCROLL_RANGE = 1.75
    const val THREE_HUNDRED_THIRTY = 320F
    const val IP_TRANSFORM_FORMAT = 0xff
    const val PLUS_SYMBOL = "+"
    const val MULTI_EMAIL_DIVIDER = ", +"
    const val MINUS_SYMBOL = "-"
    const val NUMBER_16384 = 16384
    const val MINUS_1 = -1
    const val NUMBER_1024 = 1024
    const val LOV_OTHERS_CODE = "OTHERS"
    const val STATUS_SUCCESS = "SUCCESS"
    const val REQUEST_CODE_PERMISSIONS = 10
    const val SOURCE_IMAGE_FROM_LINK = "link"
    const val SOURCE_IMAGE_FROM_FILE = "file"
    const val UTC = "UTC"
    const val MAX_RETRY = 3

    object PhoneNumber {
        const val INDONESIA_PHONE_NUMBER = "+62"
        const val INDONESIA_PHONE_NUMBER_CLEAR = "62"
    }

    const val GUIDELINE_FULL = 1F
    const val GUIDELINE_THREE_QUARTER = 0.75F
    const val GUIDELINE_HALF = 0.5F
    const val GUIDELINE_QUARTER = 0.25F
    const val TITLE = "title"
    const val DESCRIPTION = "description"
    const val TYPE_STATUS = "typeStatus"
    const val SUCCESS = "Success"
    const val LOADING = "Loading"
    const val DELAY_MILLIS_ONE_SECOND = 1000L
    const val DEFAULT_DELAY = 300L
    const val DELAY_MILLIS_HALF_SECOND = 500L
    const val DELAY_COROUTINE_SEQUENTIAL = 200L
    const val DEFAULT_APPLICATION_IDLE_TIME = "900000"
    const val ZERO_DOUBLE = 0.00
    const val ZERO_LONG = 0L
    const val REQUEST_CHANNEL_TABLET = "tablet"

    object Casa {
        const val SAVING = "S"
        const val CURRENT = "D"
        const val DEPOSITS = "T"
    }

    object AccountType {
        const val TYPE_GIRO = "D"
    }

    object DebitCreditCode {
        const val CODE_DEPOSIT = "D"
        const val CODE_CREDIT = "C"
    }

    object PadIndicator {
        const val PIN_PATTERN = "[1234567890]*"
        const val PIN_LENGTH_DEFAULT = SIX
    }

    object InputPad {
        const val PAD_TEXT = ONE
        const val PAD_ICON = TWO
        const val EMPTY_LABEL = EMPTY_STRING
    }

    object NumPad {
        const val NUM_ZERO = ZERO
        const val NUM_ONE = ONE
        const val NUM_TWO = TWO
        const val NUM_THREE = THREE
        const val NUM_FOUR = FOUR
        const val NUM_FIVE = FIVE
        const val NUM_SIX = SIX
        const val NUM_SEVEN = SEVEN
        const val NUM_EIGHT = EIGHT
        const val NUM_NINE = NINE
    }

    object CasaTransactionDetail {
        const val MARKER = "###.0"
    }

    object CardStatus {
        const val STATUS_ACTIVE = "ACTIVE"
        const val STATUS_EXPIRED = "EXPIRED"
        const val STATUS_TEMPBLOCK = "TEMPBLOCK"
        const val STATUS_PIN_ATTEMPT_EXCEED = "PIN_ATTEMPT_EXCEEDED - *"
        const val STATUS_PERMBLOCK = "PERMBLOCK"
        const val STATUS_INACTIVE = "INACTIVE"
        const val STATUS_REPLACE = "REPLACE"
        const val STATUS_NOT_ACTIVE = "NOT_ACTIVE"
    }

    object CountDown {
        const val ONE_THOUSAND = 1000
    }

    object ALPHA {
        const val OPACITY_NORMAL = 1.0F
        const val OPACITY_ZERO = 0F
    }

    object Tnc {
        const val WEB_MAX_PROGRESS = 100
        const val WEB_FONT_ARIAL = "arial"
        const val WEB_FONT_REGULAR = "tt_interphases_regular"
    }

    object Dialog {
        const val TAG_DIALOG = "dialog"
    }

    object Calender {
        const val DAY_OF_YEAR = 365
        const val FIRST_MONTH = 1
        const val END_MONTH = 11
        const val LAST_MONTH = 12
        const val EVEN_MONTH = 30
        const val ODD_MONTH = 31
        const val LEAP_MONTH = 28
        const val MIN_SWIPE_DISTANCE = 60
        const val SWIPE_OF_MAX_PATH = 150
        const val SWIPE_THRESHOLD_VELOCITY = 100
        const val THE_YEAR = 3
        const val THE_MONTH = 2
        const val THIRD_MONTH = 3
        const val SECOND_MONTH = 2
        const val WHITE_LABEL = "WHITE"
        const val BLACK_LABEL = "BLACK"
        const val GREY_LABEL = "GREY"
        const val FIRST_MONTH_DATE = 0
        const val INIT_DATE = 1
        const val DATE_TEMPLATE_MONTH_CHOOSER = "MMMM yyyy"
        const val DATE_TEMPLATE_GRID = "dd MMM yyyy"
        const val FULL_DATE_FORMAT = "dd MMMM yyyy"
        const val DATE_FORMAT = "dd/MM/yyyy"
        const val DATE_FORMAT_OPENED = "yyyyMMdd"
        const val DATE_TEMPLATE = "d-M-yyyy"
        const val DATE_TEMPLATE_FORMAT = "yyyy-MM-dd"
        const val DATE_FORMAT_WITH_MINUTE = "dd MMM yyyy HH:mm"
        const val DATE_FORMAT_WITH_SECOND = "dd MMM yyyy HH:mm:ss"
        const val DATE_ONLY_TEMPLATE_FORMAT = "dd-MM-yyyy"
        const val DATE_FORMAT_LOAN_HISTORY = "ddMMyy"
        const val DAY_OFFSET = 1
        const val HYPEN = "-"
        const val DES = "12"
        const val NOV = "11"
        const val OKT = "10"
        const val SEPT = "09"
        const val AGU = "08"
        const val JUL = "07"
        const val JUN = "06"
        const val MAY = "05"
        const val APR = "04"
        const val MAR = "03"
        const val FEB = "02"
        const val JAN = "01"
        const val MINGGU = " M"
        const val SABTU = "S"
        const val JUMAT = "J"
        const val KAMIS = "K"
        const val RABU = "R"
        const val SELASA = "S"
        const val SENIN = "S"
        const val LANGUAGE_ID_ID = "id-ID"
        const val LANGUAGE_ID = "id"
        const val IMG_JPG = ".jpg"
        const val SWIPE_MIN_DISTANCE = 60
        const val SWIPE_MAX_OF_PATH = 150
        const val DATE_TEMPLATE_HEADER = "EEEE, dd MMMM yyyy"
        const val UNTIL = "Sampai"
    }

    object Compress {
        const val COMPRESS_QUALITY = 75
    }

    object LovNonFinOldBds {
        const val LOV_CURRENT_ACCOUNT_SERVICE_LIST = "casa.service.list"
        const val LOV_ECHANNEL_SERVICE_LIST = "echannel.service.list"
        const val LOV_DEBIT_CARD_COMPLAINT_LIST = "debit.card.service.list"
        const val LOV_DEPOSITO_SERVICE_LIST = "deposito.service.list"
        const val LOV_OTHERS_SERVICE_LIST = "others.service.list"
        const val LOV_CUSTOMER_COMPLAINT_LIST = "customer.complaint.list"
    }

    const val MAX_AMOUNT_COMPANY_REPLACE = "__maximumWarkatAmountCompany__"
}
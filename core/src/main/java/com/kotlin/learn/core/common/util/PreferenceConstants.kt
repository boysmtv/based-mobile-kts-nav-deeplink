/*
 * Copyright © 2020 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.common.util

class PreferenceConstants {

    object Auth {
        const val PREF_KEY_AUTH = "prefKeyAuth"
        const val PREF_KEY_TOKEN = "prefKeyToken"
        const val PREF_KEY_USER_ID = "prefKeyUserId"
        const val PREF_KEY_USER_TYPE = "prefKeyUserType"
        const val PREF_KEY_REFRESH_TOKEN = "refreshToken"
        const val PREF_KEY_GB_LOGIN_TYPE = "gbLoginType"
        const val PREF_KEY_REFRESH_TOKEN_TIMESTAMP = "refreshTokenTimestamp"
        const val PREF_KEY_IDLE_TIME_STAMP = "idleTimeStamp"
        const val PREF_KEY_APP_IDLE_TIME = "application.idle.time"
        const val PREF_KEY_APP_IDLE_TIME_CUSTOMER = "customer.application.idle.time"
    }

    object Transaction {
        const val PREF_KEY_BRANCH_NUMBER = "prefKeyBranchNumber"
        const val PREF_KEY_SIZE_TRANSACTION = "prefKeySizeTransaction"
        const val PREF_KEY_OVERVIEW_TRANSACTION = "prefKeyOverviewTransaction"
        const val PREF_KEY_OVERVIEW_TRANSACTION_SELECTED = "prefKeyOverviewTransactionSelected"
        const val PREF_KEY_LAST_SELECTED_TRANSACTION = "prefKeyLastSelectedTransaction"
        const val PREF_KEY_SAVE_DONE_TRANSACTION = "prefKeySaveDoneTransaction"
        const val PREF_KEY_CUSTOMER_CONFIRMATION = "prefKeyCustomerConfirmation"
        const val PREF_KEY_SPV_CONFIRMATION = "prefKeySpvConData"
        const val PREF_KEY_SELECTED_DEBIT_CARD = "prefKeySelectedDebitCard"
        const val PREF_KEY_EDITED_TRANSACTION = "prefKeyEditedTransaction"
        const val PREF_KEY_EDITED_TEMPORARY_TRANSACTION = "prefKeyEditedTemporaryTransaction"
        const val PREF_KEY_EDIT_TYPE = "prefKeyEditType"
        const val PREF_KEY_SUBMIT_TRANSACTION_FAIL = "prefKeySubmitTransactionFail"
        const val PREF_KEY_SUBMIT_TRANSACTION_SUCCESS = "prefKeySubmitTransactionSuccess"
        const val PREF_KEY_FULLNAME = "prefKeyFullname"
        const val PREF_KEY_AMOUNT_WITHDRAWAL = "prefKeyAmountWithdrawal"
        const val PREF_KEY_TRANSACTION_ID = "prefKeyTransactionId"
        const val PREF_KEY_SCENARIO = "prefKeyScenario"
        const val PREF_KEY_DIALOG_STATE = "prefKeyDialogState"
        const val PREF_KEY_SEND_RECEIPT = "prefKeySendReceipt"
        const val PREF_KEY_SCENARIO_SUBMIT = "prefKeyScenarioSubmit"
        const val PREF_KEY_DETAIL_TRANSACTION = "prefKeyDetailTransaction"
        const val PREF_KEY_DETAIL_CARD_STATUS = "prefKeyCardStatus"
        const val PREF_KEY_SKP_FAILED_REASON = "prefKeySkpFailedReason"
        const val PREF_KEY_CEK_PHOTO_FRONT = "document.type.cek.photo.front"
        const val PREF_KEY_CEK_PHOTO_BACK = "document.type.cek.photo.back"
        const val PREF_KEY_STRING = "prefKeyString"
        const val PREF_KEY_IS_CARD_ELIGIBLE = "isCardEligible"
        const val PREF_KEY_INSUFFICIENT_DIALOG = "prefKeyInsufficientBalance"
        const val PREF_KEY_CANCEL_REASON = "prefKeyCancelReason"
    }


    object CustomerDataMaintenance {
        const val PREF_KEY_CATEGORY_CUSTOMER_DATA = "prefKeyCategoryCustomerData"
        const val PREF_KEY_CUSTOMER_DATA_MAINTENANCE_DETAILS = "prefKeyCDMDetails"
    }

    object CustomerInformation {
        const val PREF_KEY_CUSTOMER_DATA_DASHBOARD_ACCESSOR = "prefKeyCustomerDataDashboardAccessor"
    }

    object Dashboard {
        const val PREF_KEY_RESERVATION_ID = "prefKeyReservationId"
        const val PREF_KEY_RESERVATION_ASSIGNED = "prefKeyReservatioAssigned"
        const val PREF_KEY_SELECTED_CATEGORY = "prefKeySelectedCategory"
    }

    object Language {
        const val PREF_KEY_LANGUAGE_PACK_ID = "languagePackId"
        const val PREF_KEY_LANGUAGE_PACK = "languagePack"
        const val SHARED_PREF_LANGUAGE = "preferredLanguage"
    }

    object Splash {
        const val PREF_KEY_APP_CONFIG = "prefKeyAppConfig"
        const val PREF_KEY_IP_APP = "prefKeyIPApp"
    }

    object AppConfig {
        const val PREF_KEY_TRANSITION_TIME = "device.transition.time"
        const val PREF_KEY_INTERVAL_OTP = "otp.resend.interval"
        const val PREF_KEY_RSA_KEY = "rsa.public.key"
        const val PREF_KEY_LOV = "prefKeyLov"
        const val PREF_KEY_REGEX_ACCOUNT_NUMBER = "account.number.regex"
        const val PREF_KEY_HSM_PIN_PUBLIC_KEY = "hsm.pin.publicKey"
        const val PREF_KEY_HSM_PIN_EXPONENT = "hsm.pin.exponent"
        const val PREF_KEY_MAX_INPUTTED_DIGIT_AMOUNT = "max.inputted.digit.amount"
        const val PREF_KEY_MAX_INPUTTED_DIGIT_INCOME = "max.inputted.income.length"
        const val PREF_KEY_MAX_INCOME_WITHOUT_NPWP = "max.income.without.npwp"
        const val PREF_KEY_DEBIT_REISSUE_PIN = "debit.reissue.pin"
        const val PREF_KEY_DUKCAPIL_LINK = "dukcapil.webportal.link"
        const val PREF_KEY_BRANCH_MAINTENANCE_LIMIT = "branch.maintenance.limit"
        const val PREF_KEY_TRANSACTION_RTGS_MINIMUM_VALUE = "transaction.rtgs.minimum"
        const val PREF_KEY_TRANSACTION_OVERBOOKING_MINIMUM_VALUE = "overbooking.minimum.amount"
        const val PREF_KEY_TRANSACTION_BI_FAST_MINIMUM_VALUE = "bifast.minimum.amount"
        const val PREF_KEY_TRANSACTION_BI_FAST_MAXIMUM_VALUE = "bifast.maximum.amount"
        const val PREF_KEY_DEFAULT_CURRENCY = "default.currency"
        const val PREF_KEY_BI_FAST_EMAIL_DOMAIN = "bifast.email.domain"
        const val PREF_KEY_PROXY_PHONE_NUMBER_REGEX = "proxy.phone.number.regex"
        const val PREF_KEY_PROXY_EMAIL_REGEX = "email.regex"
        const val PREF_KEY_TRANSACTION_OVERBOOKING_MINIMUM_VALUE_AMOUNT = "overbooking.minimum.amount"
        const val PREF_KEY_MAX_UPLOAD_KK = "max.upload.kk"
        const val PREF_KEY_MAX_UPLOAD_NPWP = "max.upload.npwp"
        const val PREF_KEY_MAX_UPLOAD_EVIDENCE_COMMUNICATION = "max.upload.evidence.communication"
        const val PREF_KEY_MAX_UPLOAD_EVIDENCE_TRANSACTION = "max.upload.evidence.transaction"
        const val PREF_KEY_MAX_UPLOAD_POLICE_REPORT = "max.upload.police report"
        const val PREF_KEY_MAX_UPLOAD_STAMPED_DECLARATION_REPORT = "max.upload.stamped.declaration.report"
        const val PREF_KEY_MAX_UPLOAD_OTHERS_DOCUMENTS = "max.upload.others.documents"
        const val PREF_KEY_WARKAT_MAXIMUM_ATTEMPT = "maximum.warkat.attempt"
    }

    object LovKey {
        const val PREF_KEY_FILTER_LIST = "transaction.filter.list"
        const val PREF_KEY_STATUS_TRANSACTION = "transaction.status.list"
        const val PREF_KEY_TRANSACTION_PURPOSE = "branch.transaction.purpose.list"
        const val PREF_KEY_NON_INDIVIDUAL_TRANSACTION_PURPOSE = "non.per.trx.transaction.purpose.list"
        const val PREF_KEY_TRANSACTION_PURPOSE_BI_FAST = "bifast.purpose.list"
        const val PREF_KEY_TRANSACTION_SOURCE_FEE = "transaction.fee.list"
        const val PREF_KEY_TRANSACTION_SOURCE_FUND = "branch.source.fund.list"
        const val PREF_KEY_NON_INDIVIDUAL_TRANSACTION_SOURCE_FUND = "non.per.trx.source.fund.list"
        const val PREF_KEY_REGEX_PREFIX_WARKAT = "prefix.warkat.validation"
        const val PREF_KEY_REGEX_NUMBER_WARKAT = "warkat.number.validation"
        const val PREF_KEY_TRANSFER_BI_FAST_VALIDATION = "bifast.beneficiary.validation.type"
        const val PREF_KEY_REGEX_CUSTOMER_MAINTENANCE_PHONE_NUMBER = "cust.maintenance.mobile.number.regex"
        const val PREF_KEY_REGEX_CUSTOMER_MAINTENANCE_HOME_PHONE_NUMBER = "cust.maintenance.home.number.regex"
        const val PREF_KEY_REGEX_CUSTOMER_MAINTENANCE_EMAIL = "cust.maintenance.email.regex"
        const val PREF_KEY_REGEX_BIRTH_PLACE = "host.birth.place.regex.validator"
        const val PREF_KEY_REGEX_MOTHER_NAME = "host.mother.name.regex.validator"
        const val PREF_KEY_REGEX_TITLE_NAME = "host.title.regex.validator"
        const val PREF_KEY_REGEX_REPORTING_NAME = "host.name.regex.validator"
        const val PREF_KEY_AM_DENUNCIATION_TRANSACTION_PURPOSE = "am.denunciation.list"
        const val PREF_KEY_AM_ACTIVATION_TRANSACTION_PURPOSE = "am.activation.purpose.list"
        const val PREF_KEY_AM_BLOCK_TRANSACTION_PURPOSE = "am.blocked.purpose.list"
        const val PREF_KEY_AM_RK_STATEMENT_PURPOSE= "am.period.range.statement"
        const val PREF_KEY_CUSTOMER_DECLINED_INTERBANK_REASON = "customer.declined.interbank.reason.list"
        const val PREF_KEY_CUSTOMER_DECLINED_TRANSFER_REASON = "customer.declined.intrabank.reason.list"
        const val PREF_KEY_NON_PRIORITY_SWITCHER_BANK = "non.priority.switcher"
        const val PREF_KEY_CUSTOMER_DECLINED_WITHDRAWAL_REASON = "customer.declined.withdrawal.reason.list"
        const val PREF_KEY_CUSTOMER_DECLINED_COMPANY_WITHDRAWAL_REASON = "company.declined.transaction.reason.list"
        const val PREF_KEY_CUSTOMER_DECLINED_COMPANY_DEPOSIT_REASON = "company.declined.deposit.reason.list"
        const val PREF_KEY_CUSTOMER_DECLINED_COMPANY_OVERBOOKING_REASON = "company.declined.overbooking.reason.list"
        const val PREF_KEY_CASH_WITHDRAWAL_MIN_FEE= "min.fee.withdrawal.amount"
        const val PREF_KEY_CASH_WITHDRAWAL_MAX_COMPANY_EDIT_AMOUNT="max.warkat.company.edit.amount"
    }

    object Config {
        const val PREF_KEY_APP_VERSION = "app.version"
    }
}
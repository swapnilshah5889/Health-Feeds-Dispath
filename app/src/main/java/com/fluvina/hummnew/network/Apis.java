package com.fluvina.hummnew.network;

public class Apis {

//    public static final String BASE_URL = "https://www.mobihealth.in/";
//    public static final String BASE_URL = "https://dev.mobihealth.in/";
    public static final String BASE_URL = "http://rc.mobihealth.in/";
//    public static final String BASE_URL = "http://app.mobihealth.in/";

    public static final String APP_VERSION_API = "mobile_url/AppVersion.php";
    public static final String LOGIN_API = "mobile_url/logincheckmobile.php?shareRegId=true";
    public static final String QR_LOGIN_API = "doctor/qr_gen/qr_fun.php";
    public static final String CHECK_NUMBER_API = "checkNum.php";
    public static final String VERIFY_NUMBER_API = "VerifyNum.php";
    public static final String SIGNUP_API = "mobile_url/register_user.php";
    public static final String FORGOT_PASS_API = "includes/forgot_backend.php";
    public static final String CHANGE_PASS_API = "user/setting/change_password/backend_change_password.php";
    public static final String CHANGE_MOBILE_API = "doctor/prescription/ep_functions.php";
    public static final String LOGOUT_API = "mobile_url/logout_mobile.php";
    public static final String HUMM_FEED_API = "mobile_url/health_feed_dispatch/HealthFeedDispatch.php";
    public static final String SAVE_HUMM_FEED_ANSWER_API = "mobile_url/health_feed_dispatch/HealthFeedSurveyResult.php";
    public static final String SAVE_HUMM_MED_CONFIRMATION_API = "mobile_url/health_feed_dispatch/MedicineConfirmationSurveyResult.php";
    public static final String SAVE_HUMM_FEED_READ_API = "mobile_url/health_feed_dispatch/HealthFeedSurveyResult.php";
    public static final String MY_HEALTH_API = "mobile_url/mobile_sync/sync_master.php";
    public static final String DOCTOR_LIST_API = "dr_listing_json.php";
    public static final String VIEW_PDF_API = "mobile_url/mobile_sync/user_appointments_all.php";
    public static final String EP_FUNCTION_API = "doctor/prescription/ep_functions.php";
    public static final String PRESCRIPTION_API = "mobile_url/Prescription.php";
    public static final String CANCEL_APPOINTMENT_API = "doctor/appoinment/active/cancel_backend.php";
    public static final String REVIEW_APPOINTMENT_API = "user/my_appointments/completed_appointments/ratting_backend.php";
    public static final String RESCHEDULE_APPOINTMENT_API = "doctor/appoinment/active/reschedule_backend.php";
    public static final String APPOINTMENT_TIME_SLOTS_API = "mobile_url/appointment_slot.php";
    public static final String BOOK_APPOINTMENT_API = "front_appoinment__backend.php";
    public static final String GET_USER_SETTING_API = "doctor/appoinment/get_user_settings.php";
    public static final String APPOINTMENT_API = "mobile_url/appointment/Appointment.php";
    public static final String CHAT_MESSAGE_API = "wallet/billfun.php";
    public static final String CHAT_EMOJIS = "admin/upload_emoji/emoji_upld_fun.php";
    public static final String FILE_UPLOAD_API = "doctor/new_patients/file_upload_backend.php";

    public static final String Save_LOGIN_PROFILE_DATA = "user/profile/UserInfo/UserProfile.php";
    public static final String GetAllDisease_data = "mobile_url/appointment/Disease.php";
    public static final String UPDATE_PROFILE_IMAGE_API = "user/profile/UserInfo/image_uploader.php";
    public static final String AdhController = "mobile_url/mobile_sync/adherence_controller.php";
    public static final String BillFunction = "wallet/billfun.php";
    public static final String PPFunUrl = "doctor/ob_param/pp_fun.php";
    public static final String EPFunctionUrl = "doctor/prescription/ep_functions.php";
    public static final String changeinapppassword = "user/setting/change_password/backend_change_password.php";
    public static final String ClinicalInfoUrl = "doctor/ob_param/pp_fun.php";
    public static final String ClinicalInfoSubmitUrl = "doctor/prescription/ep_functions.php";
    public static final String updatesettingurl = "doctor/setting/setting_backend.php";
    public static final String PILLBOX_API = "mobile_url/mobile_sync/user_appointments_all_function.php";
    public static final String SETTING_API = "mobile_url/mobile_sync/user_appointments_all_function.php";
    public static final String GetNotification_data = "notification/Notifications.php";
    public static final String ReadNotification_data = "notification/ReadNotifications.php";
    public static final String feedback_url = "feedback_backend.php";
    public static final String getAllMedType = "pharmacy/pharma_fun.php";
    public static final String getMedicineSearched = "doctor/nep/med_dbv21.php";
    public static final String caretakerrequesturl = "user/profile/be_caretaker/be_caretaker_backend.php";
    public static final String removeCareTakerurl = "user/profile/be_caretaker/accept_or_reject_caretaker_request.php";
    public static final String MyCalendar_data = "mobile_url/health_machine/health_machine_reg.php";
    public static final String viewmydietreporturl = "mobile_url/mobile_sync/user_appointments_all.php";
    public static final String sync_masterurl = "mobile_url/mobile_sync/sync_master.php";
    public static final String tele_appointmnet_question = "controllers/TeleMedicineQuestions.php";
    public static final String tele_payment_setting = "controllers/AppointmentPaymentController.php";
    public static final String HUMM_FEEDS_READ_API = "mobile_url/health_feed/health_feed_app_view.php";
    public static final String LAB_REPORTS_API = "doctor/nep/nep_fun.php";
    public static final String HPAPIURL = "mobile_url/mh_apis.php";
    public static final String VIDEO_CALL_API = "controllers/videoConference.php";
    public static final String telemedicine_agora_api = "api-telemedicine";
    public static final String patient_api = "api-patient";
    public static final String HUBSPOT_API = "hubspot.php";
    public static final String HEALTH_API = "controllers/MyHealthUserController.php";
    public static final String APPOINTMENTS_API = "controllers/BookAppointment.php";
    public static final String DOCTOR_SEARCH_API = "controllers/DoctorSearchController.php";

}

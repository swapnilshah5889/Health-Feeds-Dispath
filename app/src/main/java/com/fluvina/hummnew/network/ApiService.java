package com.fluvina.hummnew.network;


import com.fluvina.hummnew.Model.AppVersionModel;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST(Apis.APP_VERSION_API)
    Call<AppVersionModel> callVersionCheckApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.LOGIN_API)
    Call<ResponseBody> callLoginApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.CHECK_NUMBER_API)
    Call<ResponseBody> callVerifyNumberApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.VERIFY_NUMBER_API)
    Call<ResponseBody> callGenerateOTPApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.VERIFY_NUMBER_API)
    Call<ResponseBody> callVerifyOtpApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.SIGNUP_API)
    Call<ResponseBody> callSignUpApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.FORGOT_PASS_API)
    Call<ResponseBody> callForgetPasswordResetApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.CHANGE_PASS_API)
    Call<ResponseBody> ChangePasswordApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.CHECK_NUMBER_API)
    Call<ResponseBody> CheckNumber(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.VERIFY_NUMBER_API)
    Call<ResponseBody> VerifyNumber(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.VERIFY_NUMBER_API)
    Call<ResponseBody> VerifyOTP(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(Apis.CHANGE_MOBILE_API)
    Call<ResponseBody> ChangeMobileNo(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(Apis.LOGOUT_API)
    Call<ResponseBody> callLogoutApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.Save_LOGIN_PROFILE_DATA)
    Call<ResponseBody> Save_LOGIN_PROFILE_DATA(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.GetAllDisease_data)
    Call<ResponseBody> GetAllDisease_data(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.HUMM_FEED_API)
    Call<ResponseBody> callHummFeedApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.SAVE_HUMM_FEED_ANSWER_API)
    Call<ResponseBody> callSaveSurveyAnswerApi(@FieldMap Map<String, String> hashMap);


    @FormUrlEncoded
    @POST(Apis.SAVE_HUMM_MED_CONFIRMATION_API)
    Call<ResponseBody> callMedConfirmationApi(@FieldMap Map<String, String> hashMap);


    @FormUrlEncoded
    @POST(Apis.SAVE_HUMM_FEED_READ_API)
    Call<ResponseBody> callReadFeedIdsApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.AdhController)
    Call<ResponseBody> AdhController(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.BillFunction)
    Call<ResponseBody> BillFunction(@FieldMap Map<String, String> hashMap);

    @Multipart
    @POST(Apis.UPDATE_PROFILE_IMAGE_API)
    Call<ResponseBody> UPDATE_PROFILE_IMAGE_API(@Part MultipartBody.Part image,
                                                @PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST(Apis.PPFunUrl)
    Call<ResponseBody> PPFunUrl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.EPFunctionUrl)
    Call<ResponseBody> EPFunctionUrl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.changeinapppassword)
    Call<ResponseBody> changeinapppassword(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.ClinicalInfoUrl)
    Call<ResponseBody> ClinicalInfoUrl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.ClinicalInfoSubmitUrl)
    Call<ResponseBody> ClinicalInfoSubmitUrl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.updatesettingurl)
    Call<ResponseBody> updatesettingurl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.PILLBOX_API)
    Call<ResponseBody> callPillBoxApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.SETTING_API)
    Call<ResponseBody> callSettingApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.GetNotification_data)
    Call<ResponseBody> GetNotification_data(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.ReadNotification_data)
    Call<ResponseBody> ReadNotification_data(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.feedback_url)
    Call<ResponseBody> feedback_url(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.getAllMedType)
    Call<ResponseBody> getAllMedType(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.getMedicineSearched)
    Call<ResponseBody> getMedicineSearched(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.QR_LOGIN_API)
    Call<ResponseBody> callQRLoginApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.caretakerrequesturl)
    Call<ResponseBody> caretakerrequesturl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.MyCalendar_data)
    Call<ResponseBody> MyCalendar_data(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.removeCareTakerurl)
    Call<ResponseBody> removeCareTakerurl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.MY_HEALTH_API)
    Call<ResponseBody> callMyHealthApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.DOCTOR_LIST_API)
    Call<ResponseBody> callDoctorListApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.VIEW_PDF_API)
    Call<ResponseBody> callViewPdfApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.EP_FUNCTION_API)
    Call<ResponseBody> callPrescriptionApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.viewmydietreporturl)
    Call<ResponseBody> viewmydietreporturl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.CANCEL_APPOINTMENT_API)
    Call<ResponseBody> callCancelAppointmentApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.REVIEW_APPOINTMENT_API)
    Call<ResponseBody> callReviewAppointmentApi(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.RESCHEDULE_APPOINTMENT_API)
    Call<ResponseBody> callRescheduleAppointmentApi(@FieldMap Map<String, String> hashMap);


    @FormUrlEncoded
    @POST(Apis.sync_masterurl)
    Call<ResponseBody> sync_masterurl(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.BOOK_APPOINTMENT_API)
    Call<ResponseBody> callBookAppointmentApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.GET_USER_SETTING_API)
    Call<ResponseBody> callUserSettingApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.APPOINTMENT_API)
    Call<ResponseBody> callAppointmentApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.CHAT_MESSAGE_API)
    Call<ResponseBody> callChatApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.CHAT_MESSAGE_API)
    Call<ResponseBody> callSendChatMessageApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.CHAT_MESSAGE_API)
    Call<ResponseBody> callDeleteChatMessageApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.CHAT_EMOJIS)
    Call<ResponseBody> callChatEmojiApi(@FieldMap Map<String, String> params);

    @Multipart
    @POST(Apis.FILE_UPLOAD_API)
    Call<ResponseBody> callFileUploadApi(@Part MultipartBody.Part image,
                                         @PartMap Map<String, RequestBody> params);




    @FormUrlEncoded
    @POST(Apis.tele_appointmnet_question)
    Call<ResponseBody> callTeleAnswersApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.HUMM_FEEDS_READ_API)
    Call<ResponseBody> HUMM_FEEDS_READ_API(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.tele_payment_setting)
    Call<ResponseBody> callSavePaymentResponseApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.LAB_REPORTS_API)
    Call<ResponseBody> callLabReportsApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.HPAPIURL)
    Call<ResponseBody> HPAPIURL(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(Apis.telemedicine_agora_api)
    Call<ResponseBody> callVideoCallApi(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Apis.patient_api)
    Call<ResponseBody> callPatientApi(@FieldMap Map<String, String> params);

    @Multipart
    @POST(Apis.patient_api)
    Call<ResponseBody> callUploadLabReport(@Part MultipartBody.Part[] surveyImage,
                                                 @PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST(Apis.HUBSPOT_API)
    Call<ResponseBody> callContactUsApi(@FieldMap LinkedHashMap<String, String> params);

    @FormUrlEncoded
    @POST(Apis.HEALTH_API)
    Call<ResponseBody> callHealthApi(@FieldMap LinkedHashMap<String, String> params);

    @FormUrlEncoded
    @POST(Apis.APPOINTMENTS_API)
    Call<ResponseBody> APPOINTMENTS_API(@FieldMap Map<String, String> hashMap);

    @FormUrlEncoded
    @POST(Apis.DOCTOR_SEARCH_API)
    Call<ResponseBody> DOCTOR_SEARCH_API(@FieldMap Map<String, String> hashMap);

}

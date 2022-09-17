package com.honeybunch.app.interfaces;

import android.net.Uri;

import com.honeybunch.app.models.AppCloseModel;
import com.honeybunch.app.models.AppOpenModel;
import com.honeybunch.app.models.BlockUserListModels;
import com.honeybunch.app.models.BlockUserModels;
import com.honeybunch.app.models.DeleteChatModel;
import com.honeybunch.app.models.InviteAppModel;
import com.honeybunch.app.models.RefusedUserListModels;
import com.honeybunch.app.models.ReloadDataModels;
import com.honeybunch.app.models.SkipListModel;
import com.honeybunch.app.models.UpdateAboutModel;
import com.honeybunch.app.models.UpdateProfileImageModels;
import com.honeybunch.app.models.UserProfileDataInterestModels;
import com.honeybunch.app.models.UserProfileDataModels;
import com.honeybunch.app.models.UserProfileModel;
import com.honeybunch.app.models.HomelistProfilesModels;
import com.honeybunch.app.models.ListLikeModels;
import com.honeybunch.app.models.ListMatchModels;
import com.honeybunch.app.models.ListMylikesModels;
import com.honeybunch.app.models.ListViewModels;
import com.honeybunch.app.models.MessageChatModels;
import com.honeybunch.app.models.OnetoOneChatModel;
import com.honeybunch.app.models.RightSwipeModels;
import com.honeybunch.app.models.SignUpModel;
import com.honeybunch.app.models.StartChatModel;
import com.honeybunch.app.models.ViewProfileModels;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("userSignup")
    Call<SignUpModel> userSignUp(@Field("deviceType") String deviceType,
                                 @Field("deviceId") String deviceId,
                                 @Field("deviceName") String deviceName,
                                 @Field("socialType") String socialType,
                                 @Field("socialId") String socialId,
                                 @Field("socialEmail") String socialEmail,
                                 @Field("socialName") String socialName,
                                 @Field("socialImgurl") Uri socialImgurl,
                                 @Field("advertisingId") String advertisingId,
                                 @Field("versionName") String versionName,
                                 @Field("versionCode") int versionCode,
                                 @Field("fcmToken") String token,
                                 @Field("utmSource") String utmSource,
                                 @Field("utmMedium") String utmMedium,
                                 @Field("referrerUrl") String referrerUrl,
                                 @Field("socialToken") String socialToken);

    @FormUrlEncoded
    @POST("listProfiles")
    Call<HomelistProfilesModels> getHomeDataProfile(@Field("userId") int userId,
                                                    @Field("securityToken") String securityToken,
                                                    @Field("versionName") String versionName,
                                                    @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("reloadList")
    Call<ReloadDataModels> getHomeDataReload(@Field("userId") int userId,
                                             @Field("securityToken") String securityToken,
                                             @Field("versionName") String versionName,
                                             @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("addMatch")
    Call<RightSwipeModels> rightswipe(@Field("userId") int userId,
                                      @Field("securityToken") String securityToken,
                                      @Field("versionName") String versionName,
                                      @Field("versionCode") int versionCode,
                                      @Field("matchId") int matchId);
    @FormUrlEncoded
    @POST("skipMatch")
    Call<RightSwipeModels> leftswipe(@Field("userId") int userId,
                                     @Field("securityToken") String securityToken,
                                     @Field("versionName") String versionName,
                                     @Field("versionCode") int versionCode,
                                     @Field("skipId") int matchId);
    @FormUrlEncoded
    @POST("appOpen")
    Call<AppOpenModel> appOpen(@Field("userId") int userId,
                               @Field("securityToken") String securityToken,
                               @Field("versionName") String versionName,
                               @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("appClose")
    Call<AppCloseModel> appClose(@Field("userId") int userId,
                                 @Field("securityToken") String securityToken,
                                 @Field("versionName") String versionName,
                                 @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("addUserDetail")
    Call<UserProfileModel> addUserProfileData(@Field("userId") int userId,
                                              @Field("securityToken") String securityToken,
                                              @Field("versionName") String versionName,
                                              @Field("versionCode") int versionCode,
                                              @Field("fullName") String userName,
                                              @Field("mobileNumber") String mobileNumber,
                                              @Field("gender") String gender,
                                              @Field("location") String location,
                                              @Field("age") String birthDate,
                                              @Field("bioText") String aboutme);
    @FormUrlEncoded
    @POST("viewProfile")
    Call<ViewProfileModels> viewProfile(@Field("userId") int userId,
                                        @Field("securityToken") String securityToken,
                                        @Field("versionName") String versionName,
                                        @Field("versionCode") int versionCode,
                                        @Field("viewProfileId") long viewProfileId);
    @FormUrlEncoded
    @POST("listViewed")
    Call<ListViewModels> listViewd(@Field("userId") int userId,
                                   @Field("securityToken") String securityToken,
                                   @Field("versionName") String versionName,
                                   @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("listMatches")
    Call<ListMatchModels> listMatch(@Field("userId") int userId,
                                    @Field("securityToken") String securityToken,
                                    @Field("versionName") String versionName,
                                    @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("listLikeds")
    Call<ListLikeModels> listLike(@Field("userId") int userId,
                                  @Field("securityToken") String securityToken,
                                  @Field("versionName") String versionName,
                                  @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("myLikes")
    Call<ListMylikesModels> listMyLikes(@Field("userId") int userId,
                                        @Field("securityToken") String securityToken,
                                        @Field("versionName") String versionName,
                                        @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("listChat")
    Call<MessageChatModels> getMessageList(@Field("userId") int userId,
                                           @Field("securityToken") String securityToken,
                                           @Field("versionName") String versionName,
                                           @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("viewChat")
    Call<OnetoOneChatModel> viewChat(@Field("userId") int userId,
                                     @Field("securityToken") String securityToken,
                                     @Field("versionName") String versionName,
                                     @Field("versionCode") int versionCode,
                                     @Field("conversationId") int conversationId,
                                     @Field("actionType") String get);
    @FormUrlEncoded
    @POST("startChat")
    Call<StartChatModel> startChat(@Field("userId") int userId,
                                   @Field("securityToken") String securityToken,
                                   @Field("versionName") String versionName,
                                   @Field("versionCode") int versionCode,
                                   @Field("recipientId") long recipientId);


    @FormUrlEncoded
    @POST("viewChat")
    Call<OnetoOneChatModel> sendMessage(@Field("userId") int userId,
                                        @Field("securityToken") String securityToken,
                                        @Field("versionName") String versionName,
                                        @Field("versionCode") int versionCode,
                                        @Field("conversationId") int conversationId,
                                        @Field("actionType") String post,
                                        @Field("message") String message);

    @FormUrlEncoded
    @POST("blockUser")
    Call<BlockUserModels> blockUser(@Field("userId") int userId,
                                    @Field("securityToken") String securityToken,
                                    @Field("versionName") String versionName,
                                    @Field("versionCode") int versionCode,
                                    @Field("blockedId") long blockedId);

    @FormUrlEncoded
    @POST("reportUser")
    Call<BlockUserModels> reportUser(@Field("userId") int userId,
                                     @Field("securityToken") String securityToken,
                                     @Field("versionName") String versionName,
                                     @Field("versionCode") int versionCode,
                                     @Field("reportedId") long reportedId,
                                     @Field("note") String note);

    @FormUrlEncoded
    @POST("listBlocked")
    Call<BlockUserListModels> blockUserList(@Field("userId") int userId,
                                            @Field("securityToken") String securityToken,
                                            @Field("versionName") String versionName,
                                            @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("inviteApp")
    Call<InviteAppModel> inviteData(@Field("userId") int userId,
                                    @Field("securityToken") String securityToken,
                                    @Field("versionName") String versionName,
                                    @Field("versionCode") int versionCode);


    @FormUrlEncoded
    @POST("listRefused")
    Call<RefusedUserListModels> refusedList(@Field("userId") int userId,
                                            @Field("securityToken") String securityToken,
                                            @Field("versionName") String versionName,
                                            @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("listSkipped")
    Call<SkipListModel> skipList(@Field("userId") int userId,
                                 @Field("securityToken") String securityToken,
                                 @Field("versionName") String versionName,
                                 @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("editUserDetail")
    Call<UserProfileDataModels> getUserProfileData(@Field("userId") int userId,
                                                   @Field("securityToken") String securityToken,
                                                   @Field("versionName") String versionName,
                                                   @Field("versionCode") int versionCode,
                                                   @Field("actionType") String actionType);
    @FormUrlEncoded
    @POST("editUserDetail")
    Call<UserProfileDataModels> getUserProfileUpdateData(@Field("userId") int userId,
                                                         @Field("securityToken") String securityToken,
                                                         @Field("versionName") String versionName,
                                                         @Field("versionCode") int versionCode,
                                                         @Field("actionType") String actionType,
                                                         @Field("fullName") String fullname,
                                                         @Field("gender") String gender,
                                                         @Field("age") String age,
                                                         @Field("location") String location,
                                                         @Field("education") String education,
                                                         @Field("profession") String profession);

    @FormUrlEncoded
    @POST("updateAbout")
    Call<UpdateAboutModel> updateAboutProfile(@Field("userId") int userId,
                                              @Field("securityToken") String securityToken,
                                              @Field("versionName") String versionName,
                                              @Field("versionCode") int versionCode,
                                              @Field("education") String education,
                                              @Field("profession") String profession,
                                              @Field("appearance") String appearance,
                                              @Field("purpose") List<String> purpose,
                                              @Field("interest") List<String> interest,
                                              @Field("actionType") String actionType);

    @FormUrlEncoded
    @POST("updateBio")
    Call<UpdateAboutModel> editupdateAboutProfile(@Field("userId") int userId,
                                              @Field("securityToken") String securityToken,
                                              @Field("versionName") String versionName,
                                              @Field("versionCode") int versionCode,
                                              @Field("actionType") String actionType,
                                              @Field("bioText") String bioText);

    @FormUrlEncoded
    @POST("updateAbout")
    Call<UpdateAboutModel> updateAboutProfile1(@Field("userId") int userId,
                                              @Field("securityToken") String securityToken,
                                              @Field("versionName") String versionName,
                                              @Field("versionCode") int versionCode,
                                              @Field("appearance") String appearance,
                                              @Field("actionType") String actionType);

    @FormUrlEncoded
    @POST("updateInterest")
    Call<UserProfileDataInterestModels> updateInterestProfile(@Field("userId") int userId,
                                                              @Field("securityToken") String securityToken,
                                                              @Field("versionName") String versionName,
                                                              @Field("versionCode") int versionCode,
                                                              @Field("interest") String interest,
                                                              @Field("actionType") String actionType);

    @FormUrlEncoded
    @POST("updatePurpose")
    Call<UserProfileModel> updatePurposeProfile(@Field("userId") int userId,
                                                @Field("securityToken") String securityToken,
                                                @Field("versionName") String versionName,
                                                @Field("versionCode") int versionCode,
                                                @Field("purpose") String interest,
                                                @Field("actionType") String actionType);


    @FormUrlEncoded
    @POST("updatePicture")
    Call<UpdateProfileImageModels> uploadPRofileImage1(@Field("userId") int userId,
                                                       @Field("securityToken") String securityToken,
                                                       @Field("versionName") String versionName,
                                                       @Field("versionCode") int versionCode,
                                                       @Body RequestBody files,
                                                       @Field("actionType") String actionType);

    @Multipart
    @POST("updatePicture")
    Call<UpdateProfileImageModels> uploadImage(@Part("userId") RequestBody userId,
                                               @Part("securityToken") RequestBody securityToken,
                                               @Part("versionName") RequestBody versionName,
                                               @Part("versionCode") RequestBody versionCode,
                                               @Part MultipartBody.Part image,
                                               @Part("actionType") RequestBody actionType);
    @Multipart
    @POST("updatePicture")
    Call<UpdateProfileImageModels> uploadProfileImage(@Part("userId") RequestBody userId,
                                               @Part("securityToken") RequestBody securityToken,
                                               @Part("versionName") RequestBody versionName,
                                               @Part("versionCode") RequestBody versionCode,
                                               @Part MultipartBody.Part image,
                                               @Part("status") RequestBody Status,
                                               @Part("actionType") RequestBody actionType);

    @Multipart
    @POST("updatePicture")
    Call<UpdateProfileImageModels> getProfileImage(@Part("userId") RequestBody userId,
                                                   @Part("securityToken") RequestBody securityToken,
                                                   @Part("versionName") RequestBody versionName,
                                                   @Part("versionCode") RequestBody versionCode,
                                                   @Part("actionType") RequestBody actionType);

    @FormUrlEncoded
    @POST("removePicture")
    Call<UpdateProfileImageModels> removeProfileImage(@Field("userId") int userId,
                                                      @Field("securityToken") String securityToken,
                                                      @Field("versionName") String versionName,
                                                      @Field("versionCode") int versionCode,
                                                      @Field("pictureId") int pictureId);

    @FormUrlEncoded
    @POST("updateMatch")
    Call<UserProfileModel> updateMatchProfile(@Field("userId") int userId,
                                                      @Field("securityToken") String securityToken,
                                                      @Field("versionName") String versionName,
                                                      @Field("versionCode") int versionCode,
                                                      @Field("matchId") int matchId,
                                                      @Field("status") String status);

    @FormUrlEncoded
    @POST("updateAppearence")
    Call<UpdateAboutModel> editupdateApreanceData(@Field("userId") int userId,
                                                  @Field("securityToken") String securityToken,
                                                  @Field("versionName") String versionName,
                                                  @Field("versionCode") int versionCode,
                                                  @Field("actionType") String actionType,
                                                  @Field("appearance") String appearance);

    @FormUrlEncoded
    @POST("updatePicture")
    Call<UpdateProfileImageModels> setUserImage(@Field("userId") int userId,
                                                @Field("securityToken") String securityToken,
                                                @Field("versionName") String versionName,
                                                @Field("versionCode") int versionCode,
                                                @Field("pictureId") int pictureId);


    @FormUrlEncoded
    @POST("deleteChat")
    Call<DeleteChatModel> deleteUserChat(@Field("userId") int userId,
                                         @Field("securityToken") String securityToken,
                                         @Field("versionName") String versionName,
                                         @Field("versionCode") int versionCode,
                                         @Field("conversationId") int conversationId);
}
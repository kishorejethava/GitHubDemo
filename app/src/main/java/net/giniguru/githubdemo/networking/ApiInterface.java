package net.giniguru.githubdemo.networking;

import net.giniguru.githubdemo.model.SingleUser;
import net.giniguru.githubdemo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KK on 11/13/2017.
 */

public interface ApiInterface {
    @GET("users")
    Call<List<User>> getUserList(@Query("since") int since);

    @GET("users/{username}")
    Call<SingleUser> getSingleUser(@Path("username") String username);
}

package net.giniguru.githubdemo.singleuser;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.giniguru.githubdemo.R;
import net.giniguru.githubdemo.blog.BlogActivity;
import net.giniguru.githubdemo.model.SingleUser;
import net.giniguru.githubdemo.networking.ApiClient;
import net.giniguru.githubdemo.networking.ApiInterface;
import net.giniguru.githubdemo.networking.NetworkStateReceiver;
import net.giniguru.githubdemo.util.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KK on 11/13/2017.
 */

public class SingleUserActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    private final String TAG = getClass().getName();

    @BindView(R.id.tvFollowersLabel)
    TextView tvFollowersLabel;

    @BindView(R.id.tvFollowers)
    TextView tvFollowers;

    @BindView(R.id.tvFollowingLabel)
    TextView tvFollowingLabel;

    @BindView(R.id.tvFollowing)
    TextView tvFollowing;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvCompany)
    TextView tvCompany;

    @BindView(R.id.tvLocation)
    TextView tvLocation;

    @BindView(R.id.tvPublicGist)
    TextView tvPublicGist;

    @BindView(R.id.tvRepo)
    TextView tvRepo;

    @BindView(R.id.tvBio)
    TextView tvBio;

    @BindView(R.id.ivSingleUser)
    CircleImageView ivSingleUser;

    @BindView(R.id.btnBlog)
    Button btnBlog;

    @BindView(R.id.llContainer)
    LinearLayout llContainer;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private SingleUser singleUser;
    private String userName;
    private NetworkStateReceiver networkStateReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);
        // bind the view using butterknife
        ButterKnife.bind(this);

        setActionBarTitle();
        setListener();
        initNetWorkStateReceiver();
        setCustomFont();

        if(NetworkUtil.isOnline(this)) {
            fetchSingleUser();
        }else {
            Toast.makeText(this, getString(R.string.internet_is_not_available), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * initialise and register network on/off listener
     */
    private void initNetWorkStateReceiver() {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * set title in action bar
     */
    private void setActionBarTitle() {
         userName = getIntent().getStringExtra("userName");
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(userName);
    }

    /**
     * set roboto custom font
     */
    private void setCustomFont() {
        Typeface latoRegular = Typeface.createFromAsset(getAssets(), getString(R.string.lato_regular));
        Typeface latoBold = Typeface.createFromAsset(getAssets(), getString(R.string.lato_bold));

        tvFollowing.setTypeface(latoBold);
        tvFollowers.setTypeface(latoBold);
        tvUserName.setTypeface(latoBold);
        tvCompany.setTypeface(latoRegular);
        tvLocation.setTypeface(latoRegular);
        tvBio.setTypeface(latoRegular);
        tvFollowersLabel.setTypeface(latoRegular);
        tvFollowingLabel.setTypeface(latoRegular);
        tvRepo.setTypeface(latoRegular);
        tvPublicGist.setTypeface(latoRegular);
        btnBlog.setTypeface(latoRegular);
    }

    /**
     * set listener
     */
    private void setListener() {
        btnBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(singleUser !=null) {
                    Intent intent = new Intent(SingleUserActivity.this, BlogActivity.class);
                    intent.putExtra("blog", singleUser.getBlog());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * fetch GitHub users
     */
    private void fetchSingleUser() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SingleUser> call = apiService.getSingleUser(userName);
        call.enqueue(new Callback<SingleUser>() {
            @Override
            public void onResponse(Call<SingleUser>call, Response<SingleUser> response) {
                progressBar.setVisibility(View.GONE);
                singleUser = response.body();
                bindSingleUser(singleUser);
            }

            @Override
            public void onFailure(Call<SingleUser>call, Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, t.toString());
            }
        });
    }

    /**
     * bind GitHub single user data to views
     * @param singleUser
     */
    private void bindSingleUser(SingleUser singleUser) {
        tvFollowingLabel.setVisibility(View.VISIBLE);
        tvFollowersLabel.setVisibility(View.VISIBLE);
        llContainer.setVisibility(View.VISIBLE);
        Glide.with(SingleUserActivity.this)
                .load(singleUser.getAvatar_url())
                .override(500,500)
                .into(ivSingleUser);
        tvUserName.setText(singleUser.getName());
        tvFollowers.setText(singleUser.getFollowers());
        tvFollowing.setText(singleUser.getFollowing());
        tvBio.setText(singleUser.getBio());
        tvCompany.setVisibility(singleUser.getCompany() == null ? View.GONE : View.VISIBLE);
        tvCompany.setText(singleUser.getCompany());
        tvLocation.setText(singleUser.getLocation());

        if(!singleUser.getPublic_gists().isEmpty())
        tvPublicGist.setText(getString(R.string.public_gist,singleUser.getPublic_gists()));

        if(!singleUser.getPublic_repos().isEmpty())
        tvRepo.setText(getString(R.string.public_repository,singleUser.getPublic_repos()));
    }

    @Override
    public void networkAvailable() {
        //fetch user when network available
        if(singleUser == null)
            fetchSingleUser();
    }

    @Override
    public void networkUnavailable() {

    }
    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }
}

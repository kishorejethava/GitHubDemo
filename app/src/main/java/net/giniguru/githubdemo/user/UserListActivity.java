package net.giniguru.githubdemo.user;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.giniguru.githubdemo.R;
import net.giniguru.githubdemo.model.User;
import net.giniguru.githubdemo.networking.ApiClient;
import net.giniguru.githubdemo.networking.ApiInterface;
import net.giniguru.githubdemo.networking.NetworkStateReceiver;
import net.giniguru.githubdemo.util.LibBitmap;
import net.giniguru.githubdemo.util.NetworkUtil;
import net.giniguru.githubdemo.util.PaginationScrollListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private UserAdapter userAdapter;
    private int LAST_SINCE=0;
    //prevent to call api from networkAvailable when app launch
    private boolean isFetchedUser;
    private boolean isLoading = false;
    private int TOTAL_PAGES = 3;
    private ProgressBar progressBar;
    private NetworkStateReceiver networkStateReceiver;

    public int getLAST_SINCE() {
        return LAST_SINCE;
    }

    public void setLAST_SINCE(int LAST_SINCE) {
        this.LAST_SINCE = LAST_SINCE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setActionBarTitle();
        init();
        initNetWorkStateReceiver();

        /**
         * get users from server if network available otherwise get from
         * local database
         */
        if(NetworkUtil.isOnline(this)) {
            UserDAO.clearAllUsers();
            String stringPath = getExternalFilesDir(null).getAbsolutePath()
                    + File.separator
                    + getString(R.string.app_name)
                    + File.separator
                    + getString(R.string.media);
            LibBitmap.deleteImageFile(new File(stringPath));
            fetchUserList();
        }else {
            Toast.makeText(this, getString(R.string.internet_is_not_available), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            ArrayList<User> users = UserDAO.getUserList();
            for(User user: users){
                Log.i(TAG,"local user id:"+user.getId());
            }
            userAdapter.addAll(users);

            //set last since id so when we get data from server it will start from that since id
            setLAST_SINCE(Integer.parseInt(users.get(users.size() - 1).getId()));
            isLoading = false;
        }
    }

    /**
     * Initialize component
     */
    private void init() {
        progressBar =  findViewById(R.id.main_progress);
        mLayoutManager = new LinearLayoutManager(this);
        userAdapter = new UserAdapter(UserListActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);

        paginationRecyclerView();
    }

    /**
     * set title in action bar
     */
    private void setActionBarTitle(){
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.github_users));
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
     * pagination for GitHub user listing
     */
    private void paginationRecyclerView() {
        recyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.i(TAG,"loadMoreItems");
                if(NetworkUtil.isOnline(UserListActivity.this)) {
                    isLoading = true;
                    // mocking network delay for API call
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchUserList();
                        }
                    }, 1000);
                }else{
                    Toast.makeText(UserListActivity.this, getString(R.string.internet_is_not_available), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    /**
     * fetch GitHub users
     */
    private void fetchUserList() {
        isFetchedUser=true;
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<User>> call = apiService.getUserList(getLAST_SINCE());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>>call, Response<List<User>> response) {
                if(getLAST_SINCE() > 0) {
                    userAdapter.removeLoadingFooter();
                    isLoading = false;
                }

                List<User> users = response.body();
                progressBar.setVisibility(View.GONE);
                userAdapter.addAll(users);
                //Add loading view at footer
                userAdapter.addLoadingFooter();
                setLAST_SINCE(Integer.parseInt(users.get(users.size() - 1).getId()));
            }

            @Override
            public void onFailure(Call<List<User>>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void networkAvailable() {
        //fetch users when network available
        if(getLAST_SINCE() == 0 && !isFetchedUser)
            fetchUserList();
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

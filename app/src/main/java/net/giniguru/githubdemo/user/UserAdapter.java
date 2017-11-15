package net.giniguru.githubdemo.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import net.giniguru.githubdemo.R;
import net.giniguru.githubdemo.singleuser.SingleUserActivity;
import net.giniguru.githubdemo.model.User;
import net.giniguru.githubdemo.util.LibBitmap;
import net.giniguru.githubdemo.util.NetworkUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by KK on 11/13/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private String TAG=getClass().getName();
    private Context mContext;
    private boolean isLoadingAdded = false;
    private Typeface latoBold;

    private List<User> userList;

    UserAdapter(Context context) {
        this.mContext = context;
        this.userList= new ArrayList<>();
        latoBold = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.lato_bold));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(v2);
                break;
        }
        return viewHolder;
    }
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.adapter_user, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final User user = userList.get(position);
        Log.i(TAG,"onBindViewHolder user id:"+user.getId());
        //Will show loader at last item when API will be called
        switch (getItemViewType(position)) {
            case ITEM:
                ((ViewHolder)holder).bindUser(user);
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView ivUser;
        TextView tvUserName;
        TextView tvUserId;
        ViewHolder(View view) {
            super(view);
            ivUser =  view.findViewById(R.id.ivUser);
            tvUserName = view.findViewById(R.id.tvUserName);
            tvUserId = view.findViewById(R.id.tvUserId);
            tvUserName.setTypeface(latoBold);
            tvUserId.setTypeface(latoBold);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,SingleUserActivity.class);
                    intent.putExtra("userName",tvUserName.getText().toString().trim());
                    mContext.startActivity(intent);

                }
            });
        }

        /**
         * Bind user data to views
         * @param user user
         */
        void bindUser(final User user) {
            tvUserId.setText(user.getId());
            tvUserName.setText(user.getLogin());

            if(!NetworkUtil.isOnline(mContext)) {
                //Show media from database
                if (user.getAvatar_url() != null) {
                    Log.i(TAG,"local avatar url:"+user.getAvatar_url());
                    Bitmap bitmap = LibBitmap.getBitmapImageFromSDCard(new File(user.getAvatar_url()));
                    Log.i(TAG,"bitmap"+bitmap);
                    ivUser.setImageBitmap(bitmap);
                }
            }else {
                ivUser.setDrawingCacheEnabled(true);
                Glide.with(mContext)
                        .load(user.getAvatar_url())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(200,200) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                final Bitmap bitmap = resource;
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivUser.setImageBitmap(bitmap);
                                    }
                                });

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String stringPath = mContext.getExternalFilesDir(null).getAbsolutePath()
                                                + File.separator
                                                + mContext.getString(R.string.app_name)
                                                + File.separator
                                                + mContext.getString(R.string.media);
                                        String localAvatarPath = LibBitmap.saveBitmapInSDCard(bitmap, stringPath, user.getId());
                                        //save bitmap image and other details in database
                                        UserDAO.insertUser(user.getId(), user.getLogin(), localAvatarPath, user.getAvatar_url());
                                    }
                                }, 100);
                            }
                        });
            }
        }
    }

    /**
     * Loader View Holder
     */
    class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * return view type based on position reached
     * @param position position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return (position == userList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    private void add(User user) {
        userList.add(user);
        notifyItemInserted(userList.size() - 1);
    }

    /**
     * Fill up list at every user fetch response
     * @param userList
     */
    void addAll(List<User> userList) {
        for (User user : userList) {
            add(user);
        }
    }

    /**
     * Add loading at footer while user fetch
     */
    void addLoadingFooter() {
        isLoadingAdded = true;
        add(new User());
    }

    /**
     * remove loader when when get response from server
     */
    void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = userList.size() - 1;
        User item = getItem(position);

        if (item != null) {
            userList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private User getItem(int position) {
        return userList.get(position);
    }

}

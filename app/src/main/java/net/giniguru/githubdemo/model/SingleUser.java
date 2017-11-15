package net.giniguru.githubdemo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KK on 11/13/2017.
 */

public class SingleUser implements Serializable
{
    @SerializedName("organizations_url")
    private String organizations_url;

    @SerializedName("received_events_url")
    private String received_events_url;

    @SerializedName("avatar_url")
    private String avatar_url;

    @SerializedName("gravatar_id")
    private String gravatar_id;

    @SerializedName("public_gists")
    private String public_gists;

    @SerializedName("location")
    private String location;

    @SerializedName("site_admin")
    private String site_admin;

    @SerializedName("type")
    private String type;

    @SerializedName("blog")
    private String blog;

    @SerializedName("id")
    private String id;

    @SerializedName("following")
    private String following;

    @SerializedName("followers")
    private String followers;

    @SerializedName("public_repos")
    private String public_repos;

    @SerializedName("name")
    private String name;

    @SerializedName("following_url")
    private String following_url;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("events_url")
    private String events_url;

    @SerializedName("login")
    private String login;

    @SerializedName("subscriptions_url")
    private String subscriptions_url;

    @SerializedName("repos_url")
    private String repos_url;

    @SerializedName("gists_url")
    private String gists_url;

    @SerializedName("starred_url")
    private String starred_url;

    @SerializedName("url")
    private String url;

    @SerializedName("html_url")
    private String html_url;

    @SerializedName("hireable")
    private String hireable;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("bio")
    private String bio;

    @SerializedName("email")
    private String email;

    @SerializedName("company")
    private String company;

    @SerializedName("followers_url")
    private String followers_url;

    public String getOrganizations_url ()
    {
        return organizations_url;
    }

    public void setOrganizations_url (String organizations_url)
    {
        this.organizations_url = organizations_url;
    }

    public String getReceived_events_url ()
    {
        return received_events_url;
    }

    public void setReceived_events_url (String received_events_url)
    {
        this.received_events_url = received_events_url;
    }

    public String getAvatar_url ()
    {
        return avatar_url;
    }

    public void setAvatar_url (String avatar_url)
    {
        this.avatar_url = avatar_url;
    }

    public String getGravatar_id ()
    {
        return gravatar_id;
    }

    public void setGravatar_id (String gravatar_id)
    {
        this.gravatar_id = gravatar_id;
    }

    public String getPublic_gists ()
    {
        return public_gists;
    }

    public void setPublic_gists (String public_gists)
    {
        this.public_gists = public_gists;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getSite_admin ()
    {
        return site_admin;
    }

    public void setSite_admin (String site_admin)
    {
        this.site_admin = site_admin;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getBlog ()
    {
        return blog;
    }

    public void setBlog (String blog)
    {
        this.blog = blog;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFollowing ()
    {
        return following;
    }

    public void setFollowing (String following)
    {
        this.following = following;
    }

    public String getFollowers ()
    {
        return followers;
    }

    public void setFollowers (String followers)
    {
        this.followers = followers;
    }

    public String getPublic_repos ()
    {
        return public_repos;
    }

    public void setPublic_repos (String public_repos)
    {
        this.public_repos = public_repos;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getFollowing_url ()
    {
        return following_url;
    }

    public void setFollowing_url (String following_url)
    {
        this.following_url = following_url;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getEvents_url ()
    {
        return events_url;
    }

    public void setEvents_url (String events_url)
    {
        this.events_url = events_url;
    }

    public String getLogin ()
    {
        return login;
    }

    public void setLogin (String login)
    {
        this.login = login;
    }

    public String getSubscriptions_url ()
    {
        return subscriptions_url;
    }

    public void setSubscriptions_url (String subscriptions_url)
    {
        this.subscriptions_url = subscriptions_url;
    }

    public String getRepos_url ()
    {
        return repos_url;
    }

    public void setRepos_url (String repos_url)
    {
        this.repos_url = repos_url;
    }

    public String getGists_url ()
    {
        return gists_url;
    }

    public void setGists_url (String gists_url)
    {
        this.gists_url = gists_url;
    }

    public String getStarred_url ()
    {
        return starred_url;
    }

    public void setStarred_url (String starred_url)
    {
        this.starred_url = starred_url;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getHtml_url ()
    {
        return html_url;
    }

    public void setHtml_url (String html_url)
    {
        this.html_url = html_url;
    }

    public String getHireable ()
    {
        return hireable;
    }

    public void setHireable (String hireable)
    {
        this.hireable = hireable;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getBio ()
    {
        return bio;
    }

    public void setBio (String bio)
    {
        this.bio = bio;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    public String getFollowers_url ()
    {
        return followers_url;
    }

    public void setFollowers_url (String followers_url)
    {
        this.followers_url = followers_url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [organizations_url = "+organizations_url+", received_events_url = "+received_events_url+", avatar_url = "+avatar_url+", gravatar_id = "+gravatar_id+", public_gists = "+public_gists+", location = "+location+", site_admin = "+site_admin+", type = "+type+", blog = "+blog+", id = "+id+", following = "+following+", followers = "+followers+", public_repos = "+public_repos+", name = "+name+", following_url = "+following_url+", created_at = "+created_at+", events_url = "+events_url+", login = "+login+", subscriptions_url = "+subscriptions_url+", repos_url = "+repos_url+", gists_url = "+gists_url+", starred_url = "+starred_url+", url = "+url+", html_url = "+html_url+", hireable = "+hireable+", updated_at = "+updated_at+", bio = "+bio+", email = "+email+", company = "+company+", followers_url = "+followers_url+"]";
    }
}
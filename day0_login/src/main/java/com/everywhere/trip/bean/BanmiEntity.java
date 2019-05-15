package com.everywhere.trip.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BanmiEntity {
    @Id
    private  Long ids;
    private String occupation;
    private int following;
    private String name;
    private String photo;
    private String location;
    private int id;
    private String introduction;
    private boolean isFollowed;
    @Generated(hash = 627998351)
    public BanmiEntity(Long ids, String occupation, int following, String name,
            String photo, String location, int id, String introduction,
            boolean isFollowed) {
        this.ids = ids;
        this.occupation = occupation;
        this.following = following;
        this.name = name;
        this.photo = photo;
        this.location = location;
        this.id = id;
        this.introduction = introduction;
        this.isFollowed = isFollowed;
    }
    @Generated(hash = 2095745280)
    public BanmiEntity() {
    }
    public Long getIds() {
        return this.ids;
    }
    public void setIds(Long ids) {
        this.ids = ids;
    }
    public String getOccupation() {
        return this.occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public int getFollowing() {
        return this.following;
    }
    public void setFollowing(int following) {
        this.following = following;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getIntroduction() {
        return this.introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public boolean getIsFollowed() {
        return this.isFollowed;
    }
    public void setIsFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    public boolean isIsFollowed() {
        return isFollowed;
    }
}

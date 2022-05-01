package com.cos.instagram.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Post {
    private String postId;
    private String username;
    private String contents;
    private int like;
    private int reply;

    @ServerTimestamp
    private Date postDate;

    public Post(String postId, String username, String contents, int like, int reply) {
        this.postId = postId;
        this.username = username;
        this.contents = contents;
        this.like = like;
        this.reply = reply;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", username='" + username + '\'' +
                ", contents='" + contents + '\'' +
                ", like=" + like +
                ", reply=" + reply +
                ", postDate=" + postDate +
                '}';
    }
}

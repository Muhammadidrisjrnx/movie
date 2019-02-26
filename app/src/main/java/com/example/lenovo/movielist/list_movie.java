package com.example.lenovo.movielist;

public class list_movie {
    private int id;
    private String title;
    private String image_url;
    private String overview;
    private String release_date;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public list_movie(int id, String title, String image_url, String overview) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.overview = overview;
    }
    public list_movie(){

    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}

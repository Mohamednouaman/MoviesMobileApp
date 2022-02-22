package models;

import java.io.Serializable;

public class MovieItem implements Serializable {

    private String date;
    private String title ;
    private String pathImage;

    public void setDate(String date) {
        this.date = date;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public String getPathImage() {
        return pathImage;
    }

    public String getTitle() {
        return title;
    }
}

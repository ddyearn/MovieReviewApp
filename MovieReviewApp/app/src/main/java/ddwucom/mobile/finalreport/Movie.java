package ddwucom.mobile.finalreport;

import java.io.Serializable;

public class Movie implements Serializable {
    private long _id;

    private String title;
    private String releaseDate;
    private String rate;

    private String characters;
    private String director;
    private String review;

    private int imageSource;

    public Movie(String title, String releaseDate, String rate, String characters, String director, String review) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.characters = characters;
        this.director = director;
        this.review = review;
    }

    public Movie(long _id, String title, String releaseDate, String rate, String characters, String director, String review, int imageSource) {
        this._id = _id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.characters = characters;
        this.director = director;
        this.review = review;
        this.imageSource = imageSource;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) { this.rate = rate; }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getImageSource() { return imageSource; }

    public void setImageSource(int imageSource) {
       this.imageSource = imageSource;
    }
}

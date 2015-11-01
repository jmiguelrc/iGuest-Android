package pt.pemitech.iguest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Event implements Serializable {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("relevance")
    @Expose
    private Integer relevance;
    @SerializedName("club_id")
    @Expose
    private String clubId;
    @SerializedName("club_name")
    @Expose
    private String clubName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("hour")
    @Expose
    private Integer hour;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The relevance
     */
    public Integer getRelevance() {
        return relevance;
    }

    /**
     *
     * @param relevance
     * The relevance
     */
    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    /**
     *
     * @return
     * The clubId
     */
    public String getClubId() {
        return clubId;
    }

    /**
     *
     * @param clubId
     * The club_id
     */
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    /**
     *
     * @return
     * The clubName
     */
    public String getClubName() {
        return clubName;
    }

    /**
     *
     * @param clubName
     * The club_name
     */
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The hour
     */
    public Integer getHour() {
        return hour;
    }

    /**
     *
     * @param hour
     * The hour
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

}
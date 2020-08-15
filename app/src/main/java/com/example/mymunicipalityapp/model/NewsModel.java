package com.example.mymunicipalityapp.model;


/**
 * An {@link NewsModel} object contains information related to a single news.
 */



public class NewsModel {

    /** Title of the article */
    private String mTitle;

    /** Section name of the article*/
    private String mSection;

    /** Web publication date of the article */
    private String mDate;

    /** Website URL of the article */
    private String mUrl;

    /** Thumbnail of the article */
    private int mThumbnail;

    /** TrailText of the article with string type Html */
    private String mTrailTextHtml;

    /** content of the article  */
    private String content;

    /**
     * Constructs a new {@link NewsModel} object.
     *
     * @param title is the title of the article
     * @param section is the section name of the article
     * @param date is the web publication date of the article
     * @param url is the website URL to find more details about the article
     * @param thumbnail is the thumbnail of the article
     * @param trailText is trail text of the article with string type Html
     * @param content is content text of the article.
     */

    public NewsModel(String title, String section, String date, String url, int thumbnail, String trailText , String content ) {
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
        mThumbnail = thumbnail;
        mTrailTextHtml = trailText;
        this.content = content;
    }

    /**
     * Returns the title of the article
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the section name of the article.
     */
    public String getSection() {
        return mSection;
    }


    /**
     * Returns the web publication date of the article.
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Returns the website URL to find more information about the news.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Returns the thumbnail of the article
     */
    public int getThumbnail() {
        return mThumbnail;
    }

    /**
     * Returns the TrailText of the article with string type Html
     */
    public String getTrailTextHtml() {
        return mTrailTextHtml;
    }


    /**
     * Returns the content of the article
     */
    public String getContent() {
        return content;
    }

}

package com.example.tony.plusreader;

/**
 * Handles the data from the SQLite  database
 */
public class Website {
//    declares the variables used by the RSS file
    Integer _id;
    String _title;
    String _link;
    String _rss_link;
    String _description;

    // constructor (duplicates multiple website objects)
    public Website(){

    }


    // These are attributes added to the constructor
    public Website(String title, String link, String rss_link, String description){
        this._title = title;
        this._link = link;
        this._rss_link = rss_link;
        this._description = description;
    }

    /**
     * Added set methods
     * */
    public void setId(Integer id){
        this._id = id;
    }

    public void setTitle(String title){
        this._title = title;
    }

    public void setLink(String link){
        this._link = link;
    }

    public void setRSSLink(String rss_link){
        this._rss_link = rss_link;
    }

    public void setDescription(String description){
        this._description = description;
    }

    /**
     * Added get methods
     * */
    public Integer getId(){
        return this._id;
    }

    public String getTitle(){
        return this._title;
    }

    public String getLink(){
        return this._link;
    }

    public String getRSSLink(){
        return this._rss_link;
    }

    public String getDescription(){
        return this._description;
    }
}

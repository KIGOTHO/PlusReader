package com.example.tony.plusreader;

/**
 * Created by tony on 30/07/15.
 */
public class RSSItem {

    // Describes the variables to be found in this file
    String _title;
    String _link;
    String _description;
    String _pubdate;
    String _guid;

    // A constructor lies below
    public RSSItem(){
    }

    // Parameters for the above constructor
    public RSSItem(String title, String link, String description, String pubdate, String guid){
        this._title = title;
        this._link = link;
        this._description = description;
        this._pubdate = pubdate;
        this._guid = guid;
    }

    /**
     * SET methods that handles the users input from RSSfeed class
     * */
    public void setTitle(String title){
        this._title = title;
    }

    public void setLink(String link){
        this._link = link;
    }

    public void setDescription(String description){
        this._description = description;
    }

    public void setPubdate(String pubDate){
        this._pubdate = pubDate;
    }


    public void setGuid(String guid){
        this._guid = guid;
    }

    /**
     * GET method that handles input from the RSS xml file
     * */
    public String getTitle(){
        return this._title;
    }

    public String getLink(){
        return this._link;
    }

    public String getDescription(){
        return this._description;
    }

    public String getPubdate(){
        return this._pubdate;
    }

    public String getGuid(){
        return this._guid;
    }
}

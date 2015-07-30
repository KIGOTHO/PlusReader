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
}

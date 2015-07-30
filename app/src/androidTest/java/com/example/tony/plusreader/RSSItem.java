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
}

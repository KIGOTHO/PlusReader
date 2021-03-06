package com.example.tony.plusreader;

import java.util.List;
/**
 * This class handle rss xml
 * */
public class RSSFeed {
    // list the things to expect from the feed
    String _title;
    String _description;
    String _link;
    String _rss_link;
    String _language;
    List<RSSItem> _items;


    // Assigns the descriptions of the RSS feeds
    public RSSFeed(String title, String description, String link,
                   String rss_link, String language) {
        this._title = title;
        this._description = description;
        this._link = link;
        this._rss_link = rss_link;
        this._language = language;
    }

    /**
     * Creates a platform for the user to input  rss information (feed)
     * */
    public void setItems(List<RSSItem> items) {
        this._items = items;
    }

    /**
     * The code below collects the RSS data from the given site
     * */
    public List<RSSItem> getItems() {
        return this._items;
    }

    public String getTitle() {
        return this._title;
    }

    public String getDescription() {
        return this._description;
    }

    public String getLink() {
        return this._link;
    }

    public String getRSSLink() {
        return this._rss_link;
    }

    public String getLanguage() {
        return this._language;
    }
}


package com.example.thekra.newsapp;



public class News {
    private String nTitle;
    private String nUrl;
    private String nSection;

    public News(String title,  String section,String url ) {
        nTitle = title;
        nUrl = url;
        nSection = section;
    }

    public String getTitle() {
        return nTitle;
    }

    public String getUrl() {
        return nUrl;
    }

    public String getSectionName() {
        return nSection;
    }
}

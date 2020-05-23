package com.example.newsapp;

public class News {
    String Pillarname;
    String WebTitle;
    String webPublicationDate;
    String SectionName;
    String url;
    String author = null ;

    public News(String Pillarname,String WebTitle,String webPublicationDate,String SectionName,String url ){
        this.Pillarname = Pillarname;
        this.WebTitle = WebTitle;
        this.webPublicationDate = webPublicationDate;
        this.SectionName = SectionName;
        this.url = url;

    }
    public News(String Pillarname,String WebTitle,String webPublicationDate,String SectionName,String url,String author ){
        this.Pillarname = Pillarname;
        this.WebTitle = WebTitle;
        this.webPublicationDate = webPublicationDate;
        this.SectionName = SectionName;
        this.url = url;
        this.author = author;

    }

    public String getPillarname() {
        return Pillarname;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getSectionName() {
        return SectionName;
    }

    public String getWebTitle() {
        return WebTitle;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public boolean hasAuthor(){
        return author != null;
    }
}

package com.example.icubeapp.model;

/**
 * Created by Prakash on 7/23/2017.
 */

public class FeedBackSelection {
    public String id;
    public String group_id;
    public String language_id;
    public String question;
    public String rating_type;
    public String outof;
    public String rating;
    public String comment;
    public String ismandatory;
    public String status_select;

    public FeedBackSelection( String id, String group_id, String language_id, String question, String rating_type,String outof,String rating,String comment,String ismandatory,String status_select) {

        this.id = id;
        this.group_id = group_id;
        this.language_id = language_id;
        this.question = question;
        this.rating_type = rating_type;
        this.outof = outof;
        this.rating=rating;
        this.comment = comment;
        this.ismandatory=ismandatory;
        this.status_select=status_select;
    }
}

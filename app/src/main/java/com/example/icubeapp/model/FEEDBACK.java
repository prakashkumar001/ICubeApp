package com.example.icubeapp.model;

import java.util.ArrayList;

/**
 * Created by Prakash on 7/23/2017.
 */

public class FEEDBACK {
    public String id;
    public String group_id;
    public String language_id;
    public String question;
    public String rating_type;
    public String outof;
    public boolean focus;
    public ArrayList<Integer> status;
    public String comment;
    public String IsMandatory;
public String status_select;
    public FEEDBACK( String id, String group_id, String language_id, String question, String rating_type,String outof) {

        this.id = id;
        this.group_id = group_id;
        this.language_id = language_id;
        this.question = question;
        this.rating_type = rating_type;
        this.outof = outof;
    }
    public FEEDBACK(String id, String group_id, String language_id, String question, String rating_type,String outof,ArrayList<Integer> status,String comment,String IsMandatory,String status_select) {

        this.id = id;
        this.group_id = group_id;
        this.language_id = language_id;
        this.question = question;
        this.rating_type = rating_type;
        this.outof = outof;
        this.status=status;
        this.comment=comment;
        this.IsMandatory=IsMandatory;
        this.status_select=status_select;
    }
}

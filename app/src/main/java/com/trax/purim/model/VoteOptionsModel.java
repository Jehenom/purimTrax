package com.trax.purim.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asisayag on 27/01/2018.
 */

public class VoteOptionsModel {

    @SerializedName("votes_options")
    public List<VoteOption> options;
}

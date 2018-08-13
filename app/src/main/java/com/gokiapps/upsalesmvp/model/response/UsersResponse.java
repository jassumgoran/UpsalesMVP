package com.gokiapps.upsalesmvp.model.response;

import com.gokiapps.upsalesmvp.model.User;

import java.util.List;

/**
 * Created by Goran on 20.4.2018.
 */

public class UsersResponse {

    Metadata metadata;
    List<User> data;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}

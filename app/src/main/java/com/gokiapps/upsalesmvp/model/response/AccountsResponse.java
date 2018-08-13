package com.gokiapps.upsalesmvp.model.response;

import com.gokiapps.upsalesmvp.model.Account;

import java.util.List;

/**
 * Created by Goran on 20.4.2018.
 */

public class AccountsResponse {

    Metadata metadata;
    List<Account> data;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Account> getData() {
        return data;
    }

    public void setData(List<Account> data) {
        this.data = data;
    }
}

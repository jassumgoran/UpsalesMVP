package com.gokiapps.upsalesmvp.presenter;

import com.gokiapps.upsalesmvp.Constants;
import com.gokiapps.upsalesmvp.api.ApiClient;

import com.gokiapps.upsalesmvp.model.Account;
import com.gokiapps.upsalesmvp.model.response.AccountsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    View view;
    List<Account> accounts;

    int offset = 0;
    Integer totalServerAccounts = 0;

    public MainActivityPresenter(View view) {
        this.view = view;
        this.accounts = new ArrayList<>();
    }

    private void appendAccounts(List<Account> accounts){
        this.accounts.addAll(accounts);
    }

    public void clearAccounts(){
        this.accounts.clear();
    }

    private void showProgress(boolean reload){
        if(reload)
            view.showProgress();
        else
            view.showLoadMore();
    }

    private void hideProgress(boolean reload){
        if(reload)
            view.hideProgress();
        else
            view.hideLoadMore();
    }

    public void refetchClients(){
        offset = 0;
        clearAccounts();
        view.refreshAccounts();
        fetchClients(true);
    }

    public void fetchClients(){
        fetchClients(false);
    }

    public void fetchClients(boolean reload){

        showProgress(reload);

        ApiClient.getClient().getAccounts(offset * Constants.PAGE_SIZE, Constants.PAGE_SIZE, "name").enqueue(new Callback<AccountsResponse>() {

            @Override
            public void onResponse(Call<AccountsResponse> call, Response<AccountsResponse> response) {
                if(response.isSuccessful()) {
                    AccountsResponse accountsResponse = response.body();
                    totalServerAccounts = accountsResponse.getMetadata().getTotal();
                    offset++;
                    List<Account> accounts = accountsResponse.getData();

                    if(accounts != null){
                        appendAccounts(accounts);
                        view.refreshAccounts();
                    }
                }
                hideProgress(reload);
            }

            @Override
            public void onFailure(Call<AccountsResponse> call, Throwable t) {
                hideProgress(reload);
            }

        });
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Integer getTotalServerAccounts() {
        return totalServerAccounts;
    }

    public interface View {
        public void showProgress();
        public void hideProgress();
        public void showLoadMore();
        public void hideLoadMore();
        public void refreshAccounts();
    }

}

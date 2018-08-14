package com.gokiapps.upsalesmvp.presenter;

import com.gokiapps.upsalesmvp.Constants;
import com.gokiapps.upsalesmvp.api.ApiClient;
import com.gokiapps.upsalesmvp.model.Account;
import com.gokiapps.upsalesmvp.model.response.AccountsResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {

    View view;

    int offset = 0;
    Integer totalServerAccounts = 0;

    private CompositeDisposable compositeDisposable;

    public MainActivityPresenter(View view) {
        this.view = view;
        this.compositeDisposable = new CompositeDisposable();
    }

    private void showProgress(boolean reload){
        try {
            if (reload)
                view.showProgress();
            else
                view.showLoadMore();
        }catch(NullPointerException e){

        }
    }

    private void hideProgress(boolean reload){
        try {
            if (reload)
                view.hideProgress();
            else
                view.hideLoadMore();
        }catch(NullPointerException e){

        }
    }

    public void refetchAccounts(){
        offset = 0;

        try {
            view.clearAccounts();
        }catch(NullPointerException e){

        }

        fetchAccounts(true);

    }

    public void fetchAccounts(){
        fetchAccounts(false);
    }

    public void fetchAccounts(boolean reload){
        showProgress(reload);
        compositeDisposable.add(
                ApiClient.getClient().getAccountsList(offset * Constants.PAGE_SIZE, Constants.PAGE_SIZE, "name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accountsResponse -> {
                    onAccountsResponse(accountsResponse, reload);
                })
        );
    }

    private void onAccountsResponse(AccountsResponse accountsResponse, boolean reload){
        totalServerAccounts = accountsResponse.getMetadata().getTotal();
        offset++;
        List<Account> accounts = accountsResponse.getData();
        if(view != null && accounts != null){
            view.addAccounts(accounts);
        }
        hideProgress(reload);
    }

    public boolean loadMore(int currentSize){
        return totalServerAccounts > currentSize;
    }

    public void onDestroy(){
        view = null;
        compositeDisposable.clear();
    }

    public interface View {
        public void showProgress();
        public void hideProgress();
        public void showLoadMore();
        public void hideLoadMore();
        public void clearAccounts();
        public void addAccounts(List<Account> accounts);
    }

}

package com.gokiapps.upsalesmvp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.gokiapps.upsalesmvp.model.Account;
import com.gokiapps.upsalesmvp.presenter.MainActivityPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    @BindView(android.R.id.list)
    ListView lvAccounts;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    MainActivityPresenter presenter;
    AccountsAdapter accountsAdapter;

    View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);

        progressView = LayoutInflater.from(this).inflate(R.layout.progress, null);
        progressView.setVisibility(View.GONE);

        accountsAdapter = new AccountsAdapter();
        lvAccounts.setAdapter(accountsAdapter);
        lvAccounts.addFooterView(progressView);

        handleEvents();

        presenter.refetchAccounts();
    }

    private void handleEvents(){

        swipeRefreshLayout.setOnRefreshListener(() -> {
            runOnUiThread(() -> presenter.refetchAccounts());
        });

        lvAccounts.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {

                if(presenter.loadMore(accountsAdapter.getCount())) {
                    loadMore();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadMore() {
        runOnUiThread(() -> {
            progressView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoadMore() {
        runOnUiThread(() -> {
            progressView.setVisibility(View.GONE);
        });
    }

    @Override
    public void clearAccounts() {
        this.accountsAdapter.clearAccounts();
    }

    @Override
    public void addAccounts(List<Account> accounts) {
        this.accountsAdapter.addAccounts(accounts);
    }

    private void loadMore(){
        runOnUiThread(() -> presenter.fetchAccounts());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}

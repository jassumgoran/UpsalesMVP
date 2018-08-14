package com.gokiapps.upsalesmvp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gokiapps.upsalesmvp.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountsAdapter extends BaseAdapter {

    List<Account> accounts;

    public AccountsAdapter() {
        this.accounts = new ArrayList<>();
    }

    public AccountsAdapter(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        view.setText(accounts.get(position).getName());
        return view;
    }

    public void addAccounts(List<Account> accounts){
        this.accounts.addAll(accounts);
        this.notifyDataSetChanged();
    }

    public void clearAccounts(){
        this.accounts.clear();
        this.notifyDataSetChanged();
    }
}

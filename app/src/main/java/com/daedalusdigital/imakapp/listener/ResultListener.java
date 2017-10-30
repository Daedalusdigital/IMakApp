package com.daedalusdigital.imakapp.listener;

import com.daedalusdigital.imakapp.ArgusState;

public interface ResultListener {
    void onSuccess(ArgusState argusState);

    void onFailure(String message);
}

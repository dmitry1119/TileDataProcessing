package com.tilepay.web.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.dto.ResponseDTO;
import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.model.Account;
import com.tilepay.core.service.WalletService;

@Service
public class PrivateKeyWebService {

    @Inject
    private SessionService sessionService;

    @Inject
    private PasswordValidator passwordValidator;

    @Inject
    private WalletService walletService;

    public ResponseDTO confirmPassphrase(String passphrase) {
        ResponseDTO form = new ResponseDTO();

        Account account = sessionService.getAccount();

        //TODO: 13.10.2014 Mait Mikkelsaar: Refactor so it would not start syncing wallet?
        if (passwordValidator.isPasswordCorrect(account.getWallet().getId(), passphrase)) {
            WalletDTO wallet = walletService.loadWallet(account.getWallet().getId(), passphrase);
            form.setSuccessful(true);
            form.setMessage(wallet.getPrivateKey());
        }
        return form;
    }
}

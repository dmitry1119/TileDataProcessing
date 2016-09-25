package com.tilepay.web.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tilepay.core.dto.WalletDTO;
import com.tilepay.core.model.Account;
import com.tilepay.core.model.Address;
import com.tilepay.core.model.Wallet;
import com.tilepay.core.service.AccountService;
import com.tilepay.core.service.AddressService;
import com.tilepay.core.service.WalletService;

@Controller
public class AccountController {

    // TODO: 22.09.2014 Andrei Sljusar: account web service
    @Inject
    private AccountService accountService;

    @Inject
    private AddressService addressService;

    @Inject
    private WalletService walletService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("account", new Account());
        return "loginNewAccount";
    }

    @RequestMapping(value = "/saveNewAccount", method = RequestMethod.POST)
    public String create(@ModelAttribute Account account) throws IOException {
        Wallet wallet = walletService.createNewWalletForAccount();
        Address walletAddress = addressService.save(wallet.getAddress());
        account.setWallet(wallet);
        accountService.createAccount(account);
        walletService.encryptWallet(wallet.getId(), account.getPassword());

        // TODO: 19.10.2014 Andrei Sljusar: loginAfterNewAccountHasBeenCreated
        return "redirect:/login?newAccount";
    }

    @RequestMapping(value = "/createNewAccount", method = RequestMethod.GET)
    public String createWallet(Model model) {
        WalletDTO wallet = walletService.createWallet();
        // TODO: 13.11.2014 Andrei Sljusar: ?
        if (wallet == null)
            return "login";
        else {
            Account account = new Account();
            account.setPassPhrase(wallet.getPassPhrase());
            model.addAttribute("account", account);
            return "createNewAccount";
        }

    }

}

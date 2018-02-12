package com.visu.sb.ams.controller;

import com.visu.sb.ams.entity.Account;
import com.visu.sb.ams.service.AccountService;
import com.visu.sb.ams.util.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@RequestMapping("/service/account")
public class AccountServiceRestController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccountById(@PathVariable("id") BigInteger id) throws Exception {

        Account account = accountService.getAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(value = "/put", method = RequestMethod.PATCH)
    public ResponseEntity<OperationResult> put(@RequestParam(value="id") BigInteger id,
                                               @RequestParam(value="amount") BigDecimal amount) {

        OperationResult operationResult = accountService.put(id, amount);
        return new ResponseEntity<>(operationResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.PATCH)
    public ResponseEntity<OperationResult> get(@RequestParam(value="id") BigInteger id,
                                               @RequestParam(value="amount") BigDecimal amount) {

        OperationResult operationResult = accountService.get(id, amount);
        return new ResponseEntity<>(operationResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.PATCH)
    public ResponseEntity<OperationResult> transfer(@RequestParam(value="senderId") BigInteger senderId,
                                                    @RequestParam(value="receiverId") BigInteger receiverId,
                                                    @RequestParam(value="amount") BigDecimal amount) {

        OperationResult operationResult = accountService.transfer(senderId, receiverId, amount);
        return new ResponseEntity<>(operationResult, HttpStatus.OK);
    }
}

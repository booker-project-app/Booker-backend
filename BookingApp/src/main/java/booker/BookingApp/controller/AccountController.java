/*package booker.BookingApp.controller;

import booker.BookingApp.dto.AccountDTO;
import booker.BookingApp.model.Account;
import booker.BookingApp.service.implementation.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountService.findAll();

        List<AccountDTO> accountsDTO = new ArrayList<>();
        for (Account a : accounts) {
            accountsDTO.add(new AccountDTO(a));
        }

        return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        Account account = accountService.findOne(id);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{findUserId}")
    public ResponseEntity<AccountDTO> getAccountByUserId(@PathVariable Long findUserId) {
        Account account = accountService.findOneByUserId(findUserId);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) {
        Account account = new Account();

        account.setRole(accountDTO.getRole());
        account.setBlocked(accountDTO.isBlocked());
        account.setDeleted(accountDTO.isDeleted());

        account = accountService.save(account);
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO) {
        Account account = accountService.findOne(accountDTO.getId());

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        account.setRole(accountDTO.getRole());
        account.setBlocked(accountDTO.isBlocked());
        account.setDeleted(accountDTO.isDeleted());

        account = accountService.save(account);
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Account account = accountService.findOne(id);

        if (account != null) {
            accountService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}*/

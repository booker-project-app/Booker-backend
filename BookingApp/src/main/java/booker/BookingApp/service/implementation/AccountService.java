/*package booker.BookingApp.service.implementation;

import booker.BookingApp.model.Account;
import booker.BookingApp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account findOne(Long id) {
        if (accountRepository.findOne(id) == null) {
            return null;
        }
        return accountRepository.findOne(id);
    }

    public Account findOneByUserId(Long userId) {
        if (accountRepository.findOneByUserId(userId) == null) {
            return null;
        }
        return accountRepository.findOneByUserId(userId);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account create(Account account) {
        return accountRepository.create(account);
    }

    public Account update(Account account) {
        return accountRepository.update(account);
    }

    public void remove(Long id) {
        accountRepository.deleteById(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}*/

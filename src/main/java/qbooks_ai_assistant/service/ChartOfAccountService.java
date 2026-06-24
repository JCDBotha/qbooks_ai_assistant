package qbooks_ai_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.repository.ChartOfAccountRepository;

@Service
public class ChartOfAccountService {

    private final ChartOfAccountRepository repository;

    public ChartOfAccountService(
            ChartOfAccountRepository repository) {

        this.repository = repository;
    }

    public List<ChartOfAccount> getAccountsForClient(
            Client client) {

        return repository.findByClientAndActiveTrue(
                client);
    }

    public ChartOfAccount save(
            ChartOfAccount account) {

        return repository.save(account);
    }
}

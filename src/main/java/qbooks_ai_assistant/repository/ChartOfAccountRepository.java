package qbooks_ai_assistant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;

public interface ChartOfAccountRepository
                extends JpaRepository<ChartOfAccount, Long> {

        List<ChartOfAccount> findByClient(Client client);

        List<ChartOfAccount> findByClientAndActiveTrue(
                        Client client);

        Optional<ChartOfAccount> findByClientAndAccountName(
                        Client client,
                        String accountName);
}

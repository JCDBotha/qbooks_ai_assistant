package qbooks_ai_assistant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import qbooks_ai_assistant.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByRealmId(String realmId);

}

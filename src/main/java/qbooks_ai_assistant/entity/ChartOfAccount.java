package qbooks_ai_assistant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chart_of_accounts")
public class ChartOfAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    private String accountName;

    private String accountType;

    private Boolean active = true;

    private String quickbooksAccountId;

    private LocalDateTime createdAt = LocalDateTime.now();

    public ChartOfAccount() {
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getQuickbooksAccountId() {
        return quickbooksAccountId;
    }

    public void setQuickbooksAccountId(String quickbooksAccountId) {
        this.quickbooksAccountId = quickbooksAccountId;
    }
}

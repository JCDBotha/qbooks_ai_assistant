package qbooks_ai_assistant.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "business_rules")
public class BusinessRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String keyword;

    @ManyToOne
    @JoinColumn(name = "chart_of_account_id")
    private ChartOfAccount chartOfAccount;

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    public BusinessRule() {
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ChartOfAccount getChartOfAccount() {
        return chartOfAccount;
    }

    public void setChartOfAccount(ChartOfAccount chartOfAccount) {
        this.chartOfAccount = chartOfAccount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

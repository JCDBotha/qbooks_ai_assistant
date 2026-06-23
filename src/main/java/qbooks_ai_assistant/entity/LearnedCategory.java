package qbooks_ai_assistant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "learned_categories")
public class LearnedCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descriptionPattern;

    private String category;

    private int usageCount;

    private double confidence;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public LearnedCategory() {
        this.usageCount = 0;
        this.confidence = 0.0;
    }

    public Long getId() {
        return id;
    }

    public String getDescriptionPattern() {
        return descriptionPattern;
    }

    public void setDescriptionPattern(String descriptionPattern) {
        this.descriptionPattern = descriptionPattern;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

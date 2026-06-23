package qbooks_ai_assistant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "predictions")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String predictedCategory;

    private Double confidenceScore;

    private String source;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public Prediction() {
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPredictedCategory() {
        return predictedCategory;
    }

    public void setPredictedCategory(String predictedCategory) {
        this.predictedCategory = predictedCategory;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}

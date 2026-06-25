package qbooks_ai_assistant.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "business_keywords")
public class BusinessKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private String meaning;

    private Boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    public BusinessKeyword() {
    }

    public Long getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
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
}

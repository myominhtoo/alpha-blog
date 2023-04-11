package com.lio.BlogApi.models.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_code")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "code_status")
    private String codeStatus;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /*
     * new added column for expire date time
     */
    @Column(name = "expired_date")
    private Date expiredDate;

}

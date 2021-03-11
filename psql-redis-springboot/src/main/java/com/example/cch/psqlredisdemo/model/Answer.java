package com.example.cch.psqlredisdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends AuditModel {
    /**
     *
     */
    private static final long serialVersionUID = 292172721641030328L;

    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(
            name = "answer_generator",
            sequenceName = "answer_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // FetchType.LAZY 只在用到時才載入關聯的物件
    @JoinColumn(name = "question_id", nullable = false) // setting FK
    @OnDelete(action = OnDeleteAction.CASCADE) // FK 約束，當在父表（即外鍵的來源表）中刪除對應記錄時，首先檢查該記錄是否有對應外鍵，如果有則也刪除外鍵在子表（即包含外鍵的表）中的記錄。
    @JsonIgnore
    private Question question;
}

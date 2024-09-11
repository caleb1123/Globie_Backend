package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
;import java.time.LocalDate;


@Entity
@Table(name = "MemberHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;

    @Column(nullable = false)
    private LocalDate changeDate;

    @Column
    private String reason;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "previousLevelId", nullable = false)
    private MemberLevel previousLevel;

}

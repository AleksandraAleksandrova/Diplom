package org.elsys.diplom.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "startDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
     */

    /*
    @ManyToOne
    @Column(name = "userId", nullable = false)
    private Long userId;
     */
}

package com.grade;

import com.common.hibernate.AppEntity;
import com.student.domain.Student;
import com.subject.domain.Subject;
import com.tutor.domain.Tutor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

@Table(name = "grades")
@Entity
public class Grade implements AppEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(nullable = false)
    LocalDateTime assessmentLocalDateTime;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    @Column(nullable = false)
    GradeType gradeType;

    @Column(nullable = false)
    BigDecimal value;


    public Grade() {
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }
}
package com.roufit.backend.domain.workout.domain.record;

import com.roufit.backend.domain.member.domain.Member;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "workout_record")
public class WorkoutRecord extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "workout_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_template_id")
    private WorkoutTemplate workoutTemplate;

    private long duration;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isCompleted;
}

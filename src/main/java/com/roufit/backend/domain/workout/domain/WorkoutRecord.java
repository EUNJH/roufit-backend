package com.roufit.backend.domain.workout.domain;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "workout_record")
public class WorkoutRecord extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "workout_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_template_id")
    private WorkoutTemplate workoutTemplate;

    private long duration;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isCompleted;

    @Builder
    public WorkoutRecord(User user, WorkoutTemplate workoutTemplate, long duration,
                         LocalDateTime startTime, LocalDateTime endTime, boolean isCompleted) {
        this.user = user;
        this.workoutTemplate = workoutTemplate;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
    }
}

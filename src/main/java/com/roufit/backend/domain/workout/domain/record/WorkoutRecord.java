package com.roufit.backend.domain.workout.domain.record;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "workout_record")
public class WorkoutRecord extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_template_id")
    private WorkoutTemplate workoutTemplate;

    private Integer duration;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isCompleted;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "workoutRecord")
    List<SetRecord> setRecords = new ArrayList<>();

    @Builder
    public WorkoutRecord(User user, WorkoutTemplate workoutTemplate, WorkoutRecordRequest request) {
        this.user = user;
        this.workoutTemplate = workoutTemplate;
        this.duration = request.getDuration();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.isCompleted = request.getIsCompleted();
    }
}

package fs19.java.backend.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task taskId;
    @Column(nullable = false)
    private String content;
    @Column
    private ZonedDateTime createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;
}
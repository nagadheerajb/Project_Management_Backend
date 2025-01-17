package fs19.java.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "project", indexes = {
    @Index(name = "idx_project_name", columnList = "name"),
    @Index(name = "idx_project_created_by_user_id", columnList = "created_by_user_id"),
    @Index(name = "idx_project_workspace_id", columnList = "workspace_id"),
    @Index(name = "idx_project_status", columnList = "status")
})
public class Project {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 500)
  private String description;

  @Column
  private ZonedDateTime createdDate;

  @Column(nullable = false)
  private ZonedDateTime startDate;

  @Column
  private ZonedDateTime endDate;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "created_by_user_id", referencedColumnName = "id", nullable = false)
  private User createdByUser;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable = false)
  private Workspace workspace;

  @Column(nullable = false)
  private Boolean status;

  @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Notification> notifications;
}


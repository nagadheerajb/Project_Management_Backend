package fs19.java.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.ZonedDateTime;
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
}


package fs19.java.backend.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users",
    indexes = {
        @Index(name = "idx_users_email", columnList = "email")
    }
)
public class User {
  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column
  private String phone;

  @Column
  private ZonedDateTime createdDate;

  @Column
  private String profileImage;

  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
  @Exclude
  private List<ActivityLog> activityLogs;
}
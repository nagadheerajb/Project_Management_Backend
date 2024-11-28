package fs19.java.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "invitation", indexes = {
        @Index(name = "idx_invitation_workspace_id", columnList = "workspace_id"),
        @Index(name = "idx_invitation_role_id", columnList = "role_id"),
        @Index(name = "idx_invitation_user_id", columnList = "user_id")
})
public class Invitation {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isAccepted;
    @NotNull
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime expiredAt;
    @NotNull
    @Column(nullable = false, length = 45)
    private String email;
    @NotNull
    @Column(nullable = false, length = 3000)
    private String url;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User createdBy;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable = false)
    private Workspace workspace;
}

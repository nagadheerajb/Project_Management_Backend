package fs19.java.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Responsible to handle permission
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role_permission", indexes = {
        @Index(name = "idx_role_permission_role_id", columnList = "role_id"),
        @Index(name = "idx_role_permission_permission_id", columnList = "permission_id")
})
public class RolePermission {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false)
    private Permission permission;
}

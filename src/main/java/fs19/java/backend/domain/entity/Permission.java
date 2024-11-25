package fs19.java.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Responsible to work as a base model for a permission object
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @Column(unique = true, nullable = false, length = 25)
    private String name;

}

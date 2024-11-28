package fs19.java.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Responsible to work as a base model for a role object
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @Column(nullable = false, length = 45)
    private String name;
    @NotNull
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime createdDate;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

}
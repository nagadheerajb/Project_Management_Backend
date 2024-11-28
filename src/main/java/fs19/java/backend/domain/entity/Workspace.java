package fs19.java.backend.domain.entity;

import fs19.java.backend.domain.entity.enums.WorkspaceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workspace")
public class Workspace {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private WorkspaceType type;

    @Column
    private ZonedDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company companyId;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkspaceUser> workspaceUsers;
}
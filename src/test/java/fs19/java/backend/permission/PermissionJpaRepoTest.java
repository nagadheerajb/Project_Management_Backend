package fs19.java.backend.permission;

import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.infrastructure.JpaRepositories.PermissionJpaRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionJpaRepoTest {

    @Autowired
    private PermissionJpaRepo permissionJpaRepo;

    @Test
    public void testFindByName() {
        // Create and save a permission
        Permission permission = new Permission();
        permission.setName("Test Permission");
        permissionJpaRepo.save(permission);

        // Find the permission by name
        Permission foundPermission = permissionJpaRepo.findByName("Test Permission");

        // Validate the result
        Assert.assertNotNull(foundPermission);
        Assert.assertEquals("Test Permission", foundPermission.getName());
    }
}

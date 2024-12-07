
import org.example.Repository.DBRepo;
import org.example.Utils.Role;
import org.example.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.example.service.CoffeeShopService;

import static org.junit.jupiter.api.Assertions.*;

public class DBRepoAdminTest extends BaseIntegrationTest {
    private DBRepo<Admin> adminRepository;
    private CoffeeShopService coffeeShopService;

    @BeforeEach
    void setUpRepository() {
        adminRepository = new DBRepo<>(sessionFactory, Admin.class);
        coffeeShopService = new CoffeeShopService(adminRepository);
    }
    void testCreateAndReadAdmin() {
        Admin admin = new Admin(19, "Jane Doe", Role.Manager);
        coffeeShopService.addAdmin(admin);

        Admin retrievedAdmin = adminRepository.read(1);
        assertNotNull(retrievedAdmin);
        assertEquals("Jane Doe", retrievedAdmin.getName());
        assertEquals(Role.Manager, retrievedAdmin.getRole());
    }
}

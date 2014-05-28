package pl.polsl.zti.db1.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import pl.polsl.zti.db1.util.DbSchemaDef;
import pl.polsl.zti.db1.util.JdbcUtils;

public class ClientOrdersTest {

    @BeforeClass
    public static void oneTimeSetUp() {
    }

    @Before
    public void setUp() {
        JdbcUtils.restoreDbSchema(new DbSchemaDef());
    }

    @Test
    @Ignore
    public void testAddClientOrder() {
//        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(ConfigConsts.PERSISTANCE_UNIT_NAME);
//        final EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        final EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//
//        Client client = new Client();
//        client.setName("olo");
//        Order order = new Order();
//        order.setDescription("aaaa");
//        order.setDate(new Date());
//        order.setNo("111");
//        order.setClient(client);
//        client.getOrders().add(order);
//        entityManager.persist(client);
//        transaction.commit();
//        entityManager.close();
//
//        long countClients = (Long) JdbcUtils.executeScalar("SELECT COUNT(*) FROM CLIENTS WHERE NAME='olo'");
//        long countOrders = (Long) JdbcUtils.executeScalar("SELECT COUNT(*) FROM ORDERS WHERE ORDER_NO='111'");
//
//        assertEquals("Klient nie został dodany", 1, countClients);
//        assertEquals("Zamówienie nie zostało dodane", 1, countOrders);
    }
}

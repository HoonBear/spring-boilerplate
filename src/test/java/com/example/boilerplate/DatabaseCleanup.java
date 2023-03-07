package com.example.boilerplate;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Service
@ActiveProfiles("test")
public class DatabaseCleanup implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;
    private List<String> tableNames;

    public static <T> String getTableName(EntityManager em, Class<T> entityClass) {
        /*
         * Check if the specified class is present in the metamodel.
         * Throws IllegalArgumentException if not.
         */
        Metamodel meta = em.getMetamodel();
        EntityType<T> entityType = meta.entity(entityClass);

        //Check whether @Table annotation is present on the class.
        Table t = entityClass.getAnnotation(Table.class);

        String tableName = (t == null) ? entityType.getName().toUpperCase() : t.name();
        return tableName;
    }

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel()
                                  .getEntities()
                                  .stream()
                                  .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                                  .map(e -> getTableName(entityManager, e.getJavaType()))
                                  .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1").executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}

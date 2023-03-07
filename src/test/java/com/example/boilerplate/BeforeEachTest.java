package com.example.boilerplate;

import com.example.boilerplate.mocking.MemberMocking;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BeforeEachTest {
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    EntityManager entityManager;
    @Autowired
    MemberMocking memberMocking;

    @BeforeEach
    public void setUpMockingData() {
        databaseCleanup.execute();
        memberMocking.createMocking();
        entityManager.flush();
        entityManager.clear();
    }
}

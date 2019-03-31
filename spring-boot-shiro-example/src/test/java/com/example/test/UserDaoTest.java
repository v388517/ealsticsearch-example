package com.example.test;

import com.example.App;
import com.example.dao.UserDao;
import com.example.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testFindByUsername() {
        Specification<User> example = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> username = root.get("username");
                Predicate predicate = criteriaBuilder.equal(username, "admin");
                return predicate;
            }
        };
        User user = userDao.findOne(example);

        System.err.println(user);
    }
}

package com.basumatarau.imProject.persistence.repository.util;

import com.basumatarau.imProject.persistence.model.User;
import com.basumatarau.imProject.persistence.model.meta.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class UserSpecifications {
    public static Specification<User> hasEmailLike(String pattern){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(User_.EMAIL), "%" + pattern + "%");
            }
        };
    }

    public static Specification<User> hasNickNameLike(String pattern){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(User_.NICK_NAME), "%" + pattern + "%");
            }
        };
    }
}

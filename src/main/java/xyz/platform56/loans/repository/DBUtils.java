package xyz.platform56.loans.repository;

import javax.persistence.criteria.*;
import java.util.List;

public class DBUtils {
    public static void addPredicateToConditions(List<Predicate> conditions, Predicate predicate) {
        if (predicate != null) {
            conditions.add(predicate);
        }
    }

    public static void addSort(CriteriaBuilder cb, CriteriaQuery<?> query, Root<?> root, String field) {
        Order sort = cb.asc(root.<Long>get(field));
        query.orderBy(sort);
    }

}

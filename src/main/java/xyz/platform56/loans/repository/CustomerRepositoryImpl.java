package xyz.platform56.loans.repository;

import xyz.platform56.loans.entity.CustomerEntity;
import xyz.platform56.loans.pojo.PaginationSearchRequest;
import xyz.platform56.loans.pojo.SearchResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Slf4j
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {


    @Autowired
    private EntityManager em;

    @Override
    public SearchResponse<CustomerEntity> search(String customerName, PaginationSearchRequest searchRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerEntity> query = cb.createQuery(CustomerEntity.class);
        List<Predicate> conditions = Lists.newArrayList();

        Root<CustomerEntity> customerRoot = query.from(CustomerEntity.class);
        Predicate anyPredicate = null;

        if (StringUtils.isNotEmpty(customerName)) {
            anyPredicate = cb.like(cb.lower(customerRoot.<String>get(CustomerEntity.Paths.name.name())), "%" + customerName.toLowerCase() + "%");
            DBUtils.addPredicateToConditions(conditions, anyPredicate);
        }
        query.where(conditions.toArray(new Predicate[conditions.size()]));
        DBUtils.addSort(cb, query, customerRoot, "id");
        query.distinct(true);
        List<CustomerEntity> entities = em.createQuery(query)
                .setFirstResult(searchRequest.getStart()).setMaxResults(searchRequest.getCount()).getResultList();
        Long total = 0L;
        //count query
        CriteriaQueryImpl<Long> countQuery = (CriteriaQueryImpl<Long>) cb.createQuery(Long.class);
        countQuery.getRoots().add(customerRoot);
        countQuery.select(cb.countDistinct(customerRoot));
        countQuery.where(conditions.toArray(new Predicate[conditions.size()]));
        total = em.createQuery(countQuery).getSingleResult();
        return new SearchResponse<CustomerEntity>(searchRequest.getStart(), searchRequest.getCount(), total.intValue(), entities);
    }


}
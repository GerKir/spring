package com.gerkir.repos;

import com.gerkir.entity.Categories;
import com.gerkir.entity.News;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NewsRepo extends CrudRepository<News, Long>, JpaSpecificationExecutor<News> {
    Iterable<News> findByCategories(Set<Categories> categories);

    Iterable<News> findByTitleIgnoreCaseContainingOrTextIgnoreCaseContaining(String text1, String text2);

    Iterable<News> findByTitleIgnoreCaseContainingOrTextIgnoreCaseContainingAndCategoriesContaining(String text1, String text2, Set<Categories> categories);

    News findById(long id);

    void deleteById(long id);

}

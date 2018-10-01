package com.gerkir.repos;

import com.gerkir.entity.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepo extends CrudRepository<News, Long> {
    Iterable<News> findByCategories(String categories);

    News findById(long id);

    void deleteById(long id);

}

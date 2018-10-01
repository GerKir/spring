package com.gerkir.spring.controller;

import com.gerkir.entity.News;
import com.gerkir.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRepo newsRepo;

    @GetMapping
    public String getAllNews(Map<String, Object> model) {
        Iterable<News> news = newsRepo.findAll();
        model.put("news", news);
        return "news";
    }

    @GetMapping("/add")
    public String add(Map<String, Object> model) {
        model.put("message", "Добавьте новость");
        return "add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,
                      @RequestParam String text,
                      @RequestParam(name = "categories", required = false, defaultValue = "") String categories,
                      Map<String, Object> model) {
        News news = new News(title, text, categories);
        newsRepo.save(news);
        model.put("message", "Новость добавлена");
        return "add";
    }

    @PostMapping("categoriesFilter")
    public String categoriesFilter(@RequestParam String categories, Map<String, Object> model) {
        if (categories.isEmpty() || categories == null) {
            return getAllNews(model);

        } else {
            model.put("news", newsRepo.findByCategories(categories));
            return "news";
        }
    }

    @GetMapping("edit")
    public String edit(@RequestParam long id, Map<String, Object> model) {
        News news = newsRepo.findById(id);
        model.put("news", news);
        return "edit";
    }

    @PostMapping("edit")
    public String edit(@RequestParam long id,
                       @RequestParam String title,
                       @RequestParam String categories,
                       @RequestParam String text,
                       Map<String, Object> model) {
        News news = new News(title, text, categories);
        news.setId(id);

        newsRepo.deleteById(id);
        newsRepo.save(news);

        return getAllNews(model);
    }

    @PostMapping("delete")
    public String delete(@RequestParam long id, Map<String, Object> model) {
        newsRepo.deleteById(id);

        return getAllNews(model);
    }
}

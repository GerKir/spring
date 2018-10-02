package com.gerkir.spring.controller;

import com.gerkir.entity.Categories;
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

    // TODO: 01.10.2018 Переписать метод сортировки

    @GetMapping
    public String getAllNews(Map<String, Object> model) {
        model.put("categories", Categories.values());
        model.put("news", newsRepo.findAll());
        return "news";
    }
    @PostMapping
    public String getFilteredNews(@RequestParam(name = "category", required = false, defaultValue = "") Categories categories,
                                  Map<String, Object> model) {
        if (categories == null) {
            return "redirect:/news";
        } else {
            Set<Categories> set = new HashSet<>();
            set.add(categories);
            model.put("categories", Categories.values());
            model.put("news", newsRepo.findByCategories(set));
            return "news";
        }
    }
    @GetMapping("/find")
    public String find(@RequestParam String text, Map<String, Object> model) {
        model.put("categories", Categories.values());
        model.put("news", newsRepo.findByTitleIgnoreCaseContainingOrTextIgnoreCaseContaining(text, text));

        return "news";
    }

    @GetMapping("/add")
    public String add(Map<String, Object> model) {
        model.put("message", "Добавьте новость");
        model.put("categories", Categories.values());
        return "add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,
                      @RequestParam String text,
                      @RequestParam(name = "category") Categories category,
                      Map<String, Object> model) {
        model.put("categories", Categories.values());
        Set<Categories> categoriesSet = new HashSet<>();
        categoriesSet.add(category);
        News news = new News(title, text, categoriesSet);
        newsRepo.save(news);
        model.put("message", "Новость добавлена");
        return "add";
    }

    @GetMapping("edit")
    public String edit(@RequestParam long id, Map<String, Object> model) {
        News news = newsRepo.findById(id);
        model.put("news", news);
        return "edit";
    }

    // TODO: 02.10.2018 Реализовать метод
    @PostMapping("edit")
    public String edit(@RequestParam long id,
                       @RequestParam String title,
                       @RequestParam String categories,
                       @RequestParam String text,
                       Map<String, Object> model) {
//        News news = new News(title, text, categories);
//        news.setId(id);
//
//        newsRepo.deleteById(id);
//        newsRepo.save(news);

        return "redirect:/news";
    }

    @PostMapping("delete")
    public String delete(@RequestParam long id, Map<String, Object> model) {
        newsRepo.deleteById(id);
        return "redirect:/news";
    }
}
